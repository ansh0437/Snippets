package com.ansh.utilities

import com.ansh.extensions.logError
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object DateUtil {

    val tag: String = DateUtil::class.java.simpleName

    const val UTC = "UTC"
    const val DATE_FORMAT = "yyyy-MM-dd"
    const val DISPLAY_DATE_FORMAT = "dd MMM, yyyy"
    const val DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
    const val DISPLAY_DATE_TIME_FORMAT = "dd MMM, yyyy HH:mm"
    const val DISPLAY_DATE_TIME_FORMAT_AM_PM = "dd MMM, yyyy hh:mm a"

    val date: String
        get() {
            val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            return dateFormat.format(Date())
        }

    val dateTimeUTC: String
        get() {
            val dateFormat = SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault())
            dateFormat.timeZone = utcTimezone
            return dateFormat.format(Date())
        }

    val dateTimeLocal: String
        get() {
            val dateFormat = SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault())
            dateFormat.timeZone = localTimezone
            return dateFormat.format(Date())
        }

    val utcTimezone = TimeZone.getTimeZone(UTC)

    val localTimezone = TimeZone.getTimeZone(Calendar.getInstance().timeZone.id)

    fun convertUTCToLocal(utcDateString: String, format: String): String {
        return try {
            val inputFormat = SimpleDateFormat(format, Locale.getDefault())
            inputFormat.timeZone = utcTimezone
            val utcDate = inputFormat.parse(utcDateString)
            val outputFormat = SimpleDateFormat(format, Locale.getDefault())
            outputFormat.timeZone = localTimezone
            outputFormat.format(utcDate)
        } catch (e: Exception) {
            utcDateString
        }
    }

    fun convertLocalToUTC(localDateString: String, format: String): String {
        return try {
            val inputFormat = SimpleDateFormat(format, Locale.getDefault())
            inputFormat.timeZone = localTimezone
            val localDate = inputFormat.parse(localDateString)
            val outputFormat = SimpleDateFormat(format, Locale.getDefault())
            outputFormat.timeZone = utcTimezone
            outputFormat.format(localDate)
        } catch (e: Exception) {
            localDateString
        }
    }

    fun convertDateFormat(dateString: String, inFormat: String, outFormat: String): String {
        return try {
            var dateFormat: DateFormat = SimpleDateFormat(inFormat, Locale.getDefault())
            val date = dateFormat.parse(dateString)
            dateFormat = SimpleDateFormat(outFormat, Locale.getDefault())
            dateFormat.format(date)
        } catch (e: ParseException) {
            e.logError(tag, "convertDateFormat: ")
            dateString
        }
    }

    fun convertToMillis(dateString: String, format: String): Long {
        return try {
            val sdf = SimpleDateFormat(format, Locale.getDefault())
            val date = sdf.parse(dateString)
            date.time
        } catch (e: ParseException) {
            e.logError(tag, "convertToMillis: ")
            0
        }
    }
}