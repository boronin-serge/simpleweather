package ru.boronin.core.api.navigator

interface Navigator {
  var localHandler: NavigatorHandler?
  var globalHandler: NavigatorHandler?
}