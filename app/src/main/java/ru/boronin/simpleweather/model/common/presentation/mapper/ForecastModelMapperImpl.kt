package ru.boronin.simpleweather.model.common.presentation.mapper

import ru.boronin.simpleweather.data.databse.DayForecastEntity
import ru.boronin.simpleweather.data.databse.ForecastEntity
import ru.boronin.simpleweather.data.databse.HourForecastEntity
import ru.boronin.simpleweather.model.common.presentation.DayForecastModel
import ru.boronin.simpleweather.model.common.presentation.ForecastModel
import ru.boronin.simpleweather.model.common.presentation.HourForecastModel
import ru.boronin.simpleweather.model.common.presentation.WeatherType

class ForecastModelMapperImpl : ForecastModelMapper {
  override fun map(data: ForecastModel) = ForecastEntity(
    data.locationName,
    data.date,
    data.temperature,
    data.temperatureDesc,
    data.feelsLike,
    data.weatherType.name,
    data.iconId,
    data.todayWeather.map { hourModelToEntity(it) },
    data.tomorrowWeather.map { hourModelToEntity(it) },
    data.nextFiveDays.map { dayModelToEntity(it) }
  )

  override fun map(data: ForecastEntity) = ForecastModel(
    data.locationName,
    data.date,
    data.temperature,
    data.temperatureDesc,
    data.feelsLike,
    WeatherType.find(data.weatherType),
    data.iconId,
    data.todayWeather.map { hourEntityToModel(it) },
    data.tomorrowWeather.map { hourEntityToModel(it) },
    data.nextFiveDays.map { dayEntityToModel(it) }
  )

  // region private

  private fun hourModelToEntity(model: HourForecastModel) = HourForecastEntity(
    model.temperature,
    model.time,
    model.iconId,
    model.weatherType.name
  )

  private fun hourEntityToModel(entity: HourForecastEntity) = HourForecastModel(
    entity.temperature,
    entity.time,
    entity.iconId,
    WeatherType.find(entity.weatherType)
  )

  private fun dayModelToEntity(model: DayForecastModel) = DayForecastEntity(
    model.minTemp,
    model.maxTemp,
    model.day,
    model.iconId,
    model.weatherType.name
  )

  private fun dayEntityToModel(entity: DayForecastEntity) = DayForecastModel(
    entity.minTemp,
    entity.maxTemp,
    entity.day,
    entity.iconId,
    WeatherType.find(entity.weatherType)
  )

  // endregion
}
