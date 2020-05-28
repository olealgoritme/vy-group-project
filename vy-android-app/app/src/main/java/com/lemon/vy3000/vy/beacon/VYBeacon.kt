package com.lemon.vy3000.vy.beacon

import com.google.gson.annotations.SerializedName
import org.altbeacon.beacon.Identifier

class VYBeacon {

    // BLE Service UUID (id1)
    @SerializedName("uuid") var uuid: String? = null

    // specific train number
    @SerializedName("trainNumber") var trainNumber: Int? = null

    // specific train carriage number
    @SerializedName("trainCarriageNumber") var trainCarriageNumber: Int? = null

    // type of beacon
    @SerializedName("type")  var type: String? = null

    // station
    @SerializedName("station") var station: String? = null

    // latitude
    @SerializedName("_latitude") var latitude: Double? = null

    // longitude
    @SerializedName("_longitude") var longitude: Double? = null

}