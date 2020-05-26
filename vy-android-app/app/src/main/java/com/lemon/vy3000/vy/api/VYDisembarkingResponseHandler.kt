package com.lemon.vy3000.vy.api

import android.util.Log
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VYDisembarkingResponseHandler: Callback<JSONObject> {

    companion object {
        private val TAG: String = javaClass::class.java.simpleName
    }

    override fun onResponse(call: Call<JSONObject>, response: Response<JSONObject>) {
        if (response.isSuccessful) {
            val body = response.body()
            Log.e(TAG, "Response body: $body");
        } else {
            Log.e(TAG, "ERR: " + response.body().toString());
        }
    }

    override fun onFailure(call: Call<JSONObject>, t: Throwable) {
        t.printStackTrace()
    }
}