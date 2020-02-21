package ru.boronin.core.api.error

interface ErrorHandler {
  fun getErrorMessage(throwable: Throwable?): String
}