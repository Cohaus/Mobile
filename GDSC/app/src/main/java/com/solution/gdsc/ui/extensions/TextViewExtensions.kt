package com.solution.gdsc.ui.extensions

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.solution.gdsc.ui.common.AiGrade

@BindingAdapter("changeTimeFormat")
fun TextView.changeTimeFormat(time: String?) {
    if (!time.isNullOrEmpty()) {
        text = time.substring(0, 10)
    }
}

@BindingAdapter("setGradeMessage")
fun TextView.setGradeMessage(grade: String?) {
    text = when (grade) {
        AiGrade.SUPERIORITY.grade -> AiGrade.SUPERIORITY.message
        AiGrade.GENERAL.grade -> AiGrade.GENERAL.message
        AiGrade.FAULTY.grade -> AiGrade.FAULTY.message
        else -> ""
    }
}

@BindingAdapter("setTelFormat")
fun TextView.setTelNumber(number: String?) {
    if (number == null) return
    else {
        val first = number.substring(0, 3) + "-"
        val last = "-" + number.substring(7)
        val middle = number.substring(3, 7)
        text = "$first$middle$last"
    }
}