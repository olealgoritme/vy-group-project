package com.lemon.vy3000.vy.ticket

import com.google.gson.annotations.SerializedName
import com.lemon.vy3000.vy.beacon.VYBeaconEncounter
import java.util.*

class VYTicket {

    @SerializedName("ticketId") var ticketId: String? = null
    var trainDestination: String? = null
    var arrivalTime: Date? = null
    @SerializedName("departureTime") var departureTime: Long? = null
    var boardingEncounter: VYBeaconEncounter? = null
    var stationEncounter: VYBeaconEncounter? = null
    var ticketPrice: Double? = null
    var started: Boolean = false

    fun hasStarted(): Boolean {
        return this.started
    }

    fun hasEnded(): Boolean {
        return !this.started
    }

    fun start(ticketId: String, departureTime: Long, trainDestination: String) {
        this.ticketId = ticketId
        this.departureTime = departureTime
        this.trainDestination = trainDestination
        this.started = true
    }

    fun stop(arrivalTime: Date) {
        this.arrivalTime = arrivalTime
        this.started = false
    }

    fun resetTicket() {
        this.departureTime = null
        this.arrivalTime = null
        this.trainDestination = null
        this.boardingEncounter = null
        this.stationEncounter = null
        this.ticketPrice = 0.0;
    }





}