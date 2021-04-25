package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    lateinit var textView: TextView

    var lastNumeric: Boolean = false
    var lastDot = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)
    }

    fun onDigit(view: View) {
        textView.append((view as Button).text)
        lastNumeric = true
    }

    fun onOperator(view: View){
        if (lastNumeric && !isOperatorAdded((textView.text).toString())){
            textView.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }
    fun onClear(view: View){
        textView.text= ""
        lastNumeric =false
        lastDot =false
    }

    fun onDecimalPoint(view: View){
        if (!lastDot && lastNumeric){
            textView.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onEqual(view: View){
        if (lastNumeric){
            var tvValue = textView.text.toString()
            var prefix = ""

            try {
                if (tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                when {
                    tvValue.contains("-") -> {
                        val splitValue = tvValue.split("-")
                        var first = splitValue[0]
                        var second = splitValue[1]
                        if (!prefix.isEmpty()){
                            first = prefix + first
                        }
                        textView.text = removeZeroAfterDecimal((first.toDouble() - second.toDouble()).toString())
                    }
                    tvValue.contains("+") -> {
                        val splitValue = tvValue.split("+")
                        var first = splitValue[0]
                        var second = splitValue[1]
                        if (!prefix.isEmpty()){
                            first = prefix + first
                        }
                        textView.text = removeZeroAfterDecimal((first.toDouble() + second.toDouble()).toString())
                    }
                    tvValue.contains("/") -> {
                        val splitValue = tvValue.split("/")
                        var first = splitValue[0]
                        var second = splitValue[1]
                        if (!prefix.isEmpty()){
                            first = prefix + first
                        }
                        textView.text = removeZeroAfterDecimal((first.toDouble() / second.toDouble()).toString())
                    }
                    tvValue.contains("*") -> {
                        val splitValue = tvValue.split("*")
                        var first = splitValue[0]
                        val second = splitValue[1]
                        if (!prefix.isEmpty()){
                            first = prefix + first
                        }
                        textView.text = removeZeroAfterDecimal((first.toDouble() * second.toDouble()).toString())
                    }
                }
            } catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun isOperatorAdded(value: String): Boolean{
        return if (value.startsWith("-")){
            false
        } else{
            value.contains("/") || value.contains("+") || value.contains("-") || value.contains("*")
        }
    }

    private fun removeZeroAfterDecimal(result: String): String{
        var value = result
        if (result.contains(".0")){
            value = result.substring(0, result.length -2)
        }
        return value
    }

}