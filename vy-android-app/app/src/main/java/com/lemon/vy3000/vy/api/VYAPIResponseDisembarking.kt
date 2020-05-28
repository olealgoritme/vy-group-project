package com.lemon.vy3000.vy.api

import android.util.Log
import com.lemon.vy3000.vy.ticket.VYTicket
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class VYAPIResponseDisembarking: Callback<VYTicket> {

    private lateinit var listener: OnAPIResponseDisembarking

    companion object {
        private val TAG: String = javaClass::class.java.simpleName
    }

    fun withListener(listener: OnAPIResponseDisembarking) {
        this.listener = listener
    }

    override fun onResponse(call: Call<VYTicket>, response: Response<VYTicket>) {

        Log.e(TAG, "/api/disembarking RESP: $response");

        if (response.isSuccessful) {
            val price = response.body()!!.price
            this.listener.onAPIDisembarkingSuccess(price as Int)
        } else {
            Log.e(TAG, "/api/disembarking ERR headers: ${response.headers()}");
            Log.e(TAG, "/api/disembarking ERR: ${response.message()}");
        }
    }

    override fun onFailure(call: Call<VYTicket>, t: Throwable) {
        t.printStackTrace()
    }
}

interface OnAPIResponseDisembarking {
    fun onAPIDisembarkingSuccess(price: Int)
}

