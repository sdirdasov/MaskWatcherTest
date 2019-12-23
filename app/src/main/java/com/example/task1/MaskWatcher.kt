package com.example.task1

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.lang.Exception
import java.util.*

class MaskWatcher(val editText: EditText) : TextWatcher {

    var currentMask = DEFAULT_MASK

    init {
        editText.addTextChangedListener(this)
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(inputText: Editable) {

        if (inputText.isFirstCharNumber()) {
            inputText.replace(0, 1, "")
            return
        }
        editText.removeTextChangedListener(this)

        val found = Regex(REGEX_BY_GROUPS).findAll(
            inputText.toString().replace(" ", "")
        )

        setMask(found)
        inputText.replace(0, inputText.length, formattedByMask(formatSection(found)).toUpperCase(
            Locale.getDefault()
        ))

        editText.addTextChangedListener(this)
    }

    private fun formattedByMask(text: String) : String {

        var i = 0
        var maskedText = StringBuilder("")
        for (char in currentMask.toCharArray()) {
            if (char != '#' && i < text.length) {
                maskedText.append(char)
                continue
            }
            try {
                maskedText.append(text[i])
            } catch (e: Exception) {
                break
            }
            i++
        }
        return maskedText.toString()
    }

    private fun setMask(match: Sequence<MatchResult>) {

        var section = 1
        currentMask = DEFAULT_MASK
        match.forEach { f ->
            var m = f.value
            if (section == 1 && m.length == 2) {
                currentMask = TAXI_MASK
            } else if (section == 2) {
                m = replaceAllChars(m)
                if (m.length >= 4) {
                    currentMask = POLICE_MASK
                }
            }
            section++
        }
    }

    private fun formatSection(match: Sequence<MatchResult>): String {

        var section = 1
        var builder = StringBuilder("")

        match.forEach { f ->
            var m = f.value
            if (section == 2 || (section == 3 && currentMask != DEFAULT_MASK) || section > 3) {
                m = replaceAllChars(m)
            }
            builder.append(m)
            section++
        }
        return builder.toString()
    }

    private fun replaceAllChars(s: String) = s.replace("[^$ALL_NUMBERS]".toRegex(), "")
}

fun Editable.isFirstCharNumber() = Regex("^$ALL_NUMBERS").matches(this)