package ru.boronin.common.utils.delegate

import java.lang.ref.WeakReference
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <T> weakReference() =
  WeakReferenceDelegate<T>()

class WeakReferenceDelegate<T> : ReadWriteProperty<Any?, T?> {
  private var reference: WeakReference<T>? = null

  override fun getValue(thisRef: Any?, property: KProperty<*>) = reference?.get()

  override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
    reference = if (value == null) {
      reference?.clear()
      null
    } else {
      WeakReference(value)
    }
  }
}