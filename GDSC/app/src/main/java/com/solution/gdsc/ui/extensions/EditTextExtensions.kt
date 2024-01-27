package com.solution.gdsc.ui.extensions

import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText

fun EditText.changeNextVisibleWithFocus(nextEditText: EditText) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
            nextEditText.visibility = View.VISIBLE
            nextEditText.requestFocus()
            true
        } else false
    }
}