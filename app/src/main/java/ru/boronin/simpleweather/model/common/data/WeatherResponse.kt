package ru.boronin.simpleweather.model.common.data

/**
 * Created by Sergey Boronin on 06.03.2020.
 */
data class WeatherResponse(
    val weather: List<Weather>,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Long,
    val timezone: Int,
    val name: String
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Main(
    val temp: Float,
    val feels_like: Float,
    val temp_min: Float,
    val temp_max: Float,
    val pressure: Int,
    val humidity: Int
)

data class Wind(
    val speed: Int,
    val deg: Int
)

data class Clouds(
    val all: Int
)
