package com.solution.gdsc.ui.extensions

import android.widget.CheckBox
import com.solution.gdsc.R

fun CheckBox.changeTextColor() {
    this.setOnClickListener {
        if (isChecked) {
            setTextColor(resources.getColor(R.color.gray_30, context?.theme))
        } else setTextColor(resources.getColor(R.color.gray_400, context?.theme))
    }
}