package ru.boronin.core.api.location.impl

import android.content.Context
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import ru.boronin.core.api.location.LatLng
import ru.boronin.core.api.location.LocationProvider

/**
 * Created by Sergey Boronin on 11.11.2019.
 */
class FusedLocationProvider(private val context: Context) : LocationProvider {

  private lateinit var fusedLocationClient: FusedLocationProviderClient

  override fun getLastKnownLocation(okFun: (latLng: LatLng) -> Unit) {
    fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
      okFun.invoke(
        LatLng(
          location?.latitude?.toFloat() ?: 0F,
          location?.longitude?.toFloat() ?: 0F
        )
      )
    }
  }
}