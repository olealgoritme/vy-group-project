package com.lemon.vy3000.vy.api

import android.util.Log
import com.lemon.vy3000.vy.beacon.VYBeacon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.function.Consumer

class VYBeaconListResponseHandler: Callback<MutableList<VYBeacon>> {

    companion object {
        private val TAG: String = javaClass::class.java.simpleName
    }

    override fun onResponse(call: Call<MutableList<VYBeacon>>, response: Response<MutableList<VYBeacon>>) {

        if (response.isSuccessful) {
            val vyBeaconList = response.body()!!

            vyBeaconList.forEach(Consumer { beacon: VYBeacon ->
            Log.e(TAG, "Got Beacon: " + beacon.name + " UUID: " + beacon.uuid)
            })
        } else {
            Log.e(TAG, response.body().toString());
        }
    }

    override fun onFailure(call: Call<MutableList<VYBeacon>>, t: Throwable) {
        t.printStackTrace()
    }
}