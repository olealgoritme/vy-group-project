package com.lemon.vy3000.vy;

import org.altbeacon.beacon.Beacon;

public interface VYBoardingListener {
    void onBoardingDetected(Beacon beacon);
    void offBoardingDetected(Beacon beacon);
}
