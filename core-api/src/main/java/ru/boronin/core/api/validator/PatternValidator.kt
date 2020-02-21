package ru.boronin.core.api.validator

interface PatternValidator<T> {
  fun valid(value: T, pattern: String): Boolean
}