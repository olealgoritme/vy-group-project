package com.lemon.vy3000.vy.api

import android.util.Log
import com.lemon.vy3000.vy.ticket.VYTicket
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VYBoardingResponseHandler: Callback<VYTicket> {

    companion object {
        private val TAG: String = javaClass::class.java.simpleName
    }

    override fun onResponse(call: Call<VYTicket>, response: Response<VYTicket>) {
        if (response.isSuccessful) {
            val vyTicket = response.body()
            Log.e(TAG, "Got response: $vyTicket");
        } else {
            Log.e(TAG, "err:" + response.body().toString());
        }
    }

    override fun onFailure(call: Call<VYTicket>, t: Throwable) {
        t.printStackTrace()
    }
}