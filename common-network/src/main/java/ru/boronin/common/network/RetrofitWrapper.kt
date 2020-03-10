package ru.boronin.common.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.reflect.KClass

class RetrofitWrapper(baseUrl: String, okHttpWrapper: OkHttpWrapper) {
  private val retrofit: Retrofit = Retrofit.Builder()
    .client(okHttpWrapper.httpClient)
    .baseUrl(baseUrl)
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .build()

  fun <T : Any> create(service: KClass<T>): T = retrofit.create(service.java)
}