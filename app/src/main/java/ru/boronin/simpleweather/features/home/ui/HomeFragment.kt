package ru.boronin.simpleweather.features.home.ui

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.home_fragment.*
import ru.boronin.common.extension.widget.fadeOutIn
import ru.boronin.simpleweather.common.presentation.mvp.BaseView
import ru.boronin.simpleweather.features.home.di.HomeComponent
import ru.boronin.simpleweather.R
import ru.boronin.simpleweather.common.presentation.CorrectLeftMarginDecoration
import ru.boronin.simpleweather.di.ActivityComponent
import ru.boronin.simpleweather.model.common.presentation.ForecastModel
import ru.boronin.simpleweather.model.common.presentation.HourForecastModel
import ru.boronin.simpleweather.utils.helpers.DateHelper
import ru.boronin.simpleweather.utils.other.requestLocationPermission
import javax.inject.Inject

class HomeFragment : BaseView<HomeView,
    HomePresenter,
    HomeComponent>(),
    HomeView,
    SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    
    @Inject 
    override lateinit var presenter: HomePresenter
    
    override fun getLayout() = R.layout.home_fragment
            
    override fun onViewBound(view: View) {
        initList()
        initToolbar()
        initListeners()
    }

    override fun initDagger(activityComponent: ActivityComponent) {
        component = activityComponent.homeFactory().create(this)
        component?.inject(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.today -> presenter.showTodayWeatherAction()
            R.id.tomorrow -> presenter.showTomorrowWeatherAction()
            R.id.nextSevenDays -> presenter.showNextFiveDaysWeatherAction()
        }
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
        val singRes = if (model.temperature < 0) R.string.home_tempWithMinus else R.string.home_tempWithPlus
        temperature.text =  getString(singRes, model.temperature)
        location.text = model.locationName
        date.text = DateHelper.parseStringToDayMonthYear(model.date * 1000)
        day.text = getString(R.string.home_today)
        weatherDesc.text = model.temperatureDesc
    }

    override fun updateList(data: List<HourForecastModel>) {
        recyclerView.fadeOutIn(200) {
            (recyclerView.adapter as HoursWeatherAdapter).update(data)
            groupDetailedWeather.isVisible = true
        }
    }

    override fun setWeatherMode(weatherMode: WeatherMode) {
        today.alpha = 0.5f
        tomorrow.alpha = 0.5f
        nextSevenDays.alpha = 0.5f
        when(weatherMode) {
            WeatherMode.TODAY -> today.alpha = 1f
            WeatherMode.TOMORROW -> tomorrow.alpha = 1f
        }
    }

    // region private

    private fun initToolbar() {
        setVisibleToolbar(false)
    }

    private fun initListeners() {
        refreshLayout.setOnRefreshListener(this)
        today.setOnClickListener(this)
        tomorrow.setOnClickListener(this)
        nextSevenDays.setOnClickListener(this)
    }

    private fun initList() {
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, RecyclerView.HORIZONTAL, false)
        recyclerView.adapter = HoursWeatherAdapter()
        recyclerView.setHasFixedSize(true)
        recyclerView.setItemViewCacheSize(20)
        recyclerView.isNestedScrollingEnabled = false
//        recyclerView.addItemDecoration(
//            CorrectLeftMarginDecoration(
//                resources.getDimension(R.dimen.home_listItemPadding).toInt()
//            )
//        )
    }

    // endregion

    enum class WeatherMode {
        TODAY,
        TOMORROW
    }
}