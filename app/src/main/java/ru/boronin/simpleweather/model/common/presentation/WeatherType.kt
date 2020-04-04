package ru.boronin.simpleweather.model.common.presentation

import ru.boronin.common.utils.DEFAULT_STRING
import ru.boronin.simpleweather.R
import java.util.*

/**
 * Created by Sergey Boronin on 06.03.2020.
 */
enum class WeatherType {

    // region Atmosphere

    FOG {
        override fun getFirstColor() = R.color.foggyFirstColor
        override fun getSecondColor() = R.color.foggySecondColor
    },
    MIST {
        override fun getFirstColor() = FOG.getFirstColor()
        override fun getSecondColor() = FOG.getFirstColor()
    },
    SMOKE {
        override fun getFirstColor() = FOG.getFirstColor()
        override fun getSecondColor() = FOG.getFirstColor()
    },
    HAZE {
        override fun getFirstColor() = FOG.getFirstColor()
        override fun getSecondColor() = FOG.getFirstColor()
    },
    DUST {
        override fun getFirstColor() = FOG.getFirstColor()
        override fun getSecondColor() = FOG.getFirstColor()
    },
    SAND {
        override fun getFirstColor() = FOG.getFirstColor()
        override fun getSecondColor() = FOG.getFirstColor()
    },
    ASH {
        override fun getFirstColor() = FOG.getFirstColor()
        override fun getSecondColor() = FOG.getFirstColor()
    },
    SQUALL {
        override fun getFirstColor() = FOG.getFirstColor()
        override fun getSecondColor() = FOG.getFirstColor()
    },
    TORNADO {
        override fun getFirstColor() = FOG.getFirstColor()
        override fun getSecondColor() = FOG.getFirstColor()
    },

    // endregion

    // region Common

    RAIN {
        override fun getFirstColor() = R.color.rainFirstColor
        override fun getSecondColor() = R.color.rainSecondColor
    },
    SNOW {
        override fun getFirstColor() = R.color.snowFirstColor
        override fun getSecondColor() = R.color.snowSecondColor
    },
    CLEAR {
        override fun getFirstColor() = R.color.sunnyFirstColor
        override fun getSecondColor() = R.color.sunnySecondColor
    },
    CLOUDS {
        override fun getFirstColor() = R.color.cloudsFirstColor
        override fun getSecondColor() = R.color.cloudsSecondColor
    },
    THUNDERSTORM {
        override fun getFirstColor() = R.color.sunnyFirstColor
        override fun getSecondColor() = R.color.sunnySecondColor
    },
    DRIZZLE {
        override fun getFirstColor() = R.color.drizzleFirstColor
        override fun getSecondColor() = R.color.drizzleSecondColor
    },

    // endregion

    ERROR {
        override fun getFirstColor() = R.color.errorFirstColor
        override fun getSecondColor() = R.color.errorSecondColor
    };

    abstract fun getFirstColor(): Int
    abstract fun getSecondColor(): Int

    companion object {
        fun find(typeStr: String?): WeatherType {
            val query = typeStr?.toUpperCase(Locale.ENGLISH) ?: DEFAULT_STRING
            return values().find { it.name == query } ?: ERROR
        }
    }
}