package com.lemon.vy3000.vy.beacon

import org.altbeacon.beacon.Beacon

object VYBeaconEncounter {
    var uuid: String? = null
    var bluetoothAddress: String = ""
    var txPower: Int = 0
    var rssi: Int = 0
    var distance: Double = 0.0
    var vyBeacon: VYBeacon? = null

    fun getData(beacon : Beacon) : VYBeaconEncounter {
        val data = VYBeaconEncounter
        data.uuid = beacon.id1.toString()
        data.bluetoothAddress = beacon.bluetoothAddress
        data.distance = beacon.distance
        data.rssi = beacon.rssi
        data.txPower = beacon.txPower
        return data
    }
}