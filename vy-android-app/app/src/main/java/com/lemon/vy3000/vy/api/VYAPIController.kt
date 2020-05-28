package com.lemon.vy3000.vy.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.altbeacon.beacon.Identifier
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

    fun updateBeaconRepository() {
        val vyBackendAPI = retrofit.create(VYAPIInterface::class.java)
        val call = vyBackendAPI.updateBeaconRepository()

        val responder = VYAPIResponseBeaconsList()
        call.enqueue(responder)
    }

    fun postAPIBoarding(email: String, beaconUUID: Identifier, listener: VYAPIResponseBoarding.OnAPIResponseBoarding) {
        val vyBackendAPI = retrofit.create(VYAPIInterface::class.java)
        val call = vyBackendAPI.postBoarding(VYAPIInterface.BoardingData(email, beaconUUID))

        val responder = VYAPIResponseBoarding()
        responder.withListener(listener)
        call.enqueue(responder)
    }

    fun postAPIDisembarking(ticketId: String, listener: OnAPIResponseDisembarking) {
        val vyBackendAPI = retrofit.create(VYAPIInterface::class.java)
        val call = vyBackendAPI.postDisembarking(VYAPIInterface.StationData(ticketId))

        val responder = VYAPIResponseDisembarking()
        responder.withListener(listener)
        call.enqueue(responder)
    }




    companion object {
        const val BASE_URL = "https://vy-automatic-ticketing-system.web.app"
    }
}