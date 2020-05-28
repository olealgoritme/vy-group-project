package com.lemon.vy3000.vy.api

import android.util.Log
import com.lemon.vy3000.vy.ticket.VYTicket
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VYAPIResponseBoarding: Callback<VYTicket> {

    private lateinit var listener: OnAPIResponseBoarding

    companion object {
        private val TAG: String = javaClass::class.java.simpleName
    }

    fun withListener(listener: OnAPIResponseBoarding) {
        this.listener = listener
    }

    override fun onResponse(call: Call<VYTicket>, response: Response<VYTicket>) {
        if (response.isSuccessful) {
            val ticketInfo = response.body()
            Log.e(TAG, "Got /api/boarding response: $ticketInfo");

            val ticketId = ticketInfo!!.ticketId
            val trainDestination = ticketInfo.trainDestination

            if (ticketId != null && trainDestination != null) {
                this.listener.onAPIBoardingSuccess(ticketId, trainDestination)
            }

        } else {
            Log.e(TAG, "err:" + response.body().toString());
        }
    }

    override fun onFailure(call: Call<VYTicket>, t: Throwable) {
        t.printStackTrace()
    }

    interface OnAPIResponseBoarding {
        fun onAPIBoardingSuccess(ticketId: String, trainDestination: String)
    }
}

