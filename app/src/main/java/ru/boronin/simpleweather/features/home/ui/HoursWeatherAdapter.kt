package ru.boronin.simpleweather.features.home.ui

import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.hour_weather_item.view.*
import ru.boronin.common.view.base.RoundedFrameLayout
import ru.boronin.simpleweather.R
import ru.boronin.simpleweather.common.presentation.BaseAdapter
import ru.boronin.simpleweather.common.presentation.image.ImageLoader
import ru.boronin.simpleweather.model.common.presentation.HourForecastModel
import ru.boronin.simpleweather.utils.helpers.DateHelper

/**
 * Created by Sergey Boronin on 16.03.2020.
 */
class HoursWeatherAdapter(private val imageLoader: ImageLoader) : BaseAdapter<HoursWeatherAdapter.PagerVH, HourForecastModel>() {

    override fun getItemLayout() = R.layout.hour_weather_item

    override fun initViewHolder(itemView: View) = PagerVH(itemView, imageLoader)

    override fun onBindViewHolder(holder: PagerVH, position: Int) {
        holder.bind(items[position])
    }

    class PagerVH(itemView: View, private val imageLoader: ImageLoader) : RecyclerView.ViewHolder(itemView) {
        private val logo = itemView.logo
        private val time = itemView.time
        private val temperature = itemView.temperature

        fun bind(model: HourForecastModel) {
            val ctx = itemView.context
            time.text = DateHelper.parseIsoStringToTime(model.time)

            val singRes = if (model.temperature <= 0) R.string.home_tempWithMinus else R.string.home_tempWithPlus
            temperature.text =  ctx.getString(singRes, model.temperature)

            val firstColor = ContextCompat.getColor(ctx, model.weatherType.getFirstColor())
            val secondColor = ContextCompat.getColor(ctx, model.weatherType.getSecondColor())

            val gd = GradientDrawable(
              GradientDrawable.Orientation.TOP_BOTTOM,
              intArrayOf(firstColor, secondColor)
            )
            gd.cornerRadius = 0f
            (itemView as RoundedFrameLayout).background = gd

            imageLoader.loadImage(model.iconId, logo)
        }
    }
}