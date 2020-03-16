package ru.boronin.simpleweather.features.home.ui

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.hour_weather_item.view.*
import ru.boronin.common.view.base.RoundedFrameLayout
import ru.boronin.simpleweather.R
import ru.boronin.simpleweather.common.presentation.BaseAdapter
import ru.boronin.simpleweather.model.common.presentation.HourForecastModel
import ru.boronin.simpleweather.utils.helpers.DateHelper

/**
 * Created by Sergey Boronin on 16.03.2020.
 */
class HoursWeatherAdapter : BaseAdapter<HoursWeatherAdapter.PagerVH, HourForecastModel>() {

    override fun getItemLayout() = R.layout.hour_weather_item

    override fun initViewHolder(itemView: View) = PagerVH(itemView)

    override fun onBindViewHolder(holder: PagerVH, position: Int) {
        holder.bind(items[position])
    }

    class PagerVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val logo = itemView.logo
        private val time = itemView.time
        private val temperature = itemView.temperature

        fun bind(model: HourForecastModel) {
            time.text = DateHelper.parseIsoStringToTime(model.time * 1000)

            val singRes = if (model.temperature < 0) R.string.home_tempWithMinus else R.string.home_tempWithPlus
            temperature.text =  itemView.context.getString(singRes, model.temperature)

            (itemView as RoundedFrameLayout).setBackgroundColor(Color.BLUE)
        }
    }
}