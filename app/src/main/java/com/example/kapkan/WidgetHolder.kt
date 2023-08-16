package com.example.kapkan

import android.widget.Button
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout

class WidgetHolder(mainActivity: MainActivity) {

    val submitButton = mainActivity.findViewById<Button>(R.id.submit_button)
    val fabButton = mainActivity.findViewById<FloatingActionButton>(R.id.floatingActionButton)
    val hundokTextView = mainActivity.findViewById<TextView>(R.id.hundok)
    val translationTextView = mainActivity.findViewById<TextView>(R.id.translation)
    val answerEditText = mainActivity.findViewById<TextInputLayout>(R.id.answer_edit_text)
}
