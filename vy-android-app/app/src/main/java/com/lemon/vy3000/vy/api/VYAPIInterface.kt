package com.lemon.vy3000.vy.api

import com.google.gson.annotations.SerializedName
import com.lemon.vy3000.vy.beacon.VYBeacon
import com.lemon.vy3000.vy.ticket.VYTicket
import org.altbeacon.beacon.Identifier
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface VYAPIInterface {


    // returns JSONObject of Beacons
    @GET("/api/beacons")
    fun updateBeaconRepository(): Call<MutableList<VYBeacon>>


    // sends: ticketId(String)
    // returns duration(long) + price (double)
    @Headers("Content-Type: application/json")
    @POST("/api/station")
    fun postDisembarking(@Body stationData: StationData): Call<JSONObject>

    data class StationData(
            @SerializedName("ticketId") var ticketId: String
    )


    // sends: email (String) + beacon_uuid (String)
    // returns ticketId(String), trainDestination, departureTime(long)
    @Headers("Content-Type: application/json")
    @POST("/api/boarding")
    fun postBoarding(@Body boardingData: BoardingData): Call<VYTicket>

    // TODO: database needs to implement table to catch our VYBeaconEncounter data (rssi, txPower, bt_address etc) - only sending uuid is not enough
    data class BoardingData(
            @SerializedName("email") var email: String,
            @SerializedName("beacon_uuid") var beaconUUID: Identifier
    )


}