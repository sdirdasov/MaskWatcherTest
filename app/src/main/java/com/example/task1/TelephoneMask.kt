package com.example.task1

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.lang.Exception

abstract class TelephoneMask {

    companion object {

        const val mask8 = "####-####"
        const val mask9 = "#####-####"
        const val mask10 = "(##) ####-####"
        const val mask11 = "(##) #####-####"

        fun unmask(s: String) = s.replace("[^0-9]*", "")

        fun getDefaultMask(string: String) = when {
            string.length > 11 -> mask11
            else -> mask8
        }

        fun insert(editText: EditText) = object : TextWatcher {

            var isUpdating = false
            var old = ""

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

                val str = unmask(s.toString())
                var defaultMask = getDefaultMask(str)

                var mask = when (str.length) {
                    11 -> mask11
                    10 -> mask10
                    9 -> mask9
                    else -> defaultMask
                }

                if (isUpdating) {
                    old = str
                    isUpdating = false
                    return
                }

                var i = 0
                var mascara = ""
                for (m in mask.toCharArray()) {
                    if ((m != '#' && str.length > old.length) || (m != '#' && str.length < old.length && str.length != i)) {
                        mascara += m
                        continue
                    }
                    try {
                        mascara += str[i]
                    } catch (e: Exception) {
                        e.printStackTrace()
                        break
                    }
                    i++
                }
                isUpdating = true
                editText.setText(mascara)
                editText.setSelection(mascara.length)
            }

            override fun afterTextChanged(s: Editable) {}
        }
    }
}