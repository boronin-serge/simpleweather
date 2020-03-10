package ru.boronin.simpleweather.model.common.data.mapper

import ru.boronin.common.utils.DEFAULT_STRING
import ru.boronin.simpleweather.model.common.data.WeatherResponse
import ru.boronin.simpleweather.model.common.presentation.ForecastModel

/**
 * Created by Sergey Boronin on 06.03.2020.
 */
class WeatherMapperImpl : WeatherMapper {
    override fun map(data: WeatherResponse): ForecastModel {
        return ForecastModel(
            data.name,
            data.dt.toString(),
            data.main.temp.toInt(),
            data.weather.firstOrNull()?.description ?: DEFAULT_STRING,
            listOf(),
            listOf(),
            listOf()
        )
    }
}