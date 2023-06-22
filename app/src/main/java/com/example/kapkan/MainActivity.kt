package com.example.kapkan

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    val stateHolder = StateHolder()

    var numberOfWins = 0

    enum class Type { HANJA, NATIVE_NUMBERS }

    var state = Type.HANJA

    enum class HintState { NOT_SHOWN, SHOWN_HUNDOK, SHOWN_BOTH }

    var hintState = HintState.NOT_SHOWN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.submit_button)

        initTextView(stateHolder.number)
        initTextView(stateHolder.hanja)

        /*        findViewById<Button>(R.id.reverseToHanja).setOnClickListener {
                    //state = if (state == Type.NATIVE_NUMBERS) Type.HANJA else Type.NATIVE_NUMBERS
                }*/

        findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener {
            when (hintState) {
                HintState.NOT_SHOWN -> {
                    if (stateHolder.hanja.hundok !in listOf("", "-")) {
                        findViewById<TextView>(R.id.hundok).let {
                            it.visibility = View.VISIBLE
                            it.text = "${stateHolder.hanja.hundok} - ?"
                        }
                        hintState = HintState.SHOWN_HUNDOK
                    }
                }

                HintState.SHOWN_HUNDOK -> {
                    if (stateHolder.hanja.translationRU.isNotEmpty()) {
                        findViewById<TextView>(R.id.translation).let {
                            it.visibility = View.VISIBLE
                            it.text = stateHolder.hanja.translationRU.toString()
                        }
                        hintState = HintState.SHOWN_BOTH
                    }
                }

                HintState.SHOWN_BOTH -> {}
            }
        }

        findViewById<Button>(R.id.submit_button).setOnClickListener {
            when (state) {
                Type.NATIVE_NUMBERS -> {

                    if (numbersButtonLogic(
                            findViewById(R.id.answer_edit_text),
                            stateHolder.number
                        )
                    ) {
                        stateHolder.updateNumber()
                        initTextView(stateHolder.number)
                    }

                }

                Type.HANJA -> {
                    if (hanjaButtonLogic(
                            findViewById(R.id.answer_edit_text),
                            stateHolder.hanja
                        )
                    ) {
                        stateHolder.updateHanja()

                        hintState = HintState.NOT_SHOWN
                        findViewById<TextView>(R.id.hundok).visibility = View.GONE
                        findViewById<TextView>(R.id.translation).visibility = View.GONE

                        initTextView(stateHolder.hanja)
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
        val readFromEditText = submitEditText.editText?.text.toString().trim()
        return if (readFromEditText == randomHanja.koreanSound.trim()) {
            submitEditText.error = null
            submitEditText.editText?.text?.clear()
            numberOfWins++
            findViewById<TextView>(R.id.number_of_wins).text = numberOfWins.toString()
            true
        } else {
            submitEditText.error = "that's not right!"
            false
        }
    }

}




