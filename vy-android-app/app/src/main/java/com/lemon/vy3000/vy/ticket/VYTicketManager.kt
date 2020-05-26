package com.lemon.vy3000.vy.ticket

class VYTicketManager {

    fun getCurrentTrip(): VYTicket {
        return currentTrip;
    }

    companion object {

        private lateinit var currentTrip: VYTicket
        private var instance: VYTicketManager? = null

        @JvmStatic
        fun getInstance(): VYTicketManager? {
                if (instance == null) {
                    instance = VYTicketManager()
                    currentTrip = VYTicket()
                }
                return instance
            }

    }
}