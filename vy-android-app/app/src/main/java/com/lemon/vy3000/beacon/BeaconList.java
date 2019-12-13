package com.lemon.vy3000.beacon;

import org.altbeacon.beacon.Identifier;

import java.util.UUID;

public class BeaconList {

    public static String OFF_BOARDING_BEACON  = "00112233-4455-6677-8899-aabbccddeeff";
    public static String ON_BOARDING_BEACON   = "00112233-4455-6677-8899-aeaeaeaeaeae";


    public static Identifier getBeacon(String uuid) {
        return Identifier.fromUuid(UUID.fromString(uuid));
    }

}
