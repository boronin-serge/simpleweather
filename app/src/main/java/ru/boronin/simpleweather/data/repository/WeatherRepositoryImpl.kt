package ru.boronin.simpleweather.data.repository

import ru.boronin.simpleweather.data.network.NetworkSource
import ru.boronin.simpleweather.domain.repository.WeatherRepository

/**
 * Created by Sergey Boronin on 06.03.2020.
 */
class WeatherRepositoryImpl(private val source: NetworkSource): WeatherRepository {
    override fun getCurrentWeather(lat: Float, lon: Float) = source.getWeather(lat, lon)
    override fun getDetailedWeather(lat: Float, lon: Float) = source.getDetailedWeather(lat, lon)
}