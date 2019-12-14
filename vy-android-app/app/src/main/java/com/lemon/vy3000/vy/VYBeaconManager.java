package com.lemon.vy3000.vy;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;

class VYBeaconManager {

    private static final String TAG = "VYBeaconManager";
    private BeaconManager beaconManager;
    private Region region;
    private boolean hasBoarded = false;
    private VYBoardingListener boardingListener;
    private Context ctx;

    VYBeaconManager(Context ctx, BeaconManager beaconManager, Region region, VYBoardingListener boardingListener) {
        this.ctx = ctx;
        this.beaconManager = beaconManager;
        this.region = region;
        this.boardingListener = boardingListener;
    }

    void startRanging() {
        beaconManager.removeAllRangeNotifiers();
        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                Log.e(TAG, "Region called: " + region.getUniqueId());

                for(Beacon beacon : beacons) {
                    Log.e(TAG, "Beacon: BT:   " + beacon.getBluetoothAddress());
                    Log.e(TAG, "Beacon: TX:   " + beacon.getTxPower());
                    Log.e(TAG, "Beacon: RSSI: " + beacon.getRssi());
                    Log.e(TAG, "Beacon: Dist: " + beacon.getDistance());
                    Log.e(TAG, "Beacon: ID:   " + beacon.getId1());

                    // OnBoarding train
                    if(VYBeacons.ON_BOARDING_BEACON_ID.equals(beacon.getId1())
                            && beacon.getDistance() < 2
                            && !VYApp.hasBoarded) {

                        // Fire off on-boarding detected
                        if(boardingListener != null) boardingListener.onBoardingDetected(beacon);
                        Log.e(TAG, "YOU ARE NOW ON THE TRAIN");
                        VYNotification.displayOnBoardingNotification(ctx);
                    }

                    // OffBoarding train
                    if(VYBeacons.OFF_BOARDING_BEACON_ID.equals(beacon.getId1())
                            && beacon.getDistance() < 2
                            && VYApp.hasBoarded
                            && !VYApp.tripEnded) {

                        // Fire off off-boarding detected
                        if(boardingListener != null) boardingListener.offBoardingDetected(beacon);
                        Log.e(TAG, "YOU ARE NOW OFF THE TRAIN");
                        VYNotification.displayOffBoardingNotification(ctx);
                    }

                    Log.e(TAG, "Beacon: " + beacon.getDistance() + " meters away");
                    Log.e(TAG, "Beacon: " + beacon.getId1());
                }
            }
        });


        try {
            beaconManager.startRangingBeaconsInRegion(region);
        } catch (RemoteException e) {
            Log.e(TAG, "CANT START BEACON RANGING");
        }
    }

    void stopRanging() {
        beaconManager.removeAllRangeNotifiers();
    }

    void startMonitoring() {
        beaconManager.removeAllMonitorNotifiers();
        beaconManager.addMonitorNotifier(new MonitorNotifier() {
            @Override
            public void didEnterRegion(Region region) {
                Log.e(TAG, "I just saw a fucking beacon!");
            }

            @Override
            public void didExitRegion(Region region) {
                Log.e(TAG, "I no longer see any beacon");
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
}
