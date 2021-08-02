package com.enrique.iamhungry.utils

import android.app.Activity
import android.util.DisplayMetrics


fun Activity.getWindowsSize(): DisplayMetrics {
    val displayMetrics = DisplayMetrics()
    this.windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics
}