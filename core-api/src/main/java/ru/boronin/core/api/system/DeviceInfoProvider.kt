package ru.boronin.core.api.system

import android.net.Uri

interface DeviceInfoProvider {
  fun getAndroidVersion(): String
  fun getAndroidVersionInt(): Int
  fun getApplicationVersion(): String
  fun getDate(): String
  fun getUUID(): String
  fun getLocale(): String
  fun getModel(): String
  fun getVendor(): String
  fun getTimezone(): String
  fun getCarrier(): String
  fun getConnectionType(): String
  fun getAllDeviceInfo(): String
  fun generateAllDeviceInfoFile(): Uri
}