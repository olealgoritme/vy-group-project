package com.lemon.vy3000.vy.notifications

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.lemon.vy3000.app.VYApp

object VYNotification {

    private val ctx: Context = VYApp.context!!
    private val notificationManager = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private const val importance = NotificationManager.IMPORTANCE_HIGH
    private var builder: NotificationCompat.Builder? = null

    @JvmStatic
    fun enableNotifications() {
        /*
        val CHANNEL_ID = "vy"
        val name: CharSequence = "vy_channel"
        val Description = "VY3000 Channel"
        val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
        mChannel.description = Description
        mChannel.enableLights(true)
        mChannel.lightColor = Color.RED
        mChannel.enableVibration(true)
        mChannel.vibrationPattern = longArrayOf(400, 200, 200, 400)
        mChannel.setShowBadge(true)
        notificationManager.createNotificationChannel(mChannel)
         */
    }

    @JvmStatic
    fun displayOnDisembarkingNotification() { /*
        val disembark = Intent(ctx, MainActivity::class.java)
        disembark.putExtra("onNotificationClick", "onClickDisembark")
        val pBoarded = PendingIntent.getActivity(ctx, 0, disembark, 0)
        val inboxStyle = NotificationCompat.InboxStyle()
        inboxStyle.setBigContentTitle(Html.fromHtml("<b>Avstigning: Tønsberg stasjon - R11 til Skien</b>"))
        inboxStyle.addLine(Html.fromHtml("Billett avsluttet<br>Betalt: 258,-"))
        builder = NotificationCompat.Builder(ctx)
                .setStyle(inboxStyle)
                .setChannelId("vy")
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.vy_logo)
                .setPriority(NotificationCompat.PRIORITY_MAX)
        notificationManager.notify(0, builder.build())

        // stop looking for beacons - for now
        ctx.stopService(Intent(ctx, VYBeaconService::class.java))
        */
    }

    @JvmStatic
    fun displayOnBoardingNotification() {
        /*
        Intent board = new Intent(ctx, MainActivity.class);
        board.putExtra("onNotificationClick", "onClickBoard");
        PendingIntent pBoarded = PendingIntent.getActivity(ctx, 0, board, 0);

        NotificationCompat.InboxStyle inboxStyle= new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle(Html.fromHtml("<b>Påstigning: Tog R11 til Skien</b>"));
        inboxStyle.addLine(Html.fromHtml("Billett aktivert"));


        builder = new NotificationCompat.Builder(ctx)
                .setStyle(inboxStyle)
                .setChannelId("vy")
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.vy_logo)
                .setPriority(NotificationCompat.PRIORITY_MAX);
        notificationManager.notify(0, builder.build());

         */
    }
}