package com.lemon.vy3000.vy.api

import com.google.gson.annotations.SerializedName
import com.lemon.vy3000.vy.beacon.VYBeacon
import com.lemon.vy3000.vy.ticket.VYTicket
import retrofit2.Call
import retrofit2.http.*

interface VYBackendAPI {


    // returns list of beacons with info (latitude,longitude,train_number,train_carriage_number)
    @get:Headers("Content-Type: application/json")
    @get:GET("/api/vy_beacon_list")
    val vYBeaconList: Call<MutableList<VYBeacon>>


    // sends: ticketId
    // returns end_timestamp + ticket_price
    @Headers("Content-Type: application/json")
    @POST("/api/disembarking")
    fun postDisembarking(@Body disembarkingData: DisembarkingData): Call<VYTicket>


    // sends: email
    // returns train_destination, ticket_id, start_timestamp
    @Headers("Content-Type: application/json")
    @POST("/api/boarding")
    fun postBoarding(@Body boardingData: BoardingData): Call<VYTicket>

    data class BoardingData(
            @SerializedName("email") var email: String
    )

    data class DisembarkingData(
            @SerializedName("ticketId") var ticketId: String
    )

}