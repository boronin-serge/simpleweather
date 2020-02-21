package ru.boronin.common.extension.core

import android.content.Context
import android.graphics.Color
import android.net.Uri
import androidx.annotation.ColorRes
import androidx.browser.customtabs.CustomTabsIntent

fun Context.callPhone(phone: String) {
  val fixPhone = phone.trim()
  if (fixPhone.isNotEmpty()) {
    val telephoneUri = Uri.parse("tel: $fixPhone")
    startActivity(actionViewIntentOf(telephoneUri))
  }
}

fun Context.openBrowserApp(url: String) {
  var fixUrl = url.trim()
  if (url.isNotEmpty()) {
    if (!url.startsWith("http://") && !url.startsWith("https://")) {
      fixUrl = "http://$url"
    }

    val urlUri = Uri.parse(fixUrl)
    startActivity(actionViewIntentOf(urlUri))
  }
}

fun Context.openChromeCustomTabs(url: String, @ColorRes color: Int) {
  val builder = CustomTabsIntent.Builder()
  builder.setToolbarColor(findColor(color) ?: Color.BLACK)
  builder.build().also {
    //it.intent.flags = FLAG_ACTIVITY_NEW_TASK
    it.launchUrl(this, Uri.parse(url))
  }
}