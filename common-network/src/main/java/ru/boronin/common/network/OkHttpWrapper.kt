package ru.boronin.common.network

import android.util.Log
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

private const val READ_TIMEOUT = 15L
private const val WRITE_TIMEOUT = 20L
private const val CONNECT_TIMEOUT = 10L

class OkHttpWrapper {
  val httpClient: OkHttpClient

  init {
    val builder = OkHttpClient.Builder()
      .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
      .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
      .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)

    if (BuildConfig.DEBUG) {
      builder.addInterceptor(
        HttpLoggingInterceptor(
          object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
              Log.d(javaClass::getName.toString(), message)
            }

          }
        ).apply {
          level = HttpLoggingInterceptor.Level.BODY
        }
      ).addNetworkInterceptor(StethoInterceptor())
    }

    httpClient = builder.build()
  }
}