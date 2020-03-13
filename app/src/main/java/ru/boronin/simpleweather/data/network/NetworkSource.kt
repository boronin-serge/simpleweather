package ru.boronin.simpleweather.data.network

import ru.boronin.common.network.RetrofitWrapper
import ru.boronin.simpleweather.data.network.ApiConfig.APP_KEY

/**
 * Created by Sergey Boronin on 06.03.2020.
 */
class NetworkSource(retrofitWrapper: RetrofitWrapper) {

    private val network = retrofitWrapper.create(WeatherApi::class)

    fun getWeather(lat: Float, lon: Float) = network.getWeather(lat, lon, APP_KEY)

    fun getDetailedWeather(lat: Float, lon: Float) = network.getDetailedWeather(lat, lon, APP_KEY)
}