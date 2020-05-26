package com.lemon.vy3000.vy.ticket;

import com.lemon.vy3000.vy.beacon.VYBeaconEncounter;

public interface VYBoardingListener {
    void onBoardingDetected(VYBeaconEncounter vyBeaconEncounter);
    void onDisembarkingDetected(VYBeaconEncounter vyBeaconEncounter);
}
