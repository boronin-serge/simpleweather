package ru.boronin.simpleweather.features.home.ui

import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.home_fragment.*
import ru.boronin.common.extension.core.getStringArray
import ru.boronin.common.extension.widget.fadeOutIn
import ru.boronin.simpleweather.R
import ru.boronin.simpleweather.common.presentation.CorrectLeftMarginDecoration
import ru.boronin.simpleweather.common.presentation.image.ImageLoader
import ru.boronin.simpleweather.common.presentation.mvp.BaseView
import ru.boronin.simpleweather.di.ActivityComponent
import ru.boronin.simpleweather.features.home.di.HomeComponent
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

    @Inject
    lateinit var imageLoader: ImageLoader
    
    override fun getLayout() = R.layout.home_fragment
            
    override fun onViewBound(view: View) {
        initList()
        initToolbar()
        initListeners()
    }

    override fun onResume() {
        super.onResume()
        presenter.onLocationNeeded()
    }

    override fun initDagger(activityComponent: ActivityComponent) {
        component = activityComponent.homeFactory().create(this)
        component?.inject(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.today -> presenter.showTodayWeatherAction()
            R.id.tomorrow -> presenter.showTomorrowWeatherAction()
            R.id.nextFiveDays -> presenter.showNextFiveDaysWeatherAction()
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
        emptyPageHint.isVisible = false

        val singRes = if (model.temperature <= 0) R.string.home_tempWithMinus else R.string.home_tempWithPlus
        val monthsArr = requireContext().getStringArray(R.array.months)
        temperature.text =  getString(singRes, model.temperature)
        location.text = model.locationName
        date.text = DateHelper.parseStringToDayMonthYearHourMin(model.date, monthsArr)
        day.text = getString(R.string.home_now)
        weatherDesc.text = model.temperatureDesc

        val singFeelsLikeRes = if (model.feelsLike <= 0) R.string.home_tempWithMinus else R.string.home_tempWithPlus
        val feelsLikeTemp = getString(singFeelsLikeRes, model.feelsLike)
        feelsLike.text = getString(R.string.home_tempFeelsLike, feelsLikeTemp)

        val secondHexColor = if (model.iconId.last() == 'n') {
            R.color.nightColor
        } else {
            model.weatherType.getSecondColor()
        }

        val firstColor = ContextCompat.getColor(requireContext(), model.weatherType.getFirstColor())
        val secondColor = ContextCompat.getColor(requireContext(), secondHexColor)

        val gd = GradientDrawable(
          GradientDrawable.Orientation.TOP_BOTTOM,
          intArrayOf(secondColor, firstColor)
        )
        gd.cornerRadius = 0f

        refreshLayout?.background = gd

        imageLoader.loadImage(model.iconId, logo)
    }

    override fun updateList(data: List<HourForecastModel>, animate: Boolean) {
        val duration = if (animate) 200 else 0
        recyclerView.fadeOutIn(duration) {
            recyclerView.scrollToPosition(0)
            (recyclerView.adapter as HoursWeatherAdapter).update(data)
            groupDetailedWeather.isVisible = true
        }
    }

    override fun setWeatherMode(weatherMode: WeatherMode) {
        today.alpha = 0.5f
        tomorrow.alpha = 0.5f
        nextFiveDays.alpha = 0.5f
        when(weatherMode) {
            WeatherMode.TODAY -> today.alpha = 1f
            WeatherMode.TOMORROW -> tomorrow.alpha = 1f
        }
    }

    override fun showErrorPage() {
        emptyPageHint.setText(R.string.home_emptyPageError)
        emptyPageHint.isVisible = true
    }

    override fun showNotLocationPage() {
        emptyPageHint.setText(R.string.home_emptyPageNotLocationError)
        emptyPageHint.isVisible = true
    }

    override fun showErrorToast() {
        showPopup(R.string.home_loadError)
    }

    override fun enableToday(enable: Boolean) {
        val lp = today.layoutParams
        lp.width = if (enable) LinearLayout.LayoutParams.WRAP_CONTENT else 0
        today.layoutParams = lp
    }

    // region private

    private fun initToolbar() {
        setVisibleToolbar(false)
    }

    private fun initListeners() {
        refreshLayout.setOnRefreshListener(this)
        today.setOnClickListener(this)
        tomorrow.setOnClickListener(this)
        nextFiveDays.setOnClickListener(this)
    }

    private fun initList() {
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, RecyclerView.HORIZONTAL, false)
        recyclerView.adapter = HoursWeatherAdapter(imageLoader)
        recyclerView.addItemDecoration(
            CorrectLeftMarginDecoration(
                resources.getDimension(R.dimen.home_listItemPadding).toInt()
            )
        )
    }

    // endregion

    enum class WeatherMode {
        TODAY,
        TOMORROW
    }
}