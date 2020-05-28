package com.lemon.vy3000.vy.api

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.lemon.vy3000.vy.beacon.VYBeacon
import com.lemon.vy3000.vy.beacon.VYBeaconRepository
import org.altbeacon.beacon.Identifier
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class VYAPIResponseBeaconsList : Callback<MutableList<VYBeacon>> {

    companion object {
        private val TAG: String = javaClass::class.java.simpleName
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onResponse(call: Call<MutableList<VYBeacon>>, response: Response<MutableList<VYBeacon>>) {

        if (response.isSuccessful) {
            val beaconList = response.body()!!


            // Setting selected boarding beacon
            val boardingBeacons = beaconList.filter { beacon -> beacon.type == "boarding"}
            val selectedBoardingBeacon = boardingBeacons[0]
            val selectedBoardingBeaconId = Identifier.fromUuid(UUID.fromString(selectedBoardingBeacon.uuid))
            VYBeaconRepository.BOARDING_BEACON_ID = selectedBoardingBeaconId


            // Setting selected station beacon
            val stationBeacons = beaconList.filter {beacon -> beacon.type == "station"}
            val selectedStationBeacon = stationBeacons[0]
            val selectedStationBeaconId = Identifier.fromUuid(UUID.fromString(selectedStationBeacon.uuid))
            VYBeaconRepository.BOARDING_BEACON_ID = selectedStationBeaconId

        } else {
            Log.e(TAG, response.body().toString());
        }
    }

    override fun onFailure(call: Call<MutableList<VYBeacon>>, t: Throwable) {
        t.printStackTrace()
    }
}