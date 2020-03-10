package ru.boronin.simpleweather.model.common.data.mapper

import ru.boronin.simpleweather.model.common.data.WeatherResponse
import ru.boronin.simpleweather.model.common.presentation.ForecastModel

/**
 * Created by Sergey Boronin on 06.03.2020.
 */
interface WeatherMapper {
    fun map(data: WeatherResponse): ForecastModel
}