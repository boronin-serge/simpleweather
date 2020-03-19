package ru.boronin.simpleweather.features.futureforecast.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.future_forecast_item.view.*
import kotlinx.android.synthetic.main.hour_weather_item.view.logo
import kotlinx.android.synthetic.main.hour_weather_item.view.temperature
import ru.boronin.simpleweather.R
import ru.boronin.simpleweather.common.presentation.BaseAdapter
import ru.boronin.simpleweather.common.presentation.image.ImageLoader
import ru.boronin.simpleweather.model.common.presentation.DayForecastModel
import ru.boronin.simpleweather.utils.helpers.DateHelper

/**
 * Created by Sergey Boronin on 16.03.2020.
 */
class FutureForecastAdapter(private val imageLoader: ImageLoader) : BaseAdapter<FutureForecastAdapter.PagerVH, DayForecastModel>() {

  override fun getItemLayout() = R.layout.future_forecast_item

  override fun initViewHolder(itemView: View) = PagerVH(itemView, imageLoader)

  override fun onBindViewHolder(holder: PagerVH, position: Int) {
    holder.bind(items[position])
  }

  class PagerVH(itemView: View, private val imageLoader: ImageLoader) : RecyclerView.ViewHolder(itemView) {
    private val logo = itemView.logo
    private val day = itemView.day
    private val temperature = itemView.temperature
    private val daysArr = itemView.context.resources.getStringArray(R.array.days)

    fun bind(model: DayForecastModel) {
      val ctx = itemView.context

      day.text = DateHelper.parseIsoStringToDayOfWeek(model.day, daysArr)

      val singRes = if (model.temperature <= 0) R.string.home_tempWithMinus else R.string.home_tempWithPlus
      temperature.text =  ctx.getString(singRes, model.temperature)

      imageLoader.loadImage(model.iconId, logo)
    }
  }
}