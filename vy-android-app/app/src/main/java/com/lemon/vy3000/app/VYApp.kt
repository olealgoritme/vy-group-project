package com.lemon.vy3000.app

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import androidx.annotation.RequiresApi
import com.lemon.vy3000.vy.api.VYAPIController
import com.lemon.vy3000.vy.beacon.VYBeaconService
import com.lemon.vy3000.vy.notifications.VYNotification
import com.lemon.vy3000.vy.ticket.VYTicketManager

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

        fun getTicketManager(): VYTicketManager {
            return VYTicketManager.getInstance()!!
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()

        val instance: Context = context()

        startService(Intent(this, VYBeaconService::class.java))

        // Simulating information regarding train delays, etc
        Handler().postDelayed({
            VYNotification.showTrainInformation(applicationContext);
        }, 5000)
    }}