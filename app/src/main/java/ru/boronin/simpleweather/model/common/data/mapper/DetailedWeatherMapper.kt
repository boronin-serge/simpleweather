package ru.boronin.simpleweather.model.common.data.mapper

import ru.boronin.simpleweather.model.common.data.DetailedWeatherResponse
import ru.boronin.simpleweather.model.common.presentation.DetailedForecastModel

/**
 * Created by Sergey Boronin on 13.03.2020.
 */
interface DetailedWeatherMapper {
  fun map(data: DetailedWeatherResponse): DetailedForecastModel
}
