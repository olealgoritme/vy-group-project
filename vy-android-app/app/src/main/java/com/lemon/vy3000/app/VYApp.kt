package com.lemon.vy3000.app

import android.app.Application
import android.content.Context

class VYApp : Application() {
    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        var context: Context? = null
            private set
    }
}