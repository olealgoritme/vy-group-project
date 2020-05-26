package com.lemon.vy3000.vy.beacon

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.lemon.vy3000.vy.notifications.VYNotifications
import com.lemon.vy3000.vy.ticket.VYBoardingListener
import com.lemon.vy3000.vy.ticket.VYTicketManager
import org.altbeacon.beacon.BeaconConsumer
import org.altbeacon.beacon.BeaconManager
import org.altbeacon.beacon.BeaconParser
import org.altbeacon.beacon.Region

class VYBeaconService : Service(), BeaconConsumer, VYBoardingListener {

    private lateinit var beaconManager: BeaconManager
    private lateinit var vyBeaconEncounterHandler: VYBeaconEncounterHandler
    private lateinit var vyTicketManager: VYTicketManager

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        beaconManager = BeaconManager.getInstanceForApplication(this)
        //beaconManager.beaconParsers.add(BeaconParser().setBeaconLayout(VYBeaconEncounterHandler.BEACON_LAYOUT))
        beaconManager.bind(this)

        vyTicketManager = VYTicketManager.getInstance()!!

        Log.e(TAG, "VY Beacon Service initialized")
        return START_STICKY
    }


    /** Callback from BeaconConsumer  */
    override fun onBeaconServiceConnect() {
        val region = Region("VY_BEACONS", null, null, null)
        vyBeaconEncounterHandler = VYBeaconEncounterHandler(beaconManager, region, this)
        vyBeaconEncounterHandler.startRanging()
        Log.e(TAG, "BeaconConsumer Service initialized")
    }


    /** Callback from VYBeaconManager  */
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
                .beaconEncounter = vyBeaconEncounter

        // show notification
        VYNotifications.showBoarding(this, vyTicketManager.getCurrentTrip())
    }


    /** Callback from VYBeaconManager  */
    override fun onDisembarkingDetected(vyBeaconEncounter: VYBeaconEncounter) {
        Log.e(TAG, "Disembarking detected")
        //VYNotification.displayOnDisembarkingNotification();
        //Notifier.showBoarding(this);
        // TODO: API call -> get ticket price + timestamp
        //ticketManager.getCurrentTrip().stopWithTimeStamp();
    }

    override fun onDestroy() {
        super.onDestroy()
        if(vyBeaconEncounterHandler.isRanging())
            vyBeaconEncounterHandler.stopRanging()
    }

    companion object {
        private const val TAG = "VYBeaconService"
    }
}