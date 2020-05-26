package com.lemon.vy3000.vy.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.text.Html
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.lemon.vy3000.R
import com.lemon.vy3000.app.VYApp
import com.lemon.vy3000.ui.activity.MainActivity
import com.lemon.vy3000.vy.ticket.VYTicket

@RequiresApi(Build.VERSION_CODES.O)
object VYNotification {

    private var notificationManager: NotificationManagerCompat? = null
    private const val importance = NotificationManager.IMPORTANCE_HIGH

    init {
        notificationManager = NotificationManagerCompat.from(VYApp.context())
    }

    @JvmStatic
    fun enableNotifications() {
        val channelId = "vy"
        val name: CharSequence = "vy_channel"
        val description = "vy3000 channel"
        val mChannel = NotificationChannel(channelId, name, importance)
        mChannel.description = description
        mChannel.enableLights(true)
        mChannel.lightColor = Color.RED
        mChannel.enableVibration(true)
        mChannel.vibrationPattern = longArrayOf(400, 200, 200, 400)
        mChannel.setShowBadge(true)
        notificationManager?.createNotificationChannel(mChannel)
    }

    @JvmStatic
    fun showDisembarkingNotification(ctx: Context, vyTicket: VYTicket) {
        val disembark = Intent(ctx, MainActivity::class.java)
        disembark.putExtra("onNotificationClick", "onClickDisembark")

        val pBoarded = PendingIntent.getActivity(ctx, 0, disembark, 0)
        val inboxStyle = NotificationCompat.InboxStyle()
        inboxStyle.setBigContentTitle(Html.fromHtml("<b>Avstigning: Tønsberg stasjon - R11 til " + vyTicket.trainDestination  + "</b>"))
        inboxStyle.addLine(Html.fromHtml("Billett avsluttet<br>Betalt: 258,-"))

        val builder = NotificationCompat.Builder(ctx, "vy")
                .setStyle(inboxStyle)
                .setChannelId("vy")
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.vy_logo)
                .setPriority(NotificationCompat.PRIORITY_MAX)

        notificationManager?.notify(0, builder.build())
    }

    @JvmStatic
    fun showBoardingNotification(ctx: Context, vyTicket: VYTicket) {

        val board = Intent(ctx, MainActivity::class.java);
        board.putExtra("onNotificationClick", "onClickBoard");

        val pBoarded = PendingIntent.getActivity(ctx, 0, board, 0);
        val inboxStyle= NotificationCompat.InboxStyle()

        inboxStyle.setBigContentTitle(Html.fromHtml("<b>Påstigning: Tog R11 til " +  vyTicket.trainDestination + "</b>"))
        inboxStyle.addLine(Html.fromHtml("Billett aktivert"));

        val builder = NotificationCompat.Builder(ctx, "vy")
                .setStyle(inboxStyle)
                .setChannelId("vy")
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.vy_logo)
                .setPriority(NotificationCompat.PRIORITY_MAX)
        notificationManager?.notify(0, builder.build());
    }
}