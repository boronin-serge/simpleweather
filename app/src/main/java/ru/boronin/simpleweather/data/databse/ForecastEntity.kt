package ru.boronin.simpleweather.data.databse

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecast_table")
data class ForecastEntity(
  @ColumnInfo val locationName: String,
  @PrimaryKey @ColumnInfo val date: Long,
  @ColumnInfo val temperature: Int,
  @ColumnInfo val temperatureDesc: String,
  @ColumnInfo val feelsLike: Int,
  @ColumnInfo val weatherType: String,
  @ColumnInfo val iconId: String,
  @ColumnInfo val todayWeather: List<HourForecastEntity>,
  @ColumnInfo val tomorrowWeather: List<HourForecastEntity>,
  @ColumnInfo val nextFiveDays: List<DayForecastEntity>
)

data class HourForecastEntity(
  val temperature: Int,
  val time: Long,
  val iconId: String,
  val weatherType: String
)

data class DayForecastEntity(
  val minTemp: Int,
  val maxTemp: Int,
  val day: Long,
  val iconId: String,
  val weatherType: String
)