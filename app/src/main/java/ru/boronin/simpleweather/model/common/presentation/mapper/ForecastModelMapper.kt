package ru.boronin.simpleweather.model.common.presentation.mapper

import ru.boronin.simpleweather.data.databse.ForecastEntity
import ru.boronin.simpleweather.model.common.presentation.ForecastModel

interface ForecastModelMapper {
  fun map(data: ForecastModel): ForecastEntity
  fun map(data: ForecastEntity): ForecastModel
}
