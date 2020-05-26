package com.lemon.vy3000.vy.ticket

import com.lemon.vy3000.vy.beacon.VYBeaconEncounter

class VYTicket {
    var ticketId: String? = null
    var trainDestination: String? = null
    var arrivalTime: Long? = null
    var departureTime: Long? = null
    var beaconEncounter: VYBeaconEncounter? = null
    var ticketPrice: Double? = null

    fun hasStarted(): Boolean {
        return this.arrivalTime != null
    }

    fun hasEnded(): Boolean {
        return this.departureTime != null
    }

    fun start(ticketId: String, departureTime: Long, trainDestination: String) {
        this.ticketId = ticketId
        this.departureTime = departureTime
        this.trainDestination = trainDestination
    }

    fun stop(arrivalTime: Long) {
        this.arrivalTime = arrivalTime
    }

    fun resetTicket() {
        this.departureTime = null
        this.arrivalTime = null
        this.trainDestination = null
    }





}