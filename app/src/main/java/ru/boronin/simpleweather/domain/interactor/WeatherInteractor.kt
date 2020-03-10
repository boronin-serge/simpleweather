package ru.boronin.simpleweather.domain.interactor

import ru.boronin.common.rx.extension.schedulers
import ru.boronin.core.api.schedulers.SchedulersProvider
import ru.boronin.simpleweather.domain.repository.WeatherRepository
import ru.boronin.simpleweather.model.common.data.mapper.WeatherMapper

/**
 * Created by Sergey Boronin on 06.03.2020.
 */
class WeatherInteractor(
    private val weatherRepository: WeatherRepository,
    private val mapper: WeatherMapper,
    private val schedulersProvider: SchedulersProvider
) {
    fun getWeather(lat: Float, lon: Float) = weatherRepository.getWeather(lat, lon).map {
        mapper.map(it)
    }.schedulers(schedulersProvider)
}