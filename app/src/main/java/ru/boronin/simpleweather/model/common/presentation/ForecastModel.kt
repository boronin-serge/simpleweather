package ru.boronin.simpleweather.model.common.presentation

/**
 * Created by Sergey Boronin on 06.03.2020.
 */
data class ForecastModel(
    val locationName: String,
    val date: Long,
    val temperature: Int,
    val temperatureDesc: String,
    val weatherType: WeatherType,
    val iconId: String,
    val todayWeather: List<HourForecastModel>,
    val tomorrowWeather: List<HourForecastModel>,
    val nextSevenDays: List<DayForecastModel>
)

data class HourForecastModel(
    val temperature: Int,
    val time: Long,
    val iconId: String,
    val weatherType: WeatherType
)

data class DayForecastModel(
    val temperature: Int,
    val day: Long,
    val iconId: String,
    val weatherType: WeatherType
)