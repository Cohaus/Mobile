package com.solution.gdsc.util


import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

private const val DATE_YEAR_MONTH_DAY_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'"
private const val DATE_REPAIR_PROCEED_AT_PATTERN = "yyyy-MM-dd"

object DateFormatter {

    fun convertToDate(dateString: String): Date? {
        val simpleDateFormat = SimpleDateFormat(DATE_YEAR_MONTH_DAY_PATTERN, Locale.KOREA)
        return simpleDateFormat.parse(dateString)
    }

    fun convertToRepairDateFormat(date: Date): String {
        val newFormat = SimpleDateFormat(DATE_REPAIR_PROCEED_AT_PATTERN, Locale.KOREA)
        return newFormat.format(date)
    }

    fun getCurrentTime(): String {
        val simpleDateFormat = SimpleDateFormat(DATE_YEAR_MONTH_DAY_PATTERN, Locale.KOREA)
        val currentDate = Calendar.getInstance(TimeZone.getTimeZone("UTC")).time
        return simpleDateFormat.format(currentDate)
    }
}