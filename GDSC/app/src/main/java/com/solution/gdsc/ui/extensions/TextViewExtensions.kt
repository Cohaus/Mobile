package com.solution.gdsc.ui.extensions

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("changeTimeFormat")
fun TextView.changeTimeFormat(time: String?) {
    if (!time.isNullOrEmpty()) {
        text = time.substring(0, 10)
    }
}