package com.lemon.vy3000.vy.beacon

import org.altbeacon.beacon.Identifier
import java.util.*

object VYBeaconRepository {

    var BOARDING_BEACON_ID: Identifier = Identifier.fromUuid(UUID.fromString("00000001-1337-1337-1337-beefc0debeef"))
    var STATION_BEACON_ID:  Identifier = Identifier.fromUuid(UUID.fromString("00000000-1337-1337-1337-beefc0debeef"))

    fun compareTo(id1: Identifier, id2: Identifier): Boolean {
        return id1 == id2
    }

}