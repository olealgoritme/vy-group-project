package com.lemon.vy3000.vy.api

import android.util.Log
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VYAPIResponseDisembarking: Callback<JSONObject> {

    private lateinit var listener: OnAPIResponseDisembarking

    companion object {
        private val TAG: String = javaClass::class.java.simpleName
    }

    fun withListener(listener: OnAPIResponseDisembarking) {
        this.listener = listener
    }

    override fun onResponse(call: Call<JSONObject>, response: Response<JSONObject>) {
        if (response.isSuccessful) {
            val json: JSONObject? = response.body()
            Log.e(TAG, "Response body: $json");

            val tripDuration = json?.get("duration") as Long
            val price = json.get("price") as Double

           this.listener.onAPIDisembarkingSuccess(tripDuration, price)

        } else {
            Log.e(TAG, "ERR: " + response.body().toString());
        }
    }

    override fun onFailure(call: Call<JSONObject>, t: Throwable) {
        t.printStackTrace()
    }
}

interface OnAPIResponseDisembarking {
    fun onAPIDisembarkingSuccess(duration: Long, price: Double)
}
