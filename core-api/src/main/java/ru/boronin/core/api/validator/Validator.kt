package ru.boronin.core.api.validator

interface Validator<T> {
  fun valid(value: T): Boolean
}