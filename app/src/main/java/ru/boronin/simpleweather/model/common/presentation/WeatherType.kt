package ru.boronin.simpleweather.model.common.presentation

import android.graphics.Color
import ru.boronin.simpleweather.R

/**
 * Created by Sergey Boronin on 06.03.2020.
 */
enum class WeatherType {
    SUNNY {
        override fun getIconRes() = R.drawable.ic_sunny
        override fun getFirstColor() = R.color.sunnyFirstColor
        override fun getSecondColor() = R.color.sunnySecondColor
    },
    CLOUDS {
        override fun getIconRes() = R.drawable.ic_cloudy
        override fun getFirstColor() = R.color.cloudsFirstColor
        override fun getSecondColor() = R.color.cloudsSecondColor
    },
    FOGGY {
        override fun getIconRes() = R.drawable.ic_foggy
        override fun getFirstColor() = R.color.foggyFirstColor
        override fun getSecondColor() = R.color.foggySecondColor
    },
    RAIN {
        override fun getIconRes() = R.drawable.ic_rain
        override fun getFirstColor() = R.color.rainFirstColor
        override fun getSecondColor() = R.color.rainSecondColor
    },
    SNOW {
        override fun getIconRes() = R.drawable.ic_snowy
        override fun getFirstColor() = R.color.snowFirstColor
        override fun getSecondColor() = R.color.snowSecondColor
    },
    CLEAR {
        override fun getIconRes() = R.drawable.ic_sunny
        override fun getFirstColor() = R.color.sunnyFirstColor
        override fun getSecondColor() = R.color.sunnySecondColor
    };

    abstract fun getIconRes(): Int
    abstract fun getFirstColor(): Int
    abstract fun getSecondColor(): Int
}