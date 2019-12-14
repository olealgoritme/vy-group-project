package com.lemon.vy3000.vy;

import org.altbeacon.beacon.Identifier;

import java.util.UUID;

class VYBeacons {
    static Identifier OFF_BOARDING_BEACON_ID = Identifier.fromUuid(UUID.fromString("00112233-4455-6677-8899-aabbccddeeff"));
    static Identifier ON_BOARDING_BEACON_ID  = Identifier.fromUuid(UUID.fromString("00112233-4455-6677-8899-aeaeaeaeaeae"));
}
