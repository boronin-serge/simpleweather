package ru.boronin.simpleweather.features.home.ui

import android.util.Log
import com.tbruyelle.rxpermissions2.Permission
import ru.boronin.common.rx.extension.progress
import ru.boronin.common.rx.extension.schedulers
import ru.boronin.core.api.location.LocationProvider
import ru.boronin.simpleweather.common.presentation.location.ILocationPresenter
import ru.boronin.simpleweather.common.presentation.mvp.BasePresenter
import ru.boronin.simpleweather.domain.interactor.WeatherInteractor
import ru.boronin.simpleweather.features.home.navigator.HomeNavigator
import ru.boronin.simpleweather.model.common.presentation.ForecastModel

class HomePresenter(
    private val navigator: HomeNavigator,
    private val interactor: WeatherInteractor,
    private val locationProvider: LocationProvider
) : BasePresenter<HomeView>(), HomeAction, ILocationPresenter {

    private var lastWeather: ForecastModel? = null
    private var currentWeatherMode: HomeFragment.WeatherMode = HomeFragment.WeatherMode.TODAY

    override fun onFirstViewAttach() {
        view?.setWeatherMode(currentWeatherMode)
    }

    override fun checkWeatherAction() {
        locationProvider.getLastKnownLocation { latLng ->
            val task = if (view?.hasConnection()!!) {
                interactor.getWeather(latLng.lat, latLng.lng)
            } else {
                interactor.getCachedWeather()
            }
            subscriptions add task
                .progress { if (it.not() || lastWeather == null) view?.setVisibleLoading(it) }
                .subscribe({
                    lastWeather = it
                    view?.updateView(it)
                    when(currentWeatherMode) {
                        HomeFragment.WeatherMode.TODAY -> showTodayWeatherAction()
                        HomeFragment.WeatherMode.TOMORROW -> showTomorrowWeatherAction()
                    }
                }, {
                    Log.d("Log", "dfg")
                })
        }
    }

    override fun showTodayWeatherAction() {
        lastWeather?.todayWeather?.let { view?.updateList(it) }
        currentWeatherMode = HomeFragment.WeatherMode.TODAY
        view?.setWeatherMode(currentWeatherMode)
    }

    override fun showTomorrowWeatherAction() {
        lastWeather?.tomorrowWeather?.let { view?.updateList(it) }
        currentWeatherMode = HomeFragment.WeatherMode.TOMORROW
        view?.setWeatherMode(currentWeatherMode)
    }

    override fun showNextFiveDaysWeatherAction() {
        navigator.openFutureForecast()
    }

    // region ILocationPresenter

    override fun onLocationNeeded() {
        view?.checkLocationPermissions()
    }

    override fun onPermissionGranted(permission: Permission, isOnDemand: Boolean) {
        checkWeatherAction()
    }

    // endregion


    // region private

    private fun checkWeather() {

    }

    // endregion


}