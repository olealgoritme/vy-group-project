package com.lemon.vy3000.vy.beacon

import org.altbeacon.beacon.Identifier
import java.util.*

object VYBeaconRepository {

    val DISEMBARKING_BEACON_ID: Identifier = Identifier.fromUuid(UUID.fromString("00112233-4455-6677-8899-aabbccddeeff"))
    val BOARDING_BEACON_ID: Identifier = Identifier.fromUuid(UUID.fromString("00112233-4455-6677-8899-aeaeaeaeaeae"))

    fun compareTo(id1: Identifier, id2: Identifier): Boolean {
        return id1 == id2
    }

}