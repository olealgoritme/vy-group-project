package com.lemon.vy3000.vy.notifications

import android.content.Context
import com.lemon.vy3000.R
import com.lemon.vy3000.vy.ticket.VYTicket
import io.karn.notify.Notify

class VYNotifications {

    companion object {
        @JvmStatic
        fun showBoarding(ctx: Context, vyTicket: VYTicket) {
            Notify
                .with(ctx)
                .header {
                    icon = R.drawable.vy_logo
                }
                .asBigText {
                    title = "Påstigning: Tog $vyTicket.trainNumber, vogn $vyTicket.trainCarriageNumber til $vyTicket.trainDestination"
                    text = "Billett aktivert!"
                    expandedText = "Billett aktivert!"
                    bigText = "Påstigning: Tog$vyTicket.trainNumber, vogn $vyTicket.trainCarriageNumber til $vyTicket.trainDestination"
                }
                .show()
        }

        @JvmStatic
        fun showStation(ctx: Context, vyTicket: VYTicket, ticketPrice: Int) {
            Notify
                .with(ctx)
                .header {
                    icon = R.drawable.vy_logo
                }
                .asBigText {
                    title = "Avstigning: Tog $vyTicket.trainNumber, vogn $vyTicket.trainCarriageNumber til $vyTicket.trainDestination"
                    text = "Billett avsluttet! Betalt: $ticketPrice kr"
                    expandedText = "Billett avsluttet! Betalt: $ticketPrice kr"
                    bigText = "Avstigning: Tog $vyTicket.trainNumber, vogn $vyTicket.trainCarriageNumber til $vyTicket.trainDestination"
                }
                .show()
        }

    }
}