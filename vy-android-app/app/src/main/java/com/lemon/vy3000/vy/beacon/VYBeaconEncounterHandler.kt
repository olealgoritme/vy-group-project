package com.lemon.vy3000.vy.beacon

import org.altbeacon.beacon.BeaconManager
import org.altbeacon.beacon.Region
import android.os.RemoteException
import android.util.Log
import com.lemon.vy3000.vy.ticket.VYBoardingListener
import com.lemon.vy3000.vy.ticket.VYTicketManager
import org.altbeacon.beacon.Beacon

class VYBeaconEncounterHandler(private val beaconManager: BeaconManager, private val region: Region, private val boardingListener: VYBoardingListener) {

    private var vyTicketManager: VYTicketManager = VYTicketManager.getInstance()!!

    companion object {
        private const val TAG = "VYBeaconEncounterHandler"
        const val BEACON_LAYOUT = "m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"
    }

    init {
        beaconManager.backgroundScanPeriod = 5000L
        beaconManager.backgroundBetweenScanPeriod = 30000L * 2 * 15 // ca 15 min
        beaconManager.applySettings()
        Log.e(TAG, "VY Beacon Encounter Handler initialized")
    }

    fun isRanging() : Boolean {
        return beaconManager.rangingNotifiers.size > 0
    }

    fun startRanging() {
        beaconManager.removeAllRangeNotifiers()
        beaconManager.addRangeNotifier { beacons, region ->

            Log.e(TAG, "Beacon in region: " + region.id1)
            Log.e(TAG, "Beacons: " + beacons.size)

            for (beacon in beacons) {

                Log.e(TAG, "Beacon in range: " + beacon.id1)

                // Boarding train detected
                if (hasFoundBoardingBeacon(beacon)) {
                        val vyBeaconEncounter = VYBeaconEncounter()
                        vyBeaconEncounter.bluetoothAddress = beacon.bluetoothAddress
                        vyBeaconEncounter.distance = beacon.distance
                        vyBeaconEncounter.rssi = beacon.rssi
                        vyBeaconEncounter.txPower = beacon.txPower

                        boardingListener.onBoardingDetected(vyBeaconEncounter)
                }

                // Disembarking train detected
                if (hasFoundDisembarkingBeacon(beacon)) {
                        val vyBeaconEncounter = VYBeaconEncounter()

                        boardingListener.onDisembarkingDetected(vyBeaconEncounter)
                }
            }

            try {
                beaconManager.startRangingBeaconsInRegion(this.region)
            } catch (e: RemoteException) {
                Log.e(TAG, "CANT START BEACON RANGING")
            }

        }
    }

    fun stopRanging() {
        beaconManager.removeAllRangeNotifiers()
    }

    private fun hasFoundBoardingBeacon(beacon: Beacon): Boolean {
        return VYBeaconRepository.BOARDING_BEACON_ID == beacon.id1
                && beacon.distance < 15
                && vyTicketManager.getCurrentTrip().hasStarted()
    }

    private fun hasFoundDisembarkingBeacon(beacon: Beacon): Boolean {
       return VYBeaconRepository.DISEMBARKING_BEACON_ID == beacon.id1
              && beacon.distance < 10
              && vyTicketManager.getCurrentTrip().hasStarted()
              && !vyTicketManager.getCurrentTrip().hasEnded()
    }

    /*
    void startMonitoring() {
        beaconManager.removeAllMonitorNotifiers();
        beaconManager.addMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                Log.e(TAG, "Entered beacon region!");
            }

            @Override
            public void didExitRegion(Region region) {
                Log.e(TAG, "Exit from beacon region");
            }

            @Override
            public void didDetermineStateForRegion(int state, Region region) {
                Log.e(TAG, "I have just switched from seeing/not seeing beacons: "+state);
            }
        });

        try {
            beaconManager.startMonitoringBeaconsInRegion(region);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    void stopMonitoring() {
        beaconManager.removeAllMonitorNotifiers();
    }

     */

}