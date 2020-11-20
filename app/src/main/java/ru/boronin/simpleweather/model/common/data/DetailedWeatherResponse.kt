package ru.boronin.simpleweather.model.common.data

/**
 * Created by Sergey Boronin on 06.03.2020.
 */
data class DetailedWeatherResponse(
  val list: List<WeatherResponse>,
  val city: City
)

data class City(
  val name: String,
  val country: String,
  val timezone: Int
)
