package com.lemon.vy3000.vy.beacon

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import com.lemon.vy3000.app.VYApp
import com.lemon.vy3000.vy.api.OnAPIResponseDisembarking
import com.lemon.vy3000.vy.api.VYAPIResponseBoarding
import com.lemon.vy3000.vy.api.VYUserDetails
import com.lemon.vy3000.vy.notifications.VYNotification
import com.lemon.vy3000.vy.ticket.VYBoardingListener
import com.lemon.vy3000.vy.ticket.VYTicketManager
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class VYBeaconService : Service(), VYBoardingListener, VYAPIResponseBoarding.OnAPIResponseBoarding, OnAPIResponseDisembarking {

    private lateinit var vyTicketManager: VYTicketManager
    private lateinit var vyBeaconConsumer: VYBeaconConsumer

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        // Our ticket Manager controls everything about our ticket
        vyTicketManager = VYTicketManager.getInstance()!!

        // Library Beacon Consumer
        vyBeaconConsumer = VYBeaconConsumer(this, vyTicketManager)
        vyBeaconConsumer.start()


        // Update local repository of all beacons
        val api = VYApp.getAPI()
        api.updateBeaconRepository()

        Log.e(TAG, "VY Beacon Service initialized")
        return START_STICKY
    }


    /** Callback from VYBeaconConsumer  */
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBoardingDetected(vyBeaconEncounter: VYBeaconEncounter) {
        Log.e(TAG, "Boarding detected")

        // Reset ticket
        vyTicketManager.getCurrentTrip().resetTicket()

        // Attach boarding encounter to ticket
        vyTicketManager.getCurrentTrip().boardingEncounter = vyBeaconEncounter


        // API CALL -> /api/boarding - body params (JSON): email, beacon_uuid
        // returns { "ticketId": "xxx", "trainDestination": "Kristiansand", "departureTime": 241425125125 }"
        val api = VYApp.getAPI()
        api.postAPIBoarding(VYUserDetails.email, vyBeaconEncounter.uuid!!, this) // response comes in onAPIResultBoarding()
    }

    override fun onAPIBoardingSuccess(ticketId: String, trainDestination: String, departureTime: Long) {
        vyTicketManager.getCurrentTrip().start(ticketId, departureTime, trainDestination)

        // show notification
        VYNotification.showBoardingNotification(this, vyTicketManager.getCurrentTrip())
    }


    /** Callback from VYBeaconConsumer*/
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDisembarkingDetected(vyBeaconEncounter: VYBeaconEncounter?) {
        Log.e(TAG, "Station detected")

        vyTicketManager.getCurrentTrip().stationEncounter = vyBeaconEncounter
        vyTicketManager.getCurrentTrip().stop(Calendar.getInstance().time)

        VYNotification.showStationNotification(this, vyTicketManager.getCurrentTrip())
        // TODO: API call /api/station -> get ticket price + timestamp -> update vyTicketManager.getCurrentTrip()
    }

    override fun onAPIDisembarkingSuccess(duration: Long, price: Double) {
    }


    override fun onDestroy() {
        super.onDestroy()
        vyBeaconConsumer.stopRanging()
    }

    companion object {
        private const val TAG = "VYBeaconService"
    }




}