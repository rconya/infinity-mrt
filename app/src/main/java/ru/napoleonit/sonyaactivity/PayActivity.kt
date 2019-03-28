package ru.napoleonit.sonyaactivity

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem
import android.widget.ArrayAdapter
import com.github.baudm.textinputvalidator.Rule
import com.github.baudm.textinputvalidator.TextInputValidator

import kotlinx.android.synthetic.main.activity_pay.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class PayActivity : AppCompatActivity() {

    //private lateinit var validator: TextInputValidator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)

        supportActionBar?.title = "Оплата заказа"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val months = listOf("Январь", "Февраль", "Март","Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь")
        monthView.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, months)

        val years = listOf("2019", "2020", "2021","2022", "2023", "2024")
        yearView.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, years)

//        val numberRule = object : Rule("Введите номер корректно") {
//            override fun isValid(text: String?) = text?.length == 16
//        }
//
//        validator = TextInputValidator.Builder()
//            .addRule(numberEdit, numberRule)
//            .build()
//
//        payButton.onClick {
//            if (validator.validate()) {
//                // sending
//            } else {
//                // ???
//            }
//        }
    }

    override fun onOptionsItemSelected(item: MenuItem?) = if (item?.itemId == android.R.id.home) {
        onBackPressed()
        true
    } else {
        super.onOptionsItemSelected(item)
    }

}
