package ru.boronin.core.api.system

/**
 * Created by Sergey Boronin on 31.07.2019.
 */
interface DeviceManager {
  fun getUniqueDeviceId(): String
  fun clearUniqueDeviceId()
  fun clearAndGetUniqueDeviceId(): String
}