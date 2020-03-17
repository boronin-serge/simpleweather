package ru.boronin.simpleweather.model.common.data.mapper

import ru.boronin.common.utils.DEFAULT_STRING
import ru.boronin.simpleweather.model.common.data.WeatherResponse
import ru.boronin.simpleweather.model.common.presentation.CurrentWeatherModel
import ru.boronin.simpleweather.model.common.presentation.WeatherType

/**
 * Created by Sergey Boronin on 06.03.2020.
 */
class CurrentWeatherMapperImpl : CurrentWeatherMapper {
    override fun map(data: WeatherResponse): CurrentWeatherModel {
        return CurrentWeatherModel(
            data.name,
            data.dt * 1000,
            data.weather.firstOrNull()?.icon ?: "01d",
            data.main.temp.toInt(),
            data.weather.firstOrNull()?.description ?: DEFAULT_STRING,
            WeatherType.valueOf(data.weather.firstOrNull()?.main?.toUpperCase() ?: DEFAULT_STRING)
        )
    }
}