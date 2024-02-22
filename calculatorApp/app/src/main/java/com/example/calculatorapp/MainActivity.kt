package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TimePicker
import android.widget.Toast
import com.example.calculatorapp.databinding.ActivityMainBinding
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val firstNumberText = StringBuilder("")
    private val secondNumberText = StringBuilder("")
    private val operatorText = StringBuilder("")
    private val decimalFormat = DecimalFormat("#.###")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    fun numberClicked(view: View){
        val numberString = (view as? Button)?.text?.toString() ?: ""
        val numberText = if (operatorText.isEmpty()) firstNumberText else secondNumberText
        numberText.append(numberString)
        updateEquationTextView()
    }

    fun clearClicked(view: View){
        firstNumberText.clear()
        secondNumberText.clear()
        operatorText.clear()
        binding.resultTextView.text = ""
        updateEquationTextView()
    }

    fun operatorClicked(view: View){
        val operatorString = (view as? Button)?.text?.toString()
        if (firstNumberText.isNullOrEmpty()){
            Toast.makeText(this,"첫번째 수를 입력해 주세요!", Toast.LENGTH_SHORT).show()
        }
        if (secondNumberText.isNullOrEmpty().not()){
            Toast.makeText(this, "추가적인 연산자 사용은 불가능합니다.", Toast.LENGTH_SHORT).show()
        }

        if (operatorText.isNotEmpty()){
            Toast.makeText(this, "추가적인 연산자 사용은 불가능합니다.", Toast.LENGTH_SHORT).show()
        } else{
            operatorText.append(operatorString)
        }
        updateEquationTextView()
    }

    fun equalClicked(view: View){
        if (firstNumberText.isEmpty() || operatorText.isEmpty() || secondNumberText.isEmpty()){
            Toast.makeText(this,"올바르지 않은 수식입니다,", Toast.LENGTH_SHORT).show()
            return
        }
        val firstNumber = firstNumberText.toString().toBigDecimal()
        val secondNumber = secondNumberText.toString().toBigDecimal()

        val result = when(operatorText.toString()){
            "+" -> decimalFormat.format(firstNumber + secondNumber)
            "-" -> decimalFormat.format(firstNumber - secondNumber)
            "*" -> decimalFormat.format( firstNumber * secondNumber)
            "/" -> (firstNumber.toFloat() / secondNumber.toFloat()).toString()
            else -> "잘못된 수식입니다"
        }

        binding.resultTextView.text = result
    }


    fun updateEquationTextView(){
        val firstFormattedNumber = if (firstNumberText.isNotEmpty()) decimalFormat.format(firstNumberText.toString().toBigDecimal()) else ""
        val secondFormattedNumber = if (secondNumberText.isNotEmpty()) decimalFormat.format(secondNumberText.toString().toBigDecimal()) else ""
        binding.equationTextView.text = "$firstFormattedNumber $operatorText $secondFormattedNumber"
    }
}