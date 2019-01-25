package com.ansh.utilities

import java.text.SimpleDateFormat
import java.util.*


object DateUtils {

    const val DATE_FORMAT = "yyyy-MM-dd"
    const val DISPLAY_DATE_FORMAT = "dd MMM, yyyy"
    const val DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
    const val DISPLAY_DATE_TIME_FORMAT = "dd MMM, yyyy HH:mm"

    val dateTimeUTC: String
        get() {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            return dateFormat.format(Date())
        }

    val dateTimeGMT: String
        get() {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            return dateFormat.format(Date())
        }
}