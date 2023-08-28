package ru.boronin.simpleweather.utils.other

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.boronin.common.rx.extension.schedulers
import ru.boronin.simpleweather.common.presentation.location.ILocationPresenter

/**
 * Created by Sergey Boronin on 20.06.2019.
 */

@SuppressLint("CheckResult")
fun Activity.requestLocationPermission(presenter: ILocationPresenter, isOnDemand: Boolean) {
  RxPermissions(this).requestEach(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    .schedulers(AndroidSchedulers.mainThread(), AndroidSchedulers.mainThread())
    .subscribe { permission ->
      presenter.onPermissionGranted(permission, isOnDemand)
    }
}
