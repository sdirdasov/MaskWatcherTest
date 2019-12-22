package com.example.task1

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import java.lang.Exception
import java.util.regex.Pattern

class MaskWatcher(val editText: EditText) : TextWatcher {

    private val TAG = "MaskWatcher"

    private val patternSymbols = Pattern.compile("[ABEKMHOPCTYXАВЕКМНОРСТУХ]")
    private val patternNumber = Pattern.compile("[0-9]")

    val regexChar = Regex("[ABEKMHOPCTYXАВЕКМНОРСТУХabekmhopctyxавекмнорстух]{1,2}|\\d+")
    val regexNumber = Regex("([ABEKMHOPCTYXАВЕКМНОРСТУХ]{2})(\\d+)")

    init {
        editText.addTextChangedListener(this)
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(inputText: Editable) {

        Log.d(TAG, "afterTextChanged: [inputText: $inputText]")

        if (inputText.isFirstCharNumber()) {
            inputText.replace(0, 1, "")
            return
        }

        editText.removeTextChangedListener(this)

        val found = Regex(REGEX_BY_GROUPS).findAll(
            inputText.toString().replace(" ", "")
        )
        var unMaskText = inputText.toString().replaceChars()

        var block = 1
        var currentMask = DEFAULT_MASK
        var tempText = StringBuilder("")
        found.forEach { f ->
            var m = f.value
            val idx = f.range

            Log.d(TAG, "afterTextChanged: $m found at indexes: $idx")

            if (block == 1 && m.length == 2) {
                currentMask = TAXI_MASK
            } else if (block == 2) {
                m = m.replace("[^$ALL_NUMBERS]".toRegex(), "")
                if (m.length >= 4) {
                    currentMask = POLICE_MASK
                }
            } else if (block == 3 && currentMask != DEFAULT_MASK) {
                m = m.replace("[^$ALL_NUMBERS]".toRegex(), "")
            } else if (block ==3 && currentMask == DEFAULT_MASK) {
                m = m.replace("[$ALL_NUMBERS]".toRegex(), "")
            }
            Log.d(TAG, "afterTextChanged: [m: $m")
            tempText.append(m)
            block++
        }
        unMaskText = tempText.toString()

        var i = 0

        var maskedText = StringBuilder("")

        Log.d(TAG, "afterTextChanged: [unMaskText: $unMaskText")

        for (char in currentMask.toCharArray()) {
            if (char != '#' && i < unMaskText.length) {
                maskedText.append(char)
                continue
            }
            try {
                maskedText.append(unMaskText[i])
            } catch (e: Exception) {
                break
            }
            i++
        }
        var testResult = inputText.replace(0, inputText.length, maskedText.toString().toUpperCase())
        Log.d(TAG, "afterTextChanged: [testResult: $testResult")

        editText.addTextChangedListener(this)
    }
}


fun Editable.isFirstCharNumber() = Regex("^$ALL_NUMBERS").matches(this)

fun String.replaceChars() = replace(REGEX_FOUND_ANY_EXCEPT.toRegex(), "")