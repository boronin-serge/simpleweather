package ru.boronin.simpleweather.features.futureforecast.ui

import android.view.View
import ru.boronin.simpleweather.features.futureforecast.di.FutureForecastComponent
import ru.boronin.simpleweather.R
import ru.boronin.simpleweather.common.presentation.mvp.BaseView
import ru.boronin.simpleweather.di.ActivityComponent
import javax.inject.Inject

class FutureForecastFragment : BaseView<FutureForecastView, FutureForecastPresenter, FutureForecastComponent>(), FutureForecastView {
    
    @Inject 
    override lateinit var presenter: FutureForecastPresenter
    
    override fun getLayout() = R.layout.futureforecast_fragment
            
    override fun onViewBound(view: View) {
        initToolbar()
        initListeners()
    }

    override fun initDagger(activityComponent: ActivityComponent) {
        component = activityComponent.futureForecastFactory().create(this)
        component?.inject(this)
    }

    // region private

    private fun initToolbar() {
        setVisibleToolbar(false)
    }

    private fun initListeners() {

    }

    // endregion

}