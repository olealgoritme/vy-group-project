package com.lemon.vy3000.beacon;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import com.lemon.vy3000.R;
import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import java.util.Collection;

public class AltBeaconManager extends Service implements BeaconConsumer {


    protected static final String TAG = "Beacon";
    private BeaconManager beaconManager;

    private boolean hasBoarded = false;


    @Override
    public void onCreate() {
        Log.e(TAG, "Beacon watcher App extensions created");
        super.onCreate();
        beaconManager = BeaconManager.getInstanceForApplication(this);

        beaconManager
                .getBeaconParsers()
                .add(new BeaconParser()
                        .setBeaconLayout("m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25"));
        beaconManager.bind(this);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onBeaconServiceConnect() {
        Region region = new Region("HK",
                BeaconList.getBeacon(BeaconList.ON_BOARDING_BEACON),
                BeaconList.getBeacon(BeaconList.OFF_BOARDING_BEACON),
                null);

        try {
            beaconManager.startRangingBeaconsInRegion(region);
        } catch (RemoteException e) {
            Log.e(TAG, "CANT START BEACON RANGING");
        }

        beaconManager.removeAllMonitorNotifiers();
        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> collection, Region region) {
                Log.e(TAG, "Region called: " + region.getUniqueId());
                Log.e(TAG, "Region called: " + region.getBluetoothAddress());

                for(Beacon beacon : collection) {
                    Log.e(TAG, "Beacon: " + beacon.getBluetoothAddress());

                    // OnBoarding train
                    if(beacon.getId1().equals(BeaconList.getBeacon(BeaconList.ON_BOARDING_BEACON)) && beacon.getDistance() < 2 && !hasBoarded) {

                        Log.e(TAG, "YOU ARE NOW ON THE TRAIN");
                        hasBoarded = true;
                        displayBoardingMessage();
                    }

                    // OffBoarding train
                    if(beacon.getId1().equals(BeaconList.getBeacon(BeaconList.OFF_BOARDING_BEACON)) && beacon.getDistance() < 2 && hasBoarded) {

                        Log.e(TAG, "YOU ARE NOW OFF THE TRAIN");
                        displayOffBoardingMessage();
                    }

                    Log.e(TAG, "Beacon: " + beacon.getDistance() + " meters away");
                    Log.e(TAG, "Beacon: " + beacon.getId1());
                }
            }
        });



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

    private void displayOffBoardingMessage() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.vy_logo)
                .setContentTitle("Vy")
                .setChannelId("vy")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("You have GONE OFF the train!"))
                .setPriority(NotificationCompat.PRIORITY_MAX);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(5, builder.build());

    }

    private void displayBoardingMessage() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.vy_logo)
                .setContentTitle("Vy")
                .setChannelId("vy")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("You have BOARDED the train!"))
                .setPriority(NotificationCompat.PRIORITY_MAX);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(2, builder.build());
    }

}
