package com.lemon.vy3000.vy.api

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

    fun getBeaconList() {
        val vyBackendAPI = retrofit.create(VYBackendAPI::class.java)
        val call = vyBackendAPI.vYBeaconList
        call.enqueue(VYBeaconListResponseHandler())
    }

    fun postAPIBoarding(email: String) {
        val vyBackendAPI = retrofit.create(VYBackendAPI::class.java)
        val call = vyBackendAPI.postBoarding(VYBackendAPI.BoardingData(email))
        call.enqueue(VYBoardingResponseHandler())
    }

    fun postAPIDisembarking(ticketId: String) {
        val vyBackendAPI = retrofit.create(VYBackendAPI::class.java)
        val call = vyBackendAPI.postDisembarking(VYBackendAPI.DisembarkingData(ticketId))
        //call.enqueue(VYDisembarkingResponseHandler())
    }


    companion object {
        const val BASE_URL = "https://vy-automatic-ticketing-system.web.app"
    }
}