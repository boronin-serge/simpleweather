package ru.boronin.simpleweather.domain.repository

import io.reactivex.Single
import ru.boronin.simpleweather.model.common.data.DetailedWeatherResponse
import ru.boronin.simpleweather.model.common.data.WeatherResponse

/**
 * Created by Sergey Boronin on 06.03.2020.
 */
interface WeatherRepository {
    fun getCurrentWeather(lat: Float, lon: Float): Single<WeatherResponse>
    fun getDetailedWeather(lat: Float, lon: Float): Single<DetailedWeatherResponse>
}