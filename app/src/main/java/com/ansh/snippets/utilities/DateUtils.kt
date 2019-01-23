package com.ansh.snippets.utilities

import android.util.Log

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object DateUtils {

    val DATE_FORMAT = "dd-MMM-yy"
    val DATE_TIME_FORMAT_IST = "dd MMM yyyy hh:mm:ss a"
    val DATE_TIME_FORMAT = "dd MMM yyyy hh:mm"
    val SUPPORTED_DATE_FORMATS = arrayOf(
        "MM/dd/yyyy",
        "dd-MMM-yyyy",
        "dd-MM-yyyy",
        "MM/dd/yy",
        "dd/MM/yyyy",
        "yyyy/MM/dd",
        "yyyy/dd/MM",
        "EE, MMM dd, yyyy",
        "yyyy/MM/dd HH:mm:ss",
        "yyyy-MM-dd",
        "yyyy-MM-dd HH:mm:ss",
        "EE, MMM dd, yyyy HH:mm:ss",
        "EE, MMM dd, yyyy HH:mm",
        "yyyy-MM-dd HH:mm",
        "EE, MMM dd, yyyy",
        "MMM dd",
        "yyyy-MM-dd HH:mm:ss.SSS",
        "HH:mm",
        "dd-MMM-yy"
    )

    private val TAG = DateUtils::class.java.simpleName
    private const val DATE_TIME_FORMAT_MONTH = "dd-MMM-yyyy HH:mm:ss"

    val serverDate: Date
        get() {
            val calendar = Calendar.getInstance()
            return calendar.time
        }

    val currentDate: String
        get() {
            var formattedDate = ""
            val calendar = Calendar.getInstance()
            try {
                val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                formattedDate = df.format(calendar.time)
            } catch (ex: Exception) {
                Log.e(TAG, ex.message, ex)
                return formattedDate
            }

            return formattedDate
        }

    val currentDateUTC: String
        get() {
            var formattedDate = ""
            val calendar = Calendar.getInstance()
            try {
                val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                df.timeZone = TimeZone.getTimeZone("UTC")
                formattedDate = df.format(calendar.time)
            } catch (ex: Exception) {
                Log.e(TAG, ex.message, ex)
                return formattedDate
            }

            return formattedDate
        }

    val utcDate: String
        get() {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            return dateFormat.format(Date())
        }

    /**
     * Purpose:To format the date
     *
     * @param date
     * @param format
     * @return
     */
    @JvmOverloads
    fun format(date: Date, format: String? = DATE_FORMAT): String? {
        if (format == null) {
            return null
        }
        val dateFormat = SimpleDateFormat(format)
        return dateFormat.format(date)
    }

    /**
     * Purpose: Get date from soap response
     *
     * @param soapDate String
     * @return Date
     */
    fun getDateFromSOAPResponse(soapDate: String?): Date? {
        if (soapDate == null) {
            return null
        }
        try {
            val parts =
                soapDate.split("T".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0].split("-".toRegex())
                    .dropLastWhile { it.isEmpty() }.toTypedArray()

            val calendar = Calendar.getInstance()

            calendar.set(Calendar.YEAR, Integer.parseInt(parts[0]))
            calendar.set(Calendar.MONTH, Integer.parseInt(parts[1]) - 1)
            calendar.set(Calendar.DATE, Integer.parseInt(parts[2]))

            return calendar.time

        } catch (e: Exception) {
            Log.e(TAG, "getDateFromSOAPResponse", e)
            return null
        }

    }

    /**
     * Purpose: Parse Date
     *
     * @param sDate String
     * @return Date
     */
    fun parseDate(sDate: String?): Date? {
        var sDate: String? = sDate ?: return null
        try {
            sDate = sDate!!.trim { it <= ' ' }
            val parts = sDate.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            val calendar = Calendar.getInstance()

            calendar.set(Calendar.DATE, Integer.parseInt(parts[0]))
            calendar.set(Calendar.MONTH, Integer.parseInt(parts[1]) - 1)
            calendar.set(Calendar.YEAR, Integer.parseInt(parts[2]))

            return calendar.time

        } catch (e: Exception) {
            Log.e(TAG, "parseDate", e)
            return null
        }

    }

    /**
     * Purpose: Returns date with out time
     *
     * @return Date
     * @throws
     */
    fun getDateWithoutTime(date: Date?): Date? {
        if (date == null) {
            return null
        }
        val calendar = GregorianCalendar()
        calendar.time = date
        calendar.set(Calendar.HOUR, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        calendar.set(Calendar.AM_PM, Calendar.AM)
        return calendar.time
    }

    /**
     * Purpose: Getting date after some day
     *
     * @param currentDate ,days
     * @return
     * @throws
     */
    fun getDateAfterDays(currentDate: Date, days: Int): Date {
        val cal = GregorianCalendar()
        cal.time = currentDate
        cal.add(Calendar.DATE, days)
        return cal.time
    }

    /**
     * Purpose: This method return current server date
     *
     * @return
     */
    /*public static Date getServerDate() {
		Calendar calendar = Calendar.getInstance();
		if (SessionInfoContainer.getDifferenceInClientServerDates() != null) {
			calendar.setTimeInMillis(new Date().getTime()
					- SessionInfoContainer.getDifferenceInClientServerDates());
		}
		return calendar.getTime();
	}*/
    fun convertUTCTimeToLocal(utcDateTime: String, format: String): String {
        try {
            val inputFormat = SimpleDateFormat(format)
            inputFormat.timeZone = TimeZone.getTimeZone("GMT+0530")
            val dateIST = inputFormat.parse(utcDateTime)
            val inputFormat2 = SimpleDateFormat(format)
            inputFormat2.timeZone = TimeZone.getTimeZone(Calendar.getInstance().timeZone.id)
            return inputFormat2.format(dateIST)
        } catch (e: Exception) {
            return utcDateTime
        }

    }


    fun splitString(serverString: String): String {
        val date: Array<String>
        var dateString = ""
        try {
            date = serverString.split(" ".toRegex(), 4).toTypedArray()
            dateString = date[0] + " " + date[1] + " " + date[2]
        } catch (ex: Exception) {
            Log.d(TAG, ex.message)
        }

        return dateString
    }

    fun getDateString(string: String): String {
        var outputString = string
        val inputFormat = SimpleDateFormat("yyyy/mm/dd")
        try {
            val date = inputFormat.parse(outputString)
            val outputFormat = SimpleDateFormat("dd MMM yyyy")
            outputString = outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }


        return outputString

    }

    fun convertDateToString(date: Date, dateFormat: String): String {
        val formatter = SimpleDateFormat(dateFormat)
        return formatter.format(date)
    }

    fun convertStringToFormatedDate(dateStr: String, inFormat: String, outFormat: String): String {

        var dateFormat: DateFormat = SimpleDateFormat(inFormat)
        var date: Date? = null
        try {
            date = dateFormat.parse(dateStr)
        } catch (e: ParseException) {
            dateFormat = SimpleDateFormat(DateUtils.DATE_TIME_FORMAT_MONTH)
            try {
                date = dateFormat.parse(dateStr)
            } catch (ex: ParseException) {
                return dateStr
            }

        }

        dateFormat = SimpleDateFormat(outFormat)
        return dateFormat.format(date)
    }

    fun convertStringToDate(date: String, dateFormat: String): Date? {
        val formatter = SimpleDateFormat(dateFormat)
        var dateToString: Date? = null
        try {
            dateToString = formatter.parse(date)
        } catch (e: ParseException) {
            Log.e(TAG, e.message, e)
        }

        return dateToString
    }

    fun isValidDate(inDate: String): Boolean {
        if (checkDate(inDate)) {
            return true
        }
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        dateFormat.isLenient = false
        try {
            dateFormat.parse(inDate.trim { it <= ' ' })
        } catch (pe: ParseException) {
            return false
        }

        return true
    }

    private fun checkDate(inDate: String): Boolean {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        dateFormat.isLenient = false
        try {
            dateFormat.parse(inDate.trim { it <= ' ' })
        } catch (pe: ParseException) {
            return false
        }

        return true
    }

    fun stringToLongDate(dateString: String): Long {
        var startDate: Long = 0
        try {
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val date = sdf.parse(dateString)
            startDate = date.time
        } catch (e: ParseException) {
            Log.e(TAG, "stringToLongDate: ", e)
        }

        return startDate
    }

}