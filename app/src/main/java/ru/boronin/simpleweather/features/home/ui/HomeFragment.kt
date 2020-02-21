package ru.boronin.simpleweather.features.home.ui

import android.view.View
import ru.boronin.simpleweather.common.presentation.mvp.BaseView
import ru.boronin.simpleweather.features.home.di.HomeComponent
import ru.boronin.simpleweather.R
import ru.boronin.simpleweather.di.ActivityComponent
import javax.inject.Inject

class HomeFragment : BaseView<HomeView, HomePresenter, HomeComponent>(), HomeView {
    
    @Inject 
    override lateinit var presenter: HomePresenter
    
    override fun getLayout() = R.layout.home_fragment
            
    override fun onViewBound(view: View) {
        initToolbar()
        initListeners()
    }

    override fun initDagger(activityComponent: ActivityComponent) {
        component = activityComponent.homeFactory().create(this)
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