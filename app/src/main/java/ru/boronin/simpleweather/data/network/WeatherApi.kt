package ru.boronin.simpleweather.data.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import ru.boronin.simpleweather.model.common.data.WeatherResponse

/**
 * Created by Sergey Boronin on 06.03.2020.
 */
interface WeatherApi {

    @GET("data/2.5/weather?lang=RU&units=metric")
    fun getWeatger(
        @Query("lat") lat: Float,
        @Query("lon") lon: Float,
        @Query("appid") appid: String
    ): Single<WeatherResponse>
}