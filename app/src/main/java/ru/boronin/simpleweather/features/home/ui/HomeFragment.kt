package ru.boronin.simpleweather.features.home.ui

import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.home_fragment.*
import ru.boronin.simpleweather.common.presentation.mvp.BaseView
import ru.boronin.simpleweather.features.home.di.HomeComponent
import ru.boronin.simpleweather.R
import ru.boronin.simpleweather.di.ActivityComponent
import ru.boronin.simpleweather.model.common.presentation.ForecastModel
import ru.boronin.simpleweather.utils.helpers.DateHelper
import ru.boronin.simpleweather.utils.other.requestLocationPermission
import javax.inject.Inject

class HomeFragment : BaseView<HomeView,
    HomePresenter,
    HomeComponent>(),
    HomeView,
    SwipeRefreshLayout.OnRefreshListener{
    
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

    override fun checkLocationPermissions() {
        requireActivity().requestLocationPermission(presenter, false)
    }

    override fun setVisibleLoading(visible: Boolean) {
        refreshLayout.isRefreshing = visible
    }

    override fun onRefresh() {
        presenter.onLocationNeeded()
    }

    override fun updateView(model: ForecastModel) {
        val singRes = if (model.temperature < 0) R.string.home_tempWithPlus else R.string.home_tempWithMinus
        temperature.text =  getString(singRes, model.temperature)
        location.text = model.locationName
        date.text = DateHelper.parseStringToDayMonthYear(model.date.toString())
        day.text = getString(R.string.home_today)
        weatherDesc.text = model.temperatureDesc
    }

    // region private

    private fun initToolbar() {
        setVisibleToolbar(false)
    }

    private fun initListeners() {
        refreshLayout.setOnRefreshListener(this)
    }



    // endregion

}