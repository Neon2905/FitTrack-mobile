package com.fittrackapp.fittrack_mobile.utils

import android.content.Context
import android.widget.Toast as ToastWidget

object Toast {
    private var appContext: Context? = null

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    fun show(message: String, duration: Int = ToastWidget.LENGTH_SHORT) {
        appContext?.let {
            ToastWidget.makeText(it, message, duration).show()
        }
    }
}
