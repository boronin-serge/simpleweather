package ru.boronin.simpleweather.domain.interactor

import io.reactivex.Completable
import io.reactivex.Single
import ru.boronin.common.rx.extension.schedulers
import ru.boronin.common.utils.DEFAULT_STRING
import ru.boronin.core.api.schedulers.SchedulersProvider
import ru.boronin.simpleweather.domain.repository.WeatherRepository
import ru.boronin.simpleweather.model.common.data.mapper.CurrentWeatherMapper
import ru.boronin.simpleweather.model.common.data.mapper.DetailedWeatherMapper
import ru.boronin.simpleweather.model.common.presentation.CurrentWeatherModel
import ru.boronin.simpleweather.model.common.presentation.DetailedForecastModel
import ru.boronin.simpleweather.model.common.presentation.ForecastModel

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

    fun getWeather(lat: Float, lon: Float): Single<ForecastModel?> {
        var currentWeather: CurrentWeatherModel? = null
        return getCurrentWeather(lat, lon).flatMap {
            currentWeather = it
            getDetailedWeather(lat, lon)
        }.map { detailedWeather ->
            cachedWeather = ForecastModel(
                currentWeather!!.locationName ?: DEFAULT_STRING,
                currentWeather!!.date,
                currentWeather!!.temperature,
                currentWeather!!.temperatureDesc,
                currentWeather!!.feelsLike,
                currentWeather!!.weatherType,
                currentWeather!!.iconId,
                detailedWeather.todayWeather.sortedBy { it.time },
                detailedWeather.tomorrowWeather,
                detailedWeather.nextFiveDays.takeLast(5)
            )
            cachedWeather?.also {
                saveForecast(it).subscribe()
            }
        }.schedulers(schedulersProvider)
    }

    fun getLastLoadedWeather() = cachedWeather

    fun getCachedWeather() = weatherRepository.getCachedForecast()
        .doOnSuccess { cachedWeather = it }
        .schedulers(schedulersProvider)

    // region private

    private fun saveForecast(forecastModel: ForecastModel) = Completable.fromCallable {
        weatherRepository.saveForecast(forecastModel)
    }.schedulers(schedulersProvider)

    private fun getCurrentWeather(lat: Float, lon: Float): Single<CurrentWeatherModel> {
        return weatherRepository.getCurrentWeather(lat, lon).map {
            currentWeatherMapper.map(it)
        }
    }

    private fun getDetailedWeather(lat: Float, lon: Float): Single<DetailedForecastModel> {
        return weatherRepository.getDetailedWeather(lat, lon).map {
            detailedWeatherMapper.map(it)
        }
    }

    // endregion
}