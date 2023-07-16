package com.example.kapkan

import kotlin.random.Random

class StateHolder {
    private val data = Data()

    var hintState = HintState.NOT_SHOWN
    enum class HintState { NOT_SHOWN, SHOWN_HUNDOK, SHOWN_BOTH }
    enum class Type { HANJA, NATIVE_NUMBERS }

    var numberOfWins = 0
    var state = Type.HANJA

    var number = getRandomNumber()
    var hanja = getRandomHanja()

    fun updateNumber() {
        number = getRandomNumber()
    }

    fun updateHanja() {
        hanja = getRandomHanja()
    }


    // получаем пару (число цифрами/число хангылем)
    private fun getRandomNumber(): Pair<Int, String> {
        // берём рандомное число.
        // fro korean
        // last digit
        val numberLD = Random.nextInt(1, 10)
        val numberStringLD = data.koreanNumbers[numberLD]

        // first digit

        val numberFD = Random.nextInt(1, 10) * 10
        val numberStringFD = data.koreanNumbers[numberFD]

        val resultInt = numberFD + numberLD
        val resultString = numberStringFD.toString() + " " + numberStringLD.toString()

        return Pair(resultInt, resultString)
    }

    private fun getRandomHanja(): Data.HanjaRecord {
        val list = data.hanjaNew
        return list[Random.nextInt(0, list.size)]
    }
}