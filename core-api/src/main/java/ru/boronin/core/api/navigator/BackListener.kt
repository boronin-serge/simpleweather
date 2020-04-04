package ru.boronin.core.api.navigator

interface BackListener {
  /**
   * If the function returns true, then the user processes the click
   * If the function returns false, then it is automatically called
   */
  fun back(): Boolean
}