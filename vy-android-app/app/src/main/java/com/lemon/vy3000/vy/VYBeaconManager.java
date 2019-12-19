package com.lemon.vy3000.vy;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;
import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;

class VYBeaconManager {

    private static final String TAG = "VYBeaconManager";
    private BeaconManager beaconManager;
    private Region region;
    private VYBoardingListener boardingListener;
    private Context ctx;

    private TripManager tripManager;

    VYBeaconManager(Context ctx, BeaconManager beaconManager, Region region, VYBoardingListener boardingListener) {
        this.ctx = ctx;
        this.beaconManager = beaconManager;
        this.beaconManager.setBackgroundScanPeriod(5000l);
        this.beaconManager.setBackgroundBetweenScanPeriod(30000l);
        this.beaconManager.applySettings();
        this.region = region;
        this.boardingListener = boardingListener;
        this.tripManager = TripManager.getInstance();
    }

    boolean rangingStarted() {
        return (this.beaconManager.getRangingNotifiers().size() > 0);
    }

    void startRanging() {
        beaconManager.removeAllRangeNotifiers();
        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                Log.e(TAG, "Region called: " + region.getUniqueId());

                for (Beacon beacon : beacons) {
                    Log.e(TAG, "Beacon: BT:   " + beacon.getBluetoothAddress());
                    Log.e(TAG, "Beacon: TX:   " + beacon.getTxPower());
                    Log.e(TAG, "Beacon: RSSI: " + beacon.getRssi());
                    Log.e(TAG, "Beacon: Dist: " + beacon.getDistance());
                    Log.e(TAG, "Beacon: ID1:   " + beacon.getId1());
                    Log.e(TAG, "Beacon: ID2:   " + beacon.getId2());
                    Log.e(TAG, "Beacon: ID3:   " + beacon.getId3());
                    Log.e(TAG, "Beacon Comp:   " + beacon.getId1() + " : " + VYBeacons.ON_BOARDING_BEACON_ID);


                    // OnBoarding train
                    if (VYBeacons.compareId(VYBeacons.ON_BOARDING_BEACON_ID, beacon.getId1())
                            && beacon.getDistance() < 15
                            && !tripManager.getCurrentTrip().isStarted()) {

                        // Fire off "on-boarding detected"
                        if (boardingListener != null) boardingListener.onBoardingDetected(beacon);
                        Log.e(TAG, "YOU ARE NOW ON THE TRAIN");

                        // Set business logic properties
                        tripManager.startTripAt(Instant.now());


                        Log.e(TAG, "should display notification!");

                        // Display notification
                        VYNotification.displayOnBoardingNotification(ctx);
                    }

                    // OffBoarding train
                    if (VYBeacons.compareId(VYBeacons.OFF_BOARDING_BEACON_ID, beacon.getId1())
                            && beacon.getDistance() < 2
                            && tripManager.getCurrentTrip().isStarted()
                            && !tripManager.getCurrentTrip().isEnded()) {


                        // TODO: This is a test for presentation, so we just check if we have been on board for more than 10 seconds and that the off-boarding beacon is in range
                        Instant before = tripManager.getCurrentTrip().getDepartureTime();
                        Instant now = Instant.now();
                        Duration between = Duration.between(before, now);
                        long duration = between.getSeconds();
                        if (duration < 10) return;


                        // Fire off "off-boarding detected"
                        if (boardingListener != null) boardingListener.offBoardingDetected(beacon);
                        Log.e(TAG, "YOU ARE NOW OFF THE TRAIN");

                        // Set business logic properties
                        tripManager.endTripAt(Instant.now());

                        // Display notification
                        VYNotification.displayOffBoardingNotification(ctx);
                    }
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
