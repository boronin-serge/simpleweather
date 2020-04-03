package ru.boronin.simpleweather.data.databse

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ModelConverters {
  companion object {
    var gson = Gson()
    @TypeConverter
    @JvmStatic
    fun stringToSomeObjectList(data: String?): List<HourForecastEntity> {
      if (data == null) {
        return listOf()
      }
      val listType: Type = object : TypeToken<List<HourForecastEntity?>?>() {}.type
      return gson.fromJson(data, listType)
    }

    @TypeConverter
    @JvmStatic
    fun someObjectListToString(someObjects: List<HourForecastEntity?>?): String {
      return gson.toJson(someObjects)
    }

    @TypeConverter
    @JvmStatic
    fun stringToDayObjectList(data: String?): List<DayForecastEntity> {
      if (data == null) {
        return listOf()
      }
      val listType: Type = object : TypeToken<List<DayForecastEntity?>?>() {}.type
      return gson.fromJson(data, listType)
    }

    @TypeConverter
    @JvmStatic
    fun someDayListToString(someObjects: List<DayForecastEntity?>?): String {
      return gson.toJson(someObjects)
    }
  }
}