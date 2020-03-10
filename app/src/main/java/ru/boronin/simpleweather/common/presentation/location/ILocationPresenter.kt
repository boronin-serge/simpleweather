package ru.boronin.simpleweather.common.presentation.location

import com.tbruyelle.rxpermissions2.Permission

interface ILocationPresenter {
    fun onLocationNeeded()
    fun onPermissionGranted(permission: Permission, isOnDemand: Boolean)
}