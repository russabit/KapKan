package com.example.kapkan

class Values {
    companion object {
        const val HINT_TIMEOUT: Long = 5000
        const val ERROR_MESSAGE = "that's not right!"
    }

    enum class GameOptions(var interpretation: String) {
        OPTION_1("Hanja To Transcription"),
        OPTION_2("Transcription To Hanja"),
        OPTION_3("Number To Transcription"),
        OPTION_4("Transcription To Number")
    }
}