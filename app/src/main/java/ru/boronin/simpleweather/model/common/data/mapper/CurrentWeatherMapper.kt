package ru.boronin.simpleweather.model.common.data.mapper

import ru.boronin.simpleweather.model.common.data.WeatherResponse
import ru.boronin.simpleweather.model.common.presentation.CurrentWeatherModel

/**
 * Created by Sergey Boronin on 06.03.2020.
 */
interface CurrentWeatherMapper {
  fun map(data: WeatherResponse): CurrentWeatherModel
}
