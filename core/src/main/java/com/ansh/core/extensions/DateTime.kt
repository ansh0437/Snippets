package com.ansh.core.extensions

import com.ansh.core.utilities.DateUtil

fun String.toLocalDate(format: String = DateUtil.DATE_FORMAT) =
    DateUtil.convertUTCToLocal(this, format)

fun String.toLocalDateTime(format: String = DateUtil.DATE_TIME_FORMAT) =
    DateUtil.convertLocalToUTC(this, format)

fun String.toFormattedDate(inFormat: String, outFormat: String) =
    DateUtil.convertDateFormat(this, inFormat, outFormat)

fun String.toMillis(format: String = DateUtil.DATE_TIME_FORMAT) =
    DateUtil.convertToMillis(this, format)