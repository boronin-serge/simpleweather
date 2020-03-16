package ru.boronin.simpleweather.model.common.data.mapper

import ru.boronin.simpleweather.model.common.data.DetailedWeatherResponse
import ru.boronin.simpleweather.model.common.presentation.DayForecastModel
import ru.boronin.simpleweather.model.common.presentation.DetailedForecastModel
import ru.boronin.simpleweather.model.common.presentation.HourForecastModel
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
            weatherByDays[today]?.toList() ?: listOf(),
            weatherByDays[tomorrow]?.toList() ?: listOf(),
            weatherByDays.values.map {
                val averageTemp = it.sumBy { it.temperature } / it.size
                DayForecastModel(
                    averageTemp,
                    it.first().time.toString(),
                    it.first().weatherType
                )
            }
        )
    }

    private fun filterByDays(weatherList: List<HourForecastModel>) {
        weatherByDays.clear()
        weatherList.forEach {
            val calendar = Calendar.getInstance()
            calendar.time = Date(it.time * 1000)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            if (weatherByDays.containsKey(day)) {
                weatherByDays[day]?.add(it)
            } else {
                weatherByDays[day] = mutableListOf(it)
            }
        }
    }
}