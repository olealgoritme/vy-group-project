package com.lemon.vy3000.beacon;

import org.altbeacon.beacon.Beacon;

public interface OnTrainListener {
    void onBoardingDetected(Beacon beacon);
    void onDeboardingDetected(Beacon beacon);
}
