package ru.boronin.common.extension.core.collection

fun <K> MutableMap<K, String>.putStringIfNotNullOrEmpty(key: K, value: String?) {
  if (!value.isNullOrEmpty()) {
    put(key, value)
  }
}

fun <K> mapNotEmptyStringOf(vararg pairs: Pair<K, String>) = mutableMapOf<K, String>().apply {
  pairs.forEach { putStringIfNotNullOrEmpty(it.first, it.second) }
}