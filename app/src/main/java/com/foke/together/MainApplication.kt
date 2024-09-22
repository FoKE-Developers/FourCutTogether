package com.foke.together

import android.app.Application
import com.foke.together.util.AppLog

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        AppLog.i(TAG, "onCreate", "app start")
    }

    companion object {
        val TAG: String = MainApplication::class.java.simpleName
    }
}