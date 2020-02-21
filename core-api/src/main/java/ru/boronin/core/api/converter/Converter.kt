package ru.boronin.core.api.converter

import java.lang.reflect.Type

/**
 * Created by tower on 05.06.2017.
 */
interface Converter {
  fun to(any: Any?): String
  fun <T> from(json: String?, type: Type?): T
}