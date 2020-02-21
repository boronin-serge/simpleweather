package ru.boronin.core.api.location

/**
 * Created by Sergey Boronin on 11.11.2019.
 */
interface LocationProvider {
  fun getLastKnownLocation(okFun: (latLng: LatLng) -> Unit)
}