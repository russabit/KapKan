package com.example.kapkan

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    val stateHolder = StateHolder()
    lateinit var widgets: WidgetHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        widgets = WidgetHolder(this)

        initTextView(stateHolder.number)
        initTextView(stateHolder.hanja)

        widgets.answerEditText.setErrorIconOnClickListener {errorIconClickListener()}

        widgets.fabButton.setOnClickListener {
            fabButtonClickListener()
        }

        widgets.submitButton.setOnClickListener {
            submitButtonClickListener()
        }
    }

    private fun errorIconClickListener() {
        Toast.makeText(this, stateHolder.hanja.koreanSound, Toast.LENGTH_SHORT).show()
    }

    private fun submitButtonClickListener(
    ) {

        when (stateHolder.state) {
            StateHolder.Type.NATIVE_NUMBERS -> {
                if (isNumberAnswerRight(stateHolder.number)) {
                    onSuccessfulNumberAnswer()
                }
            }

            StateHolder.Type.HANJA -> {
                if (isHanjaAnswerRight(stateHolder.hanja)) {
                    onSuccessfulHanjaAnswer()
                }
            }

        }
    }

    private fun onSuccessfulNumberAnswer() {
        stateHolder.updateNumber()
        initTextView(stateHolder.number)
    }

    private fun onSuccessfulHanjaAnswer() {
        stateHolder.updateHanja()

        stateHolder.hintState = StateHolder.HintState.NOT_SHOWN
        widgets.hundokTextView.visibility = View.GONE
        widgets.translationTextView.visibility = View.GONE

        initTextView(stateHolder.hanja)
    }

    private fun fabButtonClickListener() {
        when (stateHolder.hintState) {
            StateHolder.HintState.NOT_SHOWN -> {
                showHundokHintFirst()
            }

            StateHolder.HintState.SHOWN_HUNDOK -> {
                showTranslationsOnSecondPress()
            }

            StateHolder.HintState.SHOWN_BOTH -> {
                //doNothingForNow
            }
        }
    }

    private fun showTranslationsOnSecondPress() {
        val isNotEmpty = stateHolder.hanja.translationRU.isNotEmpty()

        fun showHint() {
            widgets.translationTextView.visibility = View.VISIBLE
            widgets.translationTextView.text = stateHolder.hanja.translationRU.toString()

            stateHolder.hintState = StateHolder.HintState.SHOWN_BOTH
        }

        if (isNotEmpty) showHint()
    }

    private fun showHundokHintFirst() {
        val isNotEmpty = stateHolder.hanja.hundok !in listOf("", "-")

        fun showHint() {
            widgets.hundokTextView.visibility = View.VISIBLE
            widgets.hundokTextView.text = stateHolder.hanja.hundok

            stateHolder.hintState = StateHolder.HintState.SHOWN_HUNDOK
        }

        if (isNotEmpty) showHint() else showTranslationsOnSecondPress()
    }

    private fun initTextView(number: Pair<Int, String>) {
        val numberTV = findViewById<TextView>(R.id.written_number_text_view)
        numberTV.text = number.first.toString()
    }

    private fun initTextView(hanjaRecord: Data.HanjaRecord) {
        val numberTV = findViewById<TextView>(R.id.written_number_text_view)
        numberTV.text = hanjaRecord.syllable
    }

    private fun isNumberAnswerRight(
        randomNumber: Pair<Int, String>
    ): Boolean {

        // get user written number
        val readFromEditText = widgets.answerEditText.editText?.text.toString()

        // check
        if (readFromEditText == randomNumber.second) {
            widgets.answerEditText.error = null
            widgets.answerEditText.editText?.text?.clear()
            stateHolder.numberOfWins++
            findViewById<TextView>(R.id.number_of_wins).text = stateHolder.numberOfWins.toString()

            return true
        } else {
            widgets.answerEditText.error = "that's not right!"

            return false
        }
    }

    private fun isHanjaAnswerRight(
        randomHanja: Data.HanjaRecord
    ): Boolean {
        val readFromEditText = widgets.answerEditText.editText?.text.toString().trim()
        return if (readFromEditText == randomHanja.koreanSound.trim()) {
            widgets.answerEditText.error = null
            widgets.answerEditText.editText?.text?.clear()
            stateHolder.numberOfWins++
            findViewById<TextView>(R.id.number_of_wins).text = stateHolder.numberOfWins.toString()
            true
        } else {
            widgets.answerEditText.error = "that's not right!"

            false
        }
    }

}




