package ru.boronin.core.api.cache

import android.content.SharedPreferences.Editor
import ru.boronin.common.utils.*

/**
 * Created by ivasi on 09.06.2017.
 */
interface PreferenceManager {
  fun putBoolean(key: String, value: Boolean)
  fun getBoolean(key: String, defValue: Boolean = DEFAULT_BOOLEAN): Boolean
  fun putInt(key: String, value: Int)
  fun getInt(key: String, defValue: Int = DEFAULT_INT): Int
  fun putLong(key: String, value: Long)
  fun getLong(key: String, defValue: Long = DEFAULT_LONG): Long
  fun putString(key: String, value: String)
  fun getString(key: String, defValue: String = DEFAULT_STRING): String
  fun getStringSet(key: String, defValue: Set<String> = emptySet()): Set<String>
  fun putStringSet(key: String, value: Set<String>)
  fun putFloat(key: String, value: Float)
  fun getFloat(key: String, defValue: Float = DEFAULT_FLOAT): Float
  fun hasValue(key: String): Boolean
  fun remove(key: String)
  fun removeAll(): Editor
}