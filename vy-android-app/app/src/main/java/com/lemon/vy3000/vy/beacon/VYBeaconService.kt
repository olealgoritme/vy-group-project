package com.lemon.vy3000.vy.beacon

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import com.lemon.vy3000.vy.notifications.VYNotification
import com.lemon.vy3000.vy.ticket.VYBoardingListener
import com.lemon.vy3000.vy.ticket.VYTicketManager

class VYBeaconService : Service(), VYBoardingListener {

    private lateinit var vyTicketManager: VYTicketManager
    private lateinit var vyBeaconConsumer: VYBeaconConsumer

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        vyTicketManager = VYTicketManager.getInstance()!!
        vyBeaconConsumer = VYBeaconConsumer(this, vyTicketManager)
        vyBeaconConsumer.start()

        Log.e(TAG, "VY Beacon Service initialized")
        return START_STICKY
    }


    /** Callback from VYBeaconConsumer  */
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBoardingDetected(vyBeaconEncounter: VYBeaconEncounter) {
        Log.e(TAG, "Boarding detected")

        if (vyTicketManager.getCurrentTrip().hasEnded())
            vyTicketManager.getCurrentTrip()
                 .resetTicket()

        // set ticket start timestamp
        // TODO: API call -> get ticket ID+ timestamp
        vyTicketManager.getCurrentTrip()
                .start("random-long-ticket-id",
                        240240240,
                        "Oslo S")

        // attach beacon encounter to ticket
        vyTicketManager.getCurrentTrip()
                .boardingEncounter = vyBeaconEncounter

        // show notification
        VYNotification.showBoardingNotification(this, vyTicketManager.getCurrentTrip())
    }


    /** Callback from VYBeaconManager  */
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDisembarkingDetected(vyBeaconEncounter: VYBeaconEncounter) {
        vyTicketManager.getCurrentTrip().disembarkingEncounter = vyBeaconEncounter
        vyTicketManager.getCurrentTrip().stop(492949842)
        VYNotification.showDisembarkingNotification(this, vyTicketManager.getCurrentTrip())
        Log.e(TAG, "Disembarking detected")
        // TODO: API call /api/disembarking -> get ticket price + timestamp -> update vyTicketManager.getCurrentTrip()
    }

    override fun onDestroy() {
        super.onDestroy()
        vyBeaconConsumer.stopRanging()
    }

    companion object {
        private const val TAG = "VYBeaconService"
    }
}