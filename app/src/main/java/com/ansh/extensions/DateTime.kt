package com.ansh.extensions

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

private const val tag = "DateTimeExtension"

val String.toLocalDate
    get() = {
        val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        df.timeZone = TimeZone.getTimeZone("UTC")
        val date = df.parse(this)
        df.timeZone = TimeZone.getDefault()
        df.format(date)
    }

val String.toLocalDateTime
    get() = {
        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        df.timeZone = TimeZone.getTimeZone("UTC")
        val date = df.parse(this)
        df.timeZone = TimeZone.getDefault()
        df.format(date)
    }

fun String.toFormattedDate(inFormat: String, outFormat: String): String {
    var dateFormat: DateFormat = SimpleDateFormat(inFormat, Locale.getDefault())
    val date: Date
    try {
        date = dateFormat.parse(this)
    } catch (e: ParseException) {
        e.logError(tag, "toFormattedDate: ")
        return this
    }
    dateFormat = SimpleDateFormat(outFormat, Locale.getDefault())
    return dateFormat.format(date)
}