package com.fittrackapp.fittrack_mobile.utils

import android.content.Context
import com.fittrackapp.fittrack_mobile.FitTrackMobile
import android.widget.Toast as ToastWidget

object Toast {
    private val appContext: Context
        get() = FitTrackMobile.instance.applicationContext

    fun show(message: String, duration: Int = ToastWidget.LENGTH_SHORT) {
        if (message.isBlank()) return
        ToastWidget.makeText(appContext, message, duration).show()
    }
}
