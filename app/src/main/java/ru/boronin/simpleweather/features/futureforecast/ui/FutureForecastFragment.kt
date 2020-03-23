package ru.boronin.simpleweather.features.futureforecast.ui

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.futureforecast_fragment.*
import ru.boronin.simpleweather.R
import ru.boronin.simpleweather.common.presentation.image.ImageLoader
import ru.boronin.simpleweather.common.presentation.mvp.BaseView
import ru.boronin.simpleweather.di.ActivityComponent
import ru.boronin.simpleweather.features.futureforecast.di.FutureForecastComponent
import ru.boronin.simpleweather.model.common.presentation.ForecastModel
import javax.inject.Inject

class FutureForecastFragment : BaseView<FutureForecastView, FutureForecastPresenter, FutureForecastComponent>(), FutureForecastView,
  View.OnClickListener {

  @Inject
  override lateinit var presenter: FutureForecastPresenter

  @Inject
  lateinit var imageLoader: ImageLoader

  override fun getLayout() = R.layout.futureforecast_fragment

  override fun onViewBound(view: View) {
    initList()
    initToolbar()
    initListeners()
  }

  override fun initDagger(activityComponent: ActivityComponent) {
    component = activityComponent.futureForecastFactory().create(this)
    component?.inject(this)
  }

  override fun onClick(v: View?) {
    when(v?.id) {
      R.id.btnBack -> presenter.backAction()
    }
  }

  override fun showForecast(data: ForecastModel) {
    (rvForecastList.adapter as FutureForecastAdapter).update(data.nextFiveDays)
  }

  // region private

  private fun initToolbar() {
    setVisibleToolbar(false)
  }

  private fun initListeners() {
    btnBack.setOnClickListener(this)
  }

  private fun initList() {
    rvForecastList.layoutManager = LinearLayoutManager(rvForecastList.context)
    rvForecastList.adapter = FutureForecastAdapter(imageLoader)
  }

  // endregion

}