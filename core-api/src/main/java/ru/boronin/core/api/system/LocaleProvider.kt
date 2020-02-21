package ru.boronin.core.api.system

interface LocaleProvider {

  /**
   * Return result in standart ISO 639-2/T
   */
  val currentIso: String
}