package ru.boronin.simpleweather.utils.helpers

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.*

/**
 * Created by Sergey Boronin on 19.04.2019.
 */
object DateHelper {

  fun parseDateToMillis(date: String?): Long {
    return parseIsoStringToDate(date, true).time
  }

  fun parseDateToDays(date: String?): Long {
    return parseIsoStringToDate(date, true).time / 1000 / 60 / 60 / 24
  }

  /**
   * yyyy-MM-dd -> dd/MM
   */
  fun parseStringToDayMonth(rawDate: String): String {
    val date = getInstance().apply {
      time = parseStringToDate(rawDate, true)
    }
    return calendarToDayMonth(date)
  }

  /**
   * yyyy-MM-dd -> dd/MM
   */
  fun parseIsoStringToDayMonth(rawDate: String): String {
    val date = getInstance().apply {
      time = parseIsoStringToDate(rawDate, true)
    }
    return calendarToDayMonth(date)
  }

  /**
   * yyyy-MM-dd'T'HH:mm:ss -> dd month yyyy
   */
  fun parseIsoStringToDayMonthYear(rawDate: String, monthArr: Array<String>): String {
    return try {
      val date = getInstance().apply {
        time = parseIsoStringToDate(rawDate, true)
      }

      val day = date.get(DAY_OF_MONTH)
      val month = monthArr[date.get(MONTH) - 1]
      val year = date.get(YEAR)

      val zeroDaySymbol = if (day < 10) "0" else ""

      "$zeroDaySymbol$day $month $year"
    } catch (e: Exception) {
      "Дата неизвестна"
    }
  }

  /**
   * yyyy-MM-dd -> dd month year, hh:mm
   */
  fun parseStringToDayMonthYearHourMin(rawDate: String, monthArr: Array<String>): String {
    return try {
      val date = getInstance().apply {
        time = parseIsoStringToDate(rawDate, true)
      }

      val day = date.get(DAY_OF_MONTH)
      val month = monthArr[date.get(MONTH) - 1].substring(0,3)
      val year = date.get(YEAR)
      val hour = date.get(HOUR_OF_DAY)
      val min = date.get(MINUTE)

      val zeroDaySymbol = if (day < 10) "0" else ""
      val zeroHourSymbol = if (hour < 10) "0" else ""
      val zeroMinSymbol = if (min < 10) "0" else ""

      "$zeroDaySymbol$day $month $year, $zeroHourSymbol$hour:$zeroMinSymbol$min"
    } catch (e: Exception) {
      "Дата неизвестна"
    }
  }


  /**
   * yyyy-MM-dd -> dd.MM.yyyy
   */
  fun parseStringToDayMonthYearWithDotes(
    rawDate: String
  ) = parseStringToDayMonthYearWithTime(rawDate, ".")

  /**
   * yyyy-MM-dd -> dd/MM/yyyy
   */
  fun parseStringToDayMonthYearWithTime(rawDate: String, delimiter: String = "/"): String {
    return parseStringToDayMonthYearWithTime(parseIsoStringToDate(rawDate, true).time)
  }

  /**
   * millisecs -> dd/MM/yyyy HH:mm
   */
  fun parseStringToDayMonthYearWithTime(rawDate: Long, delimiter: String = "/"): String {
    return try {
      val date = getInstance().apply {
        time = Date(rawDate)
      }

      val day = date.get(DAY_OF_MONTH)
      val month = date.get(MONTH) + 1
      val year = date.get(YEAR)

      val zeroDaySymbol = if (day < 10) "0" else ""
      val zeroMonthSymbol = if (month < 10) "0" else ""

      val time = parseIsoStringToTime(rawDate)

      "$zeroDaySymbol$day$delimiter$zeroMonthSymbol$month$delimiter$year $time"
    } catch (e: ParseException) {
      "Дата неизвестна"
    }
  }

  /**
   * yyyy-MM-dd'T'HH:mm:ss -> HH:mm
   */
  fun parseIsoStringToTime(rawDate: String?): String {
    return parseIsoStringToTime(parseIsoStringToDate(rawDate, false).time)
  }


  /**
   * millisecs -> HH:mm
   */
  fun parseIsoStringToTime(rawDate: Long): String {
    return try {
      val date = getInstance().apply {
        time = Date(rawDate)
      }

      val hours = date.get(HOUR_OF_DAY)
      val minutes = date.get(MINUTE)

      val zeroHourSymbol = if (hours < 10) "0" else ""
      val zeroMinSymbol = if (minutes < 10) "0" else ""

      "$zeroHourSymbol$hours:$zeroMinSymbol$minutes"
    } catch (e: ParseException) {
      "Дата неизвестна"
    }
  }

  // region private

  /**
   * yyyy-MM-dd'T'HH:mm:ss -> Date()
   */
  private fun parseIsoStringToDate(date: String?, byMsk: Boolean): Date {
    return try {
      val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
      if (byMsk) {
        formatter.timeZone = TimeZone.getTimeZone("Europe/Moscow")
      }
      return formatter.parse(date!!) ?: throw Exception()
    } catch (e: Exception) {
      Date()
    }
  }

  /**
   * yyyy-MM-dd -> Date()
   */
  private fun parseStringToDate(date: String?, byMsk: Boolean): Date {
    return try {
      val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
      if (byMsk) {
        formatter.timeZone = TimeZone.getTimeZone("Europe/Moscow")
      }
      return formatter.parse(date!!) ?: throw Exception()
    } catch (e: Exception) {
      Date()
    }
  }

  /**
   * yyyy-MM-dd -> Date()
   */
  private fun calendarToDayMonth(calendar: Calendar): String {
    return try {
      val day = calendar.get(DAY_OF_MONTH)
      val month = calendar.get(MONTH) + 1

      val zeroDaySymbol = if (day < 10) "0" else ""
      val zeroMonthSymbol = if (month < 10) "0" else ""

      "$zeroDaySymbol$day/$zeroMonthSymbol$month"
    } catch (e: Exception) {
      "Дата неизвестна"
    }
  }

  // endregion
}