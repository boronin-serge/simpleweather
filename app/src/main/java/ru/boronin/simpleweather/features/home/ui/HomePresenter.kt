package ru.boronin.simpleweather.features.home.ui

import com.tbruyelle.rxpermissions2.Permission
import ru.boronin.common.rx.extension.progress
import ru.boronin.common.utils.DEFAULT_BOOLEAN
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
    private var currentWeatherMode  = HomeFragment.WeatherMode.TODAY

    override fun onFirstViewAttach() {
        view?.setWeatherMode(currentWeatherMode)
        getCachedForecast()
    }

    override fun checkWeatherAction() {
        locationProvider.getLastKnownLocation { latLng ->
            val hasConnection = view?.hasConnection() ?: DEFAULT_BOOLEAN

            if (hasConnection) {
                loadForecast(latLng.lat, latLng.lng)
            } else {
                view?.showErrorToast()
                getCachedForecast()
            }
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

    private fun loadForecast(lat: Float, lng: Float) {
        subscriptions add  interactor.getWeather(lat, lng)
            .progress { showProgress(it) }
            .subscribe({
                if (it != null) {
                    handleForecast(it)
                }
            }, {
                view?.showErrorToast()
                getCachedForecast()
            })
    }

    private fun getCachedForecast() {
        subscriptions add  interactor.getCachedWeather()
            .progress { showProgress(it) }
            .subscribe({
                if (it != null) {
                    handleForecast(it)
                }
            }, {
                view?.showErrorPage()
            })
    }

    private fun handleForecast(forecastModel: ForecastModel) {
        lastWeather = forecastModel
        view?.updateView(forecastModel)
        lastWeather?.todayWeather?.let {
            if (it.isEmpty()) {
                currentWeatherMode = HomeFragment.WeatherMode.TOMORROW
                view?.enableToday(false)
            } else {
                view?.enableToday(true)
            }
        }

        when(currentWeatherMode) {
            HomeFragment.WeatherMode.TODAY -> showTodayWeatherAction()
            HomeFragment.WeatherMode.TOMORROW -> showTomorrowWeatherAction()
        }
    }

    private fun showProgress(show: Boolean) {
        val isActionHide = show.not()
        if (isActionHide || lastWeather == null) view?.setVisibleLoading(show)
    }

    // endregion


}