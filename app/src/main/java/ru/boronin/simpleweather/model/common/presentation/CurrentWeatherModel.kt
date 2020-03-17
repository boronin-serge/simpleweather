package ru.boronin.simpleweather.model.common.presentation

/**
 * Created by Sergey Boronin on 13.03.2020.
 */
data class CurrentWeatherModel(
    val locationName: String?,
    val date: Long,
    val iconId: String,
    val temperature: Int,
    val temperatureDesc: String,
    val weatherType: WeatherType
)