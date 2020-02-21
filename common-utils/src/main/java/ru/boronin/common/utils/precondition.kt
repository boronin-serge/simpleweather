package ru.boronin.common.utils

fun checkException(value: Boolean, exception: Exception) {
  if (!value) {
    throw exception
  }
}