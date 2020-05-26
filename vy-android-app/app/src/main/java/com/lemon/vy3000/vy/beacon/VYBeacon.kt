package com.lemon.vy3000.vy.beacon

class VYBeacon {
    // BLE Service UUID (id1)
    var uuid: String? = null

    // defines latitude coordinates of the mounted beacon
    var latitude: String? = null

    // defines longitude coordinates of the mounted beacon
    var longitude: String? = null

    // defines specific train number
    var trainNumber: String? = null

    // defines specific train carriage number
    var trainCarriageNumber: String? = null

    // defines string representation of the complete beacon (simple concat)
    var name: String = "$uuid-$latitude-$longitude-$trainNumber-$trainCarriageNumber"
}