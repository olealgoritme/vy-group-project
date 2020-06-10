package com.lemon.vy3000.vy.beacon

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import com.lemon.vy3000.app.VYApp
import com.lemon.vy3000.vy.api.OnAPIResponseDisembarking
import com.lemon.vy3000.vy.api.VYAPIResponseBeaconsList
import com.lemon.vy3000.vy.api.VYAPIResponseBoarding
import com.lemon.vy3000.vy.api.VYUserDetails
import com.lemon.vy3000.vy.notifications.VYNotification
import com.lemon.vy3000.vy.ticket.VYBoardingListener
import com.lemon.vy3000.vy.ticket.VYTicketManager

@RequiresApi(Build.VERSION_CODES.O)
class VYBeaconService : Service(),
        VYBoardingListener,
        VYAPIResponseBeaconsList.OnAPIResponseBeacons,
        VYAPIResponseBoarding.OnAPIResponseBoarding,
        OnAPIResponseDisembarking
    {

    private var vyTicketManager: VYTicketManager? = null
    private var vyBeaconConsumer: VYBeaconConsumer? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        // Our ticket Manager controls everything about our ticket registration
        vyTicketManager = VYApp.getTicketManager()

        // Update local repository of all beacons
        val api = VYApp.getAPI()
        api.updateBeaconRepository(this)

        Log.e(TAG, "VY Beacon Service initialized")
        return START_STICKY
    }

    override fun onAPIResponseBeaconsSuccess() {
        // Beacon Consumer
        Log.e(TAG, "/api/beacons GET Success")
        vyBeaconConsumer = VYBeaconConsumer(this)
        vyBeaconConsumer!!.start()
    }


    /** Callback from VYBeaconConsumer  */
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBoardingDetected(vyBeaconEncounter: VYBeaconEncounter) {
        Log.e(TAG, "Boarding detected")

        // Reset ticket
        vyTicketManager!!.getCurrentTrip().resetTicket()

        // Attach boarding encounter to ticket
        vyTicketManager!!.getCurrentTrip().boardingEncounter = vyBeaconEncounter


        // API CALL -> /api/boarding - body params (JSON): email, beacon_uuid
        // returns { "ticketId": "xxx", "trainDestination": "Bodø", "departureTime": 241425125125 }"
        val api = VYApp.getAPI()

        // PRESUMES we have already been detected at XXX station (triggers a possible automatic ticket scenario by being at a station)
        val stationUUID = VYBeaconRepository.STATION_BEACON!!.uuid!!
        val stationName = VYBeaconRepository.STATION_BEACON!!.station!!

        api.postAPIBoarding(VYUserDetails.email, vyBeaconEncounter.uuid!!, stationUUID, stationName, this) // response comes in onAPIBoardingSuccess()
    }

    override fun onAPIBoardingSuccess(ticketId: String, trainDestination: String) {

        vyTicketManager!!.getCurrentTrip().start(ticketId, trainDestination) // the returned ticketId and destination from API

        // show notification
        VYNotification.showBoardingNotification(this, vyTicketManager!!.getCurrentTrip())
    }


    /** Callback from VYBeaconConsumer*/
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDisembarkingDetected(vyBeaconEncounter: VYBeaconEncounter?) {
        Log.e(TAG, "Station detected")

        vyTicketManager!!.getCurrentTrip().disembarkingEncounter = vyBeaconEncounter

        val api = VYApp.getAPI()
        api.postAPIDisembarking(VYUserDetails.email, vyTicketManager!!.getCurrentTrip().ticketId!!, vyBeaconEncounter!!.uuid!!, this)
    }

    override fun onAPIDisembarkingSuccess(price: Int) {
        vyTicketManager!!.getCurrentTrip().price = price
        vyTicketManager!!.getCurrentTrip().stop()
        VYNotification.showDisembarkingNotification(this, vyTicketManager!!.getCurrentTrip())
    }


    override fun onDestroy() {
        super.onDestroy()
        if(vyBeaconConsumer != null)
            vyBeaconConsumer!!.stopRanging()
    }

    companion object {
        private const val TAG = "VYBeaconService"
    }



}