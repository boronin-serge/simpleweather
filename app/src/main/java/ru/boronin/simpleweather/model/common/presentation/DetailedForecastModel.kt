package ru.boronin.simpleweather.model.common.presentation

/**
 * Created by Sergey Boronin on 06.03.2020.
 */
data class DetailedForecastModel(
    val locationName: String?,
    val todayWeather: MutableList<HourForecastModel>,
    val tomorrowWeather: List<HourForecastModel>,
    val nextSevenDays: List<DayForecastModel>
)