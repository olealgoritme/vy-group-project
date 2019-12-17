package com.lemon.vy3000.vy;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Region;

import java.time.Instant;

public class VYApp extends Application implements BeaconConsumer {

    protected static final String TAG = "VYApp";
    private BeaconManager beaconManager;
    private VYBeaconManager vyBeaconManager;

    // to be used later for public APIs
    private VYBoardingListener boardingListener;

    private static Context ctx;



    @Override
    public void onCreate() {
        Log.e(TAG, "Beacon watcher App extensions created");
        super.onCreate();
        ctx = this;
        beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers()
                .add(new BeaconParser()
                        .setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        beaconManager.bind(this);
    }

    @Override
    public void onBeaconServiceConnect() {
        Region region = new Region("VY_BEACONS", null,null, null);
        vyBeaconManager = new VYBeaconManager(ctx,  beaconManager, region, boardingListener);
        vyBeaconManager.startRanging();
   }

   public static Context getContext() {
        return ctx;
   }
}
