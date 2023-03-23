package com.example.kapkan

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    val stateHolder = StateHolder()

    var numberOfWins = 0

    enum class Type { HANJA, NATIVE_NUMBERS }

    var state = Type.HANJA

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.submit_button)

        initTextView(stateHolder.number)
        initTextView(stateHolder.hanja)

/*        findViewById<Button>(R.id.reverseToHanja).setOnClickListener {
            //state = if (state == Type.NATIVE_NUMBERS) Type.HANJA else Type.NATIVE_NUMBERS
        }*/

        findViewById<Button>(R.id.submit_button).setOnClickListener {
            when (state) {
                Type.NATIVE_NUMBERS -> {
                    button.setOnClickListener {
                        if (numbersButtonLogic(
                                findViewById(R.id.answer_edit_text),
                                stateHolder.number
                            )
                        ) {
                            stateHolder.updateNumber()
                            initTextView(stateHolder.number)
                        }
                    }
                }
                Type.HANJA -> {
                    button.setOnClickListener {
                        if (hanjaButtonLogic(
                                findViewById(R.id.answer_edit_text),
                                stateHolder.hanja
                            )
                        ) {
                            stateHolder.updateHanja()
                            initTextView(stateHolder.hanja)
                        }
                    }
                }
            }
        }
    }

    private fun initTextView(number: Pair<Int, String>) {
        val numberTV = findViewById<TextView>(R.id.written_number_text_view)
        numberTV.text = number.first.toString()
    }

    private fun initTextView(hanjaRecord: Data.HanjaRecord) {
        val numberTV = findViewById<TextView>(R.id.written_number_text_view)
        numberTV.text = hanjaRecord.syllable
    }

    private fun numbersButtonLogic(
        submitEditText: TextInputLayout,
        randomNumber: Pair<Int, String>
    ): Boolean {

        // get user written number
        val readFromEditText = submitEditText.editText?.text.toString()

        // check
        if (readFromEditText == randomNumber.second) {
            submitEditText.error = null
            submitEditText.editText?.text?.clear()
            numberOfWins++
            findViewById<TextView>(R.id.number_of_wins).text = numberOfWins.toString()

            return true
        } else {
            submitEditText.error = "that's not right!"

            return false
        }
    }

    private fun hanjaButtonLogic(
        submitEditText: TextInputLayout,
        randomHanja: Data.HanjaRecord
    ): Boolean {
        val readFromEditText = submitEditText.editText?.text.toString()
        if (readFromEditText == randomHanja.koreanSound) {
            submitEditText.error = null
            submitEditText.editText?.text?.clear()
            numberOfWins++
            findViewById<TextView>(R.id.number_of_wins).text = numberOfWins.toString()
            return true
        } else {
            submitEditText.error = "that's not right!"
            return false
        }
    }

}




