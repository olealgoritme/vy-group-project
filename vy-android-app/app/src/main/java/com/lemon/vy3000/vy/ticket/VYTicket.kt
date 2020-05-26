package com.lemon.vy3000.vy.ticket

import com.lemon.vy3000.vy.beacon.VYBeaconEncounter

class VYTicket {
    var ticketId: String? = null
    var trainDestination: String? = null
    var arrivalTime: Long? = null
    var departureTime: Long? = null
    var boardingEncounter: VYBeaconEncounter? = null
    var disembarkingEncounter: VYBeaconEncounter? = null
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

    fun stop(arrivalTime: Long) {
        this.arrivalTime = arrivalTime
        this.started = false
    }

    fun resetTicket() {
        this.departureTime = null
        this.arrivalTime = null
        this.trainDestination = null
        this.boardingEncounter = null
        this.disembarkingEncounter = null
        this.ticketPrice = 0.0;
    }





}