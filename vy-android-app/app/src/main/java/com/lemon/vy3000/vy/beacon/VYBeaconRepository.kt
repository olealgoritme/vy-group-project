package com.lemon.vy3000.vy.beacon


object VYBeaconRepository {

    //var BOARDING_BEACON_ID: Identifier = Identifier.fromUuid(UUID.fromString("00000001-1337-1337-1337-beefc0debeef"))
    //var STATION_BEACON_ID:  Identifier = Identifier.fromUuid(UUID.fromString("00000000-1337-1337-1337-beefc0debeef"))

    var BOARDING_BEACON: VYBeacon? = null
    var STATION_BEACON: VYBeacon? = null

    fun compareTo(id1: String, id2: String): Boolean {
        return id1.toLowerCase() == id2.toLowerCase()
    }


    fun getBeaconByID(uuid: String) : VYBeacon? {
        val boarding = BOARDING_BEACON
        if (boarding!!.uuid == uuid) return boarding

        val station = STATION_BEACON
        if (station!!.uuid== uuid) return station

        return null
    }

}