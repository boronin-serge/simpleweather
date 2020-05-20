package ru.boronin.simpleweather.data.repository

import io.reactivex.Maybe
import ru.boronin.simpleweather.data.databse.CacheSource
import ru.boronin.simpleweather.data.network.NetworkSource
import ru.boronin.simpleweather.domain.repository.WeatherRepository
import ru.boronin.simpleweather.model.common.presentation.ForecastModel
import ru.boronin.simpleweather.model.common.presentation.mapper.ForecastModelMapper

/**
 * Created by Sergey Boronin on 06.03.2020.
 */
class WeatherRepositoryImpl(
    private val networkSource: NetworkSource,
    private val cacheSource: CacheSource,
    private val mapper: ForecastModelMapper
): WeatherRepository {
    override fun getCurrentWeather(lat: Float, lon: Float) = networkSource.getWeather(lat, lon)
    override fun getDetailedWeather(lat: Float, lon: Float) = networkSource.getDetailedWeather(lat, lon)
    override fun saveForecast(forecast: ForecastModel) {
        val entity = mapper.map(forecast)
        cacheSource.saveForecast(entity)
    }

    override fun getCachedForecast(): Maybe<ForecastModel> {
        return Maybe.create {
            val forecast = cacheSource.getForecast()
            if (forecast != null) {
                it.onSuccess(mapper.map(forecast))
            } else {
                it.onComplete()
            }
        }
    }
}