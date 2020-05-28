package com.lemon.vy3000.app

import android.app.Application
import android.content.Context
import com.lemon.vy3000.vy.api.VYAPIController

class VYApp : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: VYApp? = null
        private var api = VYAPIController()

        fun context() : Context {
            return instance!!.applicationContext
        }

        fun getAPI() : VYAPIController {
            return api
        }
    }


    override fun onCreate() {
        super.onCreate()
        val instance: Context = context()
    }}