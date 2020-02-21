package ru.boronin.common.extension.primitive

import ru.boronin.common.utils.*

inline fun <T, R> default(
  value: T?,
  defaultFun: (T) -> R?
): R? = if (value != null) defaultFun.invoke(value) else null

inline fun <T> default(defaultFun: () -> T?, defValue: T): T = defaultFun.invoke() ?: defValue
inline fun defaultFloat(defaultFun: () -> Float?) = default(defaultFun, DEFAULT_FLOAT)
inline fun defaultString(defaultFun: () -> String?) = default(defaultFun, DEFAULT_STRING)
inline fun defaultBoolean(defaultFun: () -> Boolean?) = default(defaultFun, DEFAULT_BOOLEAN)

fun Int?.orDefault(default: Int = DEFAULT_INT) = this ?: default
fun Long?.orDefault(default: Long = DEFAULT_LONG) = this ?: default
fun Float?.orDefault(default: Float = DEFAULT_FLOAT) = this ?: default
fun Double?.orDefault(default: Double = DEFAULT_DOUBLE) = this ?: default
fun Boolean?.orDefault(default: Boolean = DEFAULT_BOOLEAN) = this ?: default