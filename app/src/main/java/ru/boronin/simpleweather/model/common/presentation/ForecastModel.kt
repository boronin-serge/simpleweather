package ru.boronin.simpleweather.model.common.presentation

/**
 * Created by Sergey Boronin on 06.03.2020.
 */
data class ForecastModel(
    val locationName: String,
    val date: String,
    val temperature: Int,
    val temperatureDesc: String,
    val todayWeather: List<HourForecastModel>,
    val tomorrowWeather: List<HourForecastModel>,
    val nextSevenDays: List<DayForecastModel>
)

data class HourForecastModel(
    val temperature: Int,
    val time: String,
    val weatherType: WeatherType
)

data class DayForecastModel(
    val temperature: Int,
    val day: String,
    val weatherType: WeatherType
)