package ru.boronin.simpleweather.domain.interactor

import io.reactivex.Single
import ru.boronin.common.rx.extension.schedulers
import ru.boronin.common.utils.DEFAULT_STRING
import ru.boronin.core.api.schedulers.SchedulersProvider
import ru.boronin.simpleweather.domain.repository.WeatherRepository
import ru.boronin.simpleweather.model.common.data.mapper.CurrentWeatherMapper
import ru.boronin.simpleweather.model.common.data.mapper.DetailedWeatherMapper
import ru.boronin.simpleweather.model.common.presentation.CurrentWeatherModel
import ru.boronin.simpleweather.model.common.presentation.ForecastModel
import ru.boronin.simpleweather.model.common.presentation.HourForecastModel

/**
 * Created by Sergey Boronin on 06.03.2020.
 */
class WeatherInteractor(
    private val weatherRepository: WeatherRepository,
    private val currentWeatherMapper: CurrentWeatherMapper,
    private val detailedWeatherMapper: DetailedWeatherMapper,
    private val schedulersProvider: SchedulersProvider
) {
    private var cachedWeather: ForecastModel? = null

    fun getCurrentWeather(lat: Float, lon: Float) = weatherRepository.getCurrentWeather(lat, lon).map {
        currentWeatherMapper.map(it)
    }.schedulers(schedulersProvider)

    fun getDetailedWeather(lat: Float, lon: Float) = weatherRepository.getDetailedWeather(lat, lon).map {
        detailedWeatherMapper.map(it)
    }.schedulers(schedulersProvider)

    fun getWeather(lat: Float, lon: Float): Single<ForecastModel> {
        var currentWeatherModel: CurrentWeatherModel? = null
        return getCurrentWeather(lat, lon).flatMap {
            currentWeatherModel = it
            getDetailedWeather(lat, lon)
        }.map { detailedWeatherModel ->
            detailedWeatherModel.todayWeather.add(
                HourForecastModel(
                    currentWeatherModel!!.temperature,
                    currentWeatherModel!!.date,
                    currentWeatherModel!!.iconId,
                    currentWeatherModel!!.weatherType
                )
            )

            cachedWeather = ForecastModel(
                currentWeatherModel!!.locationName ?: DEFAULT_STRING,
                currentWeatherModel!!.date,
                currentWeatherModel!!.temperature,
                currentWeatherModel!!.temperatureDesc,
                currentWeatherModel!!.weatherType,
                currentWeatherModel!!.iconId,
                detailedWeatherModel.todayWeather.sortedBy { it.time },
                detailedWeatherModel.tomorrowWeather,
                detailedWeatherModel.nextSevenDays
            )
            cachedWeather
        }
    }

    fun getCachedWeather() = cachedWeather
}