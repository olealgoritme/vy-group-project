package com.lemon.vy3000.vy.beacon

import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.RemoteException
import android.util.Log
import com.lemon.vy3000.app.VYApp
import com.lemon.vy3000.vy.api.VYBeaconLayout
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

                // Boarding detection
                if (hasFoundBoardingBeacon(beacon) && satisfyConditions()) {
                    val vyBeaconEncounter = VYBeaconEncounter()
                    vyBeaconEncounter.bluetoothAddress = beacon.bluetoothAddress
                    vyBeaconEncounter.distance = beacon.distance
                    vyBeaconEncounter.rssi = beacon.rssi
                    vyBeaconEncounter.txPower = beacon.txPower

                    boardingListener.onBoardingDetected(vyBeaconEncounter)
                }

                // Disembarking detection
                if (hasFoundDisembarkingBeacon(beacon) && satisfyConditions()) {
                    val vyBeaconEncounter = VYBeaconEncounter()
                    boardingListener.onDisembarkingDetected(vyBeaconEncounter)
                }
            }
        }

            try {
                beaconManager.startRangingBeaconsInRegion(newRegion)
            } catch (e: RemoteException) {
                Log.e(TAG, "CANT START BEACON RANGING")
            } finally {
                Log.e(TAG, "Beacon Ranging started in region: " + newRegion.id1)
            }
    }

    fun stopRanging() {
        beaconManager.removeAllRangeNotifiers()
    }

    // DEFINITION OF BOARDING DETECTED
    // Detect 2 interval detections
    // TxPower: -77
    private fun hasFoundBoardingBeacon(beacon: Beacon): Boolean {
        return VYBeaconRepository.compareTo(VYBeaconRepository.BOARDING_BEACON_ID, beacon.id1)
                && beacon.distance < 3
                && !vyTicketManager.getCurrentTrip().hasStarted()
    }

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

    // DEFINITION OF DISEMBARKING DETECTED
    // Detect 2 interval detections
    // TxPower: -77
    private fun hasFoundDisembarkingBeacon(beacon: Beacon): Boolean {
        return VYBeaconRepository.compareTo(VYBeaconRepository.DISEMBARKING_BEACON_ID,beacon.id1)
                && vyTicketManager.getCurrentTrip().hasStarted()
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