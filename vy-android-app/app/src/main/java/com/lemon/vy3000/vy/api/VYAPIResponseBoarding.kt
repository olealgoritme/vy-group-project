package com.lemon.vy3000.vy.api

import android.util.Log
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VYAPIResponseBoarding: Callback<JSONObject> {

    private lateinit var listener: OnAPIResponseBoarding
companion object {
        private val TAG: String = javaClass::class.java.simpleName
    }

    fun withListener(listener: OnAPIResponseBoarding) {
        this.listener = listener
    }

    override fun onResponse(call: Call<JSONObject>, response: Response<JSONObject>) {
        if (response.isSuccessful) {
            val json = response.body()
            Log.e(TAG, "Got /api/boarding response: $json");

            val ticketId = json?.get("ticketId") as String
            val trainDestination = json.get("ticketId") as String
            val departureTime = json.get("departureTime") as Long

            this.listener.onAPIBoardingSuccess(ticketId, trainDestination, departureTime)

        } else {
            Log.e(TAG, "err:" + response.body().toString());
        }
    }

    override fun onFailure(call: Call<JSONObject>, t: Throwable) {
        t.printStackTrace()
    }

    interface OnAPIResponseBoarding {
        fun onAPIBoardingSuccess(ticketId: String, trainDestination: String, departureTime: Long)
    }
}