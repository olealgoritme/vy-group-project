package com.lemon.vy3000.vy.ticket

import com.google.gson.annotations.SerializedName
import com.lemon.vy3000.vy.beacon.VYBeaconEncounter
import java.util.*

class VYTicket {

    @SerializedName("ticketId") var ticketId: String? = null
    @SerializedName("price") var price: Int? = null

    private var arrivalTime: Date? = null
    private var departureTime: Date? = null
    private var started: Boolean = false
    var trainDestination: String? = null
    var boardingEncounter: VYBeaconEncounter? = null
    var disembarkingEncounter: VYBeaconEncounter? = null

    fun hasStarted(): Boolean {
        return this.started
    }

    fun start(ticketId: String, trainDestination: String) {
        this.ticketId = ticketId
        val time = Calendar.getInstance().time
        this.departureTime = time
        this.trainDestination = trainDestination
        this.started = true
    }

    fun stop() {
        val time = Calendar.getInstance().time
        this.arrivalTime = time
        this.started = false
    }

    fun resetTicket() {
        this.departureTime = null
        this.arrivalTime = null
        this.trainDestination = null
        this.boardingEncounter = null
        this.disembarkingEncounter = null
        this.price = 0
    }





}