package com.boreal.puertocorazon.core.utils

import android.annotation.SuppressLint
import android.widget.TextView
import com.boreal.commonutils.extensions.hideView
import com.boreal.commonutils.extensions.removeTilde
import com.boreal.commonutils.extensions.showView
import com.boreal.commonutils.text.capitalizeName
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue

/**
 * *Pattern	Example
 * *dd-MM-yy	31-01-12
 * *dd-MM-yyyy	31-01-2012
 * *MM-dd-yyyy	01-31-2012
 * *yyyy-MM-dd	2012-01-31
 * *yyyy-MM-dd hh:mm:ss	2012-01-31 11:59:59
 * *yyyy-MM-dd HH:mm:ss	2012-01-31 23:59:59
 * *yyyy-MM-dd HH:mm:ss.SSS	2012-01-31 23:59:59.999
 * *yyyy-MM-dd HH:mm:ss.SSSZ	2012-01-31 23:59:59.999+0100
 * *EEEEE MMMMM yyyy HH:mm:ss.SSSZ	Saturday November 2012 10:45:42.720+0100
 * *EEEE dd MMM yyyy
 */

@SuppressLint("SimpleDateFormat")
fun Timestamp.getFormat(format: String = "dd MMM yy", locale: Locale = Locale("es", "MX")) =
    SimpleDateFormat(format, locale).format(Date(toDate().time)).capitalizeName()

@SuppressLint("SimpleDateFormat")
fun getToday(timestamp: Timestamp = Timestamp.now()) = with(timestamp) {
    "${
        getNameOfDay().subSequence(IntRange(0, 2))
    }, ${getDay()} de ${getNameOfMonth()} del ${getYear()}"
}

fun getAMPM(format: String = "a") = with(Timestamp.now()) {
    getHour(format).replace(" ", "").uppercase()
}

fun getHourAMPM(format: String = "HH:mm", timestamp: Timestamp = Timestamp.now()) =
    with(timestamp) {
        "${getHour(format).replace(" ", "")} ${getAMPM()}".uppercase()
    }

fun getEndHour() = "23:59 P.M."

fun Timestamp.getFormatWithDay(
    format: String = "EEEE dd MMM yy HH:mm:ss",
    locale: Locale = Locale("es", "MX")
) =
    SimpleDateFormat(format, locale).format(Date(toDate().time)).capitalizeName()

fun Timestamp.getDay(format: String = "dd", locale: Locale = Locale("es", "MX")) =
    SimpleDateFormat(format, locale).format(Date(toDate().time)).capitalize(locale)

fun Timestamp.getMonth(format: String = "MM", locale: Locale = Locale("es", "MX")) =
    SimpleDateFormat(format, locale).format(Date(toDate().time)).capitalize(locale)

fun Timestamp.getNameOfMonth(format: String = "MMM", locale: Locale = Locale("es", "MX")) =
    SimpleDateFormat(format, locale).format(Date(toDate().time)).capitalize(locale)

fun Timestamp.getNameOfDay(format: String = "EEEE", locale: Locale = Locale("es", "MX")) =
    SimpleDateFormat(format, locale).format(Date(toDate().time)).capitalize(locale).removeTilde()

fun Timestamp.getYear(format: String = "yyyy", locale: Locale = Locale("es", "MX")) =
    SimpleDateFormat(format, locale).format(Date(toDate().time)).capitalize(locale)


fun Timestamp.getHour(format: String = "HH", locale: Locale = Locale("es", "MX")) =
    SimpleDateFormat(format, locale).format(Date(toDate().time)).capitalize(locale).replace("\\s".toRegex(), "")

fun Timestamp.isValidDate() = this != Timestamp(0L, 0)

fun Timestamp.nowSumDays(
    days: Int = 7 * 14,
    setHour: String = "23:59:59",
    locale: Locale = Locale("es", "MX"), receivedTime: Boolean = false
) =
    with(Calendar.getInstance()) {
        time =
            SimpleDateFormat("dd MMM yy HH:mm:ss", locale).parse(
                "${
                    if (receivedTime) {
                        this@nowSumDays.getFormat()
                    } else {
                        Timestamp.now().getFormat()
                    }
                } $setHour"
            )
        add(Calendar.DAY_OF_YEAR, days)
        Timestamp(Date(time.time))
    }

fun Timestamp.sumDays(
    days: Int = 7 * 14,
    setHour: String = "23:59:59",
    locale: Locale = Locale("es", "MX")
) =
    with(Calendar.getInstance()) {
        time = SimpleDateFormat(
            "dd MMM yy HH:mm:ss",
            locale
        ).parse("${this@sumDays.getFormat()} $setHour")
        add(Calendar.DAY_OF_YEAR, days)
        Timestamp(Date(time.time))
    }

fun Timestamp.secondsBetweenDays(evaluateDate: Timestamp, absolute: Boolean = false) =
    if (absolute) ((toDate().time - evaluateDate.toDate().time) / 1000).absoluteValue else
        (toDate().time - evaluateDate.toDate().time) / 1000


fun Timestamp.minutesBetweenDays(evaluateDate: Timestamp, absolute: Boolean = false) =
    if (absolute) (((toDate().time - evaluateDate.toDate().time) / 1000) / 60).absoluteValue else
        ((toDate().time - evaluateDate.toDate().time) / 1000) / 60


fun Timestamp.hoursBetweenDays(evaluateDate: Timestamp, absolute: Boolean = false): Long {
    val seconds = (toDate().time - evaluateDate.toDate().time) / 1000
    val minutes = seconds / 60
    return if (absolute) (minutes / 60).absoluteValue else minutes / 60
}

infix fun Timestamp.hoursBetweenDays(evaluateDate: Timestamp): Long {
    val seconds = (toDate().time - evaluateDate.toDate().time) / 1000
    val minutes = seconds / 60
    return (minutes / 60).absoluteValue
}

fun Timestamp.daysBetweenDays(evaluateDate: Timestamp, absolute: Boolean = false): Long {
    val seconds = (toDate().time - evaluateDate.toDate().time) / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    return if (absolute) (hours / 24).absoluteValue else hours / 24
}

fun Timestamp.setMaxHour() =
    with(Calendar.getInstance()) {
        time = SimpleDateFormat(
            "dd MMM yy HH:mm:ss",
            Locale("es", "MX")
        ).parse("${this@setMaxHour.getFormat()} 23:59:59")
        Timestamp(Date(time.time))
    }

fun Timestamp.setHour(hourAndMinute: String) =
    with(Calendar.getInstance()) {
        time = SimpleDateFormat(
            "dd MM yy HH:mm:ss",
            Locale("es", "MX")
        ).parse(
            "${this@setHour.getFormat("dd MM yy")} ${
                hourAndMinute.replace("A.M.", "").replace(" ", "")
                    .replace("P.M.", "").trimIndent()
            }:00"
        )
        Timestamp(Date(time.time))
    }

fun Timestamp.isBirthDayRegisterValid(messageErrorHolder: TextView, errorMessage: String) =
    when (this.seconds) {
        0L -> {
            messageErrorHolder.showView()
            messageErrorHolder.text = errorMessage
            false
        }
        else -> {
            messageErrorHolder.hideView()
            messageErrorHolder.text = ""
            true
        }

    }

fun String.birthDayToFormat(locale: Locale = Locale("es", "MX")) = with(Calendar.getInstance()) {
    time = SimpleDateFormat("dd / MM / yy", locale).parse("${this@birthDayToFormat}")
    Timestamp(Date(time.time))
}

fun String.toTimestamp(
    format: String = "EEEE dd MMM yy HH:mm:ss",
    locale: Locale = Locale("es", "MX")
) = with(Calendar.getInstance()) {
    time = SimpleDateFormat(format, locale).parse("${this@toTimestamp}")
    Timestamp(Date(time.time))
}

fun String.toTimestampString(
    initialDate: String,
    locale: Locale = Locale("es", "MX")
) = with(Calendar.getInstance()) {
    time = SimpleDateFormat("dd MM yy HH:mm:ss", locale).parse(
        "$initialDate ${this@toTimestampString}:00"
    )
    Timestamp(Date(time.time))
}

fun Long.toFormat() = with(Timestamp(Date(this))) {
    "${
        getNameOfDay().subSequence(
            IntRange(
                0,
                2
            )
        )
    }, ${getDay()} de ${getNameOfMonth()} del ${getYear()}"
}

fun Long.toTimeStamp() = Timestamp(Date(this))

infix fun Timestamp.greaterThan(nextTimestamp: Timestamp) = this > nextTimestamp
infix fun Timestamp.equals(nextTimestamp: Timestamp) = this == nextTimestamp