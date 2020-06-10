package com.lemon.vy3000.vy.api

import com.google.gson.annotations.SerializedName
import com.lemon.vy3000.vy.beacon.VYBeacon
import com.lemon.vy3000.vy.ticket.VYTicket
import retrofit2.Call
import retrofit2.http.*

interface VYAPIInterface {


    // returns JSONObject of Beacons
    @GET("/api/beacons")
    fun updateBeaconRepository(): Call<MutableList<VYBeacon>>


    // sends: ticketId(String)
    // returns duration(long) + price (double)
    @Headers("Content-Type: application/json")
    @POST("/api/disembarking")
    fun postDisembarking(@Body disembarkingData: DisembarkingData): Call<VYTicket>

    data class DisembarkingData(
            @SerializedName("email") var email: String,
            @SerializedName("ticket_id") var ticketId: String,
            @SerializedName("beacon_uuid") var beaconUUID: String
    )


    // sends: email (String) + beacon_uuid (String) + station_beacon_uuid + station_name
    // returns ticketId(String), trainDestination(String), departureTime(long)
    @Headers("Content-Type: application/json")
    @POST("/api/boarding")
    fun postBoarding(@Body boardingData: BoardingData): Call<VYTicket>

    // TODO: (if we have time) database needs to implement table to catch our VYBeaconEncounter data (rssi, txPower, bt_address etc) - only sending uuid is not really enough
    data class BoardingData(
            @SerializedName("email") var email: String,
            @SerializedName("boarding_uuid") var boardingUUID: String,
            @SerializedName("station_uuid") var beaconUUID: String,
            @SerializedName("station_name") var stationName: String
    )


}