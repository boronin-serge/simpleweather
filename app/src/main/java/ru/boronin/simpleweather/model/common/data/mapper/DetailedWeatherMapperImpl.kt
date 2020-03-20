package ru.boronin.simpleweather.model.common.data.mapper

import ru.boronin.common.utils.DEFAULT_INT
import ru.boronin.simpleweather.model.common.data.DetailedWeatherResponse
import ru.boronin.simpleweather.model.common.presentation.DayForecastModel
import ru.boronin.simpleweather.model.common.presentation.DetailedForecastModel
import ru.boronin.simpleweather.model.common.presentation.HourForecastModel
import ru.boronin.simpleweather.model.common.presentation.WeatherType
import java.util.*
import kotlin.collections.HashMap

/**
 * Created by Sergey Boronin on 13.03.2020.
 */
class DetailedWeatherMapperImpl(private val mapper: CurrentWeatherMapper) : DetailedWeatherMapper {

    private var weatherByDays = HashMap<Int, MutableList<HourForecastModel>>()

    override fun map(data: DetailedWeatherResponse): DetailedForecastModel {
        val weatherList = data.list.map {
            val weather = mapper.map(it)
            HourForecastModel(
                weather.temperature,
                weather.date,
                weather.iconId,
                weather.weatherType
            )
        }

        filterByDays(weatherList)

        val calendar = Calendar.getInstance()
        val today = calendar.get(Calendar.DAY_OF_MONTH)
        val tomorrow = calendar.apply {
            add(Calendar.DAY_OF_MONTH, 1)
        }.get(Calendar.DAY_OF_MONTH)

        return DetailedForecastModel(
            data.city.name,
            weatherByDays[today] ?: mutableListOf(),
            weatherByDays[tomorrow]?.toList() ?: listOf(),
            weatherByDays.values.map {
                val weather = countWeatherType(it).maxBy { it.value }!!.key
                val icon = countIconType(it).maxBy { it.value }!!.key
                DayForecastModel(
                    it.minBy { forecastModel -> forecastModel.temperature }?.temperature ?: DEFAULT_INT,
                    it.maxBy { forecastModel -> forecastModel.temperature }?.temperature ?: DEFAULT_INT,
                    it.first().time,
                    icon,
                    weather
                )
            }
        )
    }

    private fun countWeatherType(forecastList: List<HourForecastModel>): HashMap<WeatherType, Int> {
        val weatherTypeMap = hashMapOf<WeatherType, Int>()
        forecastList.forEach { model ->
            val count = weatherTypeMap[model.weatherType] ?: DEFAULT_INT
            weatherTypeMap[model.weatherType] = count + 1
        }
        return weatherTypeMap
    }

    private fun countIconType(forecastList: List<HourForecastModel>): HashMap<String, Int> {
        val iconTypeMap = hashMapOf<String, Int>()
        forecastList.forEach { model ->
            val count = iconTypeMap[model.iconId] ?: DEFAULT_INT
            iconTypeMap[model.iconId] = count + 1
        }
        return iconTypeMap
    }

    private fun filterByDays(weatherList: List<HourForecastModel>) {
        weatherByDays.clear()
        weatherList.forEach {
            val calendar = Calendar.getInstance()
            calendar.time = Date(it.time)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            if (weatherByDays.containsKey(day)) {
                weatherByDays[day]?.add(it)
            } else {
                weatherByDays[day] = mutableListOf(it)
            }
        }
    }
}