package ru.boronin.common.extension.core

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

fun actionViewIntentOf(uri: Uri, vararg pairs: Pair<String, Any?>) = intentOf(*pairs).apply {
  action = Intent.ACTION_VIEW
  data = uri
}

fun intentOf(vararg pairs: Pair<String, Any?>) = Intent().apply {
  for ((key, value) in pairs) {
    when (value) {
      null -> putExtra(key, "")

      // Scalars
      is Boolean -> putExtra(key, value)
      is Byte -> putExtra(key, value)
      is Char -> putExtra(key, value)
      is Double -> putExtra(key, value)
      is Float -> putExtra(key, value)
      is Int -> putExtra(key, value)
      is Long -> putExtra(key, value)
      is Short -> putExtra(key, value)

      // References
      is Bundle -> putExtra(key, value)
      is CharSequence -> putExtra(key, value)
      is Parcelable -> putExtra(key, value)

      // Scalar arrays
      is BooleanArray -> putExtra(key, value)
      is ByteArray -> putExtra(key, value)
      is CharArray -> putExtra(key, value)
      is DoubleArray -> putExtra(key, value)
      is FloatArray -> putExtra(key, value)
      is IntArray -> putExtra(key, value)
      is LongArray -> putExtra(key, value)
      is ShortArray -> putExtra(key, value)

      is Array<*> -> {
        val componentType = value::class.java.componentType
        @Suppress("UNCHECKED_CAST") // Checked by reflection.
        when {
          Parcelable::class.java.isAssignableFrom(componentType!!) -> {
            putExtra(key, value as Array<Parcelable>)
          }
          String::class.java.isAssignableFrom(componentType) -> {
            putExtra(key, value as Array<String>)
          }
          CharSequence::class.java.isAssignableFrom(componentType) -> {
            putExtra(key, value as Array<CharSequence>)
          }
          Serializable::class.java.isAssignableFrom(componentType) -> {
            putExtra(key, value)
          }
          else -> {
            val valueType = componentType.canonicalName
            throw IllegalArgumentException(
              "Illegal value array type $valueType for key \"$key\"")
          }
        }
      }

      // Last resort. Also we must check this after Array<*> as all arrays are serializable.
      is Serializable -> putExtra(key, value)

      else -> {
        val valueType = value.javaClass.canonicalName
        throw IllegalArgumentException("Illegal value type $valueType for key \"$key\"")
      }
    }
  }
}