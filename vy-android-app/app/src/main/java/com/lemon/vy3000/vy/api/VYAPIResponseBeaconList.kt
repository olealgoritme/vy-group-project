package com.lemon.vy3000.vy.api

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.lemon.vy3000.vy.beacon.VYBeacon
import com.lemon.vy3000.vy.beacon.VYBeaconRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VYAPIResponseBeaconsList : Callback<MutableList<VYBeacon>> {


    private lateinit var listener: OnAPIResponseBeacons

    companion object {
        private val TAG: String = javaClass::class.java.simpleName
    }


    fun withListener(listener: OnAPIResponseBeacons) {
        this.listener = listener
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onResponse(call: Call<MutableList<VYBeacon>>, response: Response<MutableList<VYBeacon>>) {

        if (response.isSuccessful) {
            val beaconList = response.body()!!

            // Setting selected boarding beacon
            val boardingBeacons = beaconList.filter { beacon -> beacon.type == "boarding"}
            VYBeaconRepository.BOARDING_BEACON = boardingBeacons[0]

            // Setting selected station beacon
            val stationBeacons = beaconList.filter {beacon -> beacon.type == "station"}
            VYBeaconRepository.STATION_BEACON = stationBeacons[0]

            // fire off success call to callback if lists are "full"
            if (boardingBeacons.isNotEmpty() && stationBeacons.isNotEmpty())
               listener.onAPIResponseBeaconsSuccess()

        } else {
            Log.e(TAG, "/api/beacons ERR: " + response.body().toString())
        }
    }

    override fun onFailure(call: Call<MutableList<VYBeacon>>, t: Throwable) {
        t.printStackTrace()
    }


    interface OnAPIResponseBeacons {
        fun onAPIResponseBeaconsSuccess()
    }
}