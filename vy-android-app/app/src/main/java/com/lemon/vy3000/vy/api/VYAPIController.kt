package com.lemon.vy3000.vy.api

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class VYAPIController {

    private var retrofit: Retrofit
    private var gson: Gson = GsonBuilder().setLenient().create()

    init {
        retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    }

    fun updateBeaconRepository(listener: VYAPIResponseBeaconsList.OnAPIResponseBeacons) {
        val vyBackendAPI = retrofit.create(VYAPIInterface::class.java)
        val call = vyBackendAPI.updateBeaconRepository()

        val responder = VYAPIResponseBeaconsList()
        responder.withListener(listener)
        call.enqueue(responder)
    }

    fun postAPIBoarding(email: String, boardingUUID: String, stationUUID: String, stationName: String, listener: VYAPIResponseBoarding.OnAPIResponseBoarding) {

        Log.e("API Post", "Boarding -> $email : boarding_uuid:$boardingUUID : station_uuid:$stationUUID : station_name:$stationName")

        val vyBackendAPI = retrofit.create(VYAPIInterface::class.java)
        val call = vyBackendAPI.postBoarding(VYAPIInterface.BoardingData(email, boardingUUID, stationUUID, stationName))

        val responder = VYAPIResponseBoarding()
        responder.withListener(listener)
        call.enqueue(responder)
    }

    fun postAPIDisembarking(email: String, ticketId: String, beaconUUID: String, listener: OnAPIResponseDisembarking) {

        Log.e("API Post", "Disembarking -> email:$email : ticket_id:$ticketId : station_uuid:$beaconUUID")
        val vyBackendAPI = retrofit.create(VYAPIInterface::class.java)
        val call = vyBackendAPI.postDisembarking(VYAPIInterface.DisembarkingData(email, ticketId, beaconUUID))

        val responder = VYAPIResponseDisembarking()
        responder.withListener(listener)
        call.enqueue(responder)
    }

    companion object {
        const val BASE_URL = "https://vy-automatic-ticketing-system.web.app"
    }
}