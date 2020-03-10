package ru.boronin.simpleweather.features.home.ui

import com.tbruyelle.rxpermissions2.Permission
import ru.boronin.common.rx.extension.progress
import ru.boronin.core.api.location.LocationProvider
import ru.boronin.simpleweather.common.presentation.location.ILocationPresenter
import ru.boronin.simpleweather.common.presentation.mvp.BasePresenter
import ru.boronin.simpleweather.domain.interactor.WeatherInteractor
import ru.boronin.simpleweather.features.home.navigator.HomeNavigator

class HomePresenter(
    private val navigator: HomeNavigator,
    private val interactor: WeatherInteractor,
    private val locationProvider: LocationProvider
) : BasePresenter<HomeView>(), HomeAction, ILocationPresenter {

    override fun onFirstViewAttach() {
        onLocationNeeded()
    }

    override fun checkWeatherAction() {
        locationProvider.getLastKnownLocation { latLng ->
            subscriptions add interactor.getWeather(latLng.lat, latLng.lng)
                .progress { view?.setVisibleLoading(it) }
                .subscribe({
                    view?.updateView(it)
                }, {

                })
        }
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