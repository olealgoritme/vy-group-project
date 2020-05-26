package com.lemon.vy3000.app

import android.app.Application
import android.content.Context

class VYApp : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: VYApp? = null

        fun context() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        val instance: Context = context()
    }}