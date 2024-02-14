package com.jeriklima.apptic12

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.jeriklima.apptic12.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private var totalValue = 0.0
    private val checkboxStates = mutableListOf<Boolean>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.Calculate.setOnClickListener(this)
        binding.Order.setOnClickListener(this)
        initializeCheckboxStates()
    }

    private fun initializeCheckboxStates() {
        val checkboxList = listOf(
            binding.checkbox1Entrada, binding.checkbox2Entrada,
            binding.checkbox3Entrada, binding.checkbox4Entrada,
            binding.checkbox1Principal, binding.checkbox2Principal,
            binding.checkbox1Bebidas, binding.checkbox2Bebidas,
            binding.checkbox1Sobremesa, binding.checkbox2Sobremesa
        )
        checkboxStates.clear()
        for(checkbox in checkboxList){
            checkboxStates.add(checkbox.isChecked)
        }
    }

    private fun Calculate() {

        val checkboxList = listOf(
            binding.checkbox1Entrada, binding.checkbox2Entrada,
            binding.checkbox3Entrada, binding.checkbox4Entrada,
            binding.checkbox1Principal, binding.checkbox2Principal,
            binding.checkbox1Bebidas, binding.checkbox2Bebidas,
            binding.checkbox1Sobremesa, binding.checkbox2Sobremesa
        )
        val priceTextViewList = listOf(
            binding.priceSalada, binding.priceCaponata,
            binding.priceGuacamole, binding.priceFondue,
            binding.pricePaillard, binding.priceSteak,
            binding.priceCoca, binding.priceAgua,
            binding.priceAbacaxi, binding.priceMorango
        )

        for (i in checkboxList.indices) {
            val checkbox = checkboxList[i]
            val price = priceTextViewList[i].text.toString().toDoubleOrNull()

            if (checkbox.isChecked && price != null && !checkboxStates[i] ) {
                totalValue += price
            } else if (!checkbox.isChecked && price != null && checkboxStates[i]) {
                totalValue -= price
            }
            checkboxStates[i] = checkbox.isChecked
        }
        binding.totalValue.text = "R$ ${totalValue.toFormattedPrice()}"
    }

    private fun Order() {
        Toast.makeText(this, "Seu pedido foi enviado para o balcão do restaurante", Toast.LENGTH_LONG).show()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.Calculate -> Calculate()
            R.id.Order -> Order()
        }
    }

    fun String.toDoubleOrNull(): Double? {
        return try {
            replace("R$ ", "").replace(",", ".").toDouble()
        } catch (e: NumberFormatException) {
            null
        }
    }

    fun Double.toFormattedPrice(): String {
        return String.format(Locale.US, "%.2f", this)
    }
}