package ru.boronin.simpleweather.features.home.ui

import ru.boronin.simpleweather.common.presentation.mvp.BasePresenter
import ru.boronin.simpleweather.features.home.navigator.HomeNavigator
import javax.inject.Inject

class HomePresenter @Inject constructor(
    private val navigator: HomeNavigator
) : BasePresenter<HomeView>(), HomeAction {
    
}