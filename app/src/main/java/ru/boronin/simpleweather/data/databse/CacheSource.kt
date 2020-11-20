package ru.boronin.simpleweather.data.databse

class CacheSource(private val database: ForecastDatabase) {
  fun saveForecast(forecast: ForecastEntity) {
    database.forecastDao().deleteAll()
    database.forecastDao().insertForecast(forecast)
  }

  fun getForecast() = database.forecastDao().getLastForecast()
}
