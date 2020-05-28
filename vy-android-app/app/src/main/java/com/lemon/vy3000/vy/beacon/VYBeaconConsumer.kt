package com.lemon.vy3000.vy.beacon

import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.RemoteException
import android.util.Log
import com.lemon.vy3000.app.VYApp
import com.lemon.vy3000.vy.ticket.VYBoardingListener
import com.lemon.vy3000.vy.ticket.VYTicketManager
import org.altbeacon.beacon.*

class VYBeaconConsumer(private val boardingListener: VYBoardingListener, private val vyTicketManager: VYTicketManager) : BeaconConsumer {

    private lateinit var beaconManager: BeaconManager

    private var intervalDetectionCount: Int = 0

    companion object {
        const val TAG: String = "VYBeaconConsumer"
    }

    fun start() {
        beaconManager = BeaconManager.getInstanceForApplication(VYApp.context())
        beaconManager.backgroundScanPeriod = 5000L
        beaconManager.backgroundBetweenScanPeriod = 30000L
        beaconManager.applySettings()
        beaconManager.beaconParsers.add(BeaconParser().setBeaconLayout(VYBeaconLayout.ALTBEACON))
        beaconManager.bind(this)
    }

    private fun startRangingNotifiers(newRegion: Region) {

        // Add range notifier
        stopRanging()
        beaconManager.addRangeNotifier { beacons, _ ->

            for (beacon in beacons) {

                val beaconId = beacon.id1.toString()
                Log.e("Consumer", "Converted beacon id to string: $beaconId")

                // Boarding detection
                if (hasFoundBoardingBeacon(beacon) && satisfyConditions()) {
                    val vyBeaconEncounter = VYBeaconEncounter.getData(beacon)
                    boardingListener.onBoardingDetected(vyBeaconEncounter)
                }

                // Disembarking detection
                if (hasFoundStationBeacon(beacon) && satisfyConditions()) {
                    val vyBeaconEncounter = VYBeaconEncounter.getData(beacon)
                    val beaconId = beacon.id1.toString()
                    vyBeaconEncounter.vyBeacon = VYBeaconRepository.getBeaconByID(beaconId)
                    boardingListener.onDisembarkingDetected(vyBeaconEncounter)
                }
            }
        }

            try {
                beaconManager.startRangingBeaconsInRegion(newRegion)
            } catch (e: RemoteException) {
                Log.e(TAG, "CANT START BEACON RANGING")
            } finally {
                Log.e(TAG, "Beacon Ranging started")
            }
    }

    fun stopRanging() {
        beaconManager.removeAllRangeNotifiers()
    }

    // DEFINITION OF BOARDING DETECTED
    private fun hasFoundBoardingBeacon(beacon: Beacon): Boolean {
        return VYBeaconRepository.compareTo(VYBeaconRepository.STATION_BEACON!!.uuid!!, beacon.id1.toString())
                && beacon.distance < 3
                && ! vyTicketManager.getCurrentTrip().hasStarted()
    }

    // DEFINITION OF DISEMBARKING DETECTED
    private fun hasFoundStationBeacon(beacon: Beacon): Boolean {
        return VYBeaconRepository.compareTo(VYBeaconRepository.STATION_BEACON!!.uuid!!, beacon.id1.toString())
                && vyTicketManager.getCurrentTrip().hasStarted()
                && ! vyTicketManager.getCurrentTrip().hasEnded()
    }

    // Detect min 2 interval detections
    // TxPower: -77
    private fun satisfyConditions(): Boolean {
        if(this.intervalDetectionCount >= 2) {
            this.intervalDetectionCount = 0
            return true
        } else if (this.intervalDetectionCount >= 0) {
            intervalDetectionCount++
            return false
        }
        return false
    }


    override fun getApplicationContext(): Context {
        return VYApp.context()
    }

    override fun unbindService(p0: ServiceConnection?) {
    }

    override fun bindService(p0: Intent?, p1: ServiceConnection?, p2: Int): Boolean {
        return true
    }

    override fun onBeaconServiceConnect() {
        val region = Region("VY_BEACONS", null, null, null)
        startRangingNotifiers(region)
        Log.e(TAG, "BeaconConsumer Service Connected")

    }
}