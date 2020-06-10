package com.lemon.vy3000.vy.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.text.Html
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.lemon.vy3000.R
import com.lemon.vy3000.app.VYApp
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
        var station = vyTicket.disembarkingEncounter?.vyBeacon?.station
        if (station.isNullOrBlank()) station = "Nerdrum"

        val max = 100
        val progress = 45

        val builder = NotificationCompat.Builder(ctx, "vy").apply {
            setContentTitle(Html.fromHtml("<b>Avstigning: " + station + " - " + " Tognr. 1003 til " + vyTicket.trainDestination + "</b>"))
            setSubText(Html.fromHtml("Billett avsluttet. Betalt: kr " + vyTicket.price + ",-"))
            setSmallIcon(R.drawable.vy_logo)
            setProgress(max, progress, false)
            setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            priority = NotificationCompat.PRIORITY_MAX

        }
        notificationManager?.notify(0, builder.build())
    }

    @JvmStatic
    fun showBoardingNotification(ctx: Context, vyTicket: VYTicket) {
        val max = 100
        val progress = 25

        val builder = NotificationCompat.Builder(ctx, "vy").apply {
            setContentTitle(Html.fromHtml("<b>Påstigning: Oslo S" + " - Tognr. 1003 til " + vyTicket.trainDestination + "</b>"))
            setSubText(Html.fromHtml("Billett aktivert"))
            setSmallIcon(R.drawable.vy_logo)
            setProgress(max, progress, false)
            setVisibility(NotificationCompat.VISIBILITY_PUBLIC) // visible from lock screen
            priority = NotificationCompat.PRIORITY_MAX
        }
        notificationManager?.notify(0, builder.build());

    }

    @JvmStatic
    fun cancelAll() {
        notificationManager?.cancel(0)
        notificationManager?.cancelAll()
    }

    @JvmStatic
    fun showTrainInformation(ctx: Context) {

        val style = NotificationCompat.BigTextStyle()
        style.bigText(
                "\nForventet avgang: kl 14:41" +
                    "\nSitteplasser: 144/390" +
                    "\nLedige vogner: 4, 5, 7, 9")


        val builder = NotificationCompat.Builder(ctx, "vy").apply {

            setStyle(style)
            setContentTitle(Html.fromHtml("<b>Tog fra Oslo S med avgang kl 14:34 er forsinket</b>"))
            setSubText(Html.fromHtml("Forsinkelser"))
            setSmallIcon(R.drawable.vy_logo)
            setVisibility(NotificationCompat.VISIBILITY_PUBLIC) // visible from lock screen
            priority = NotificationCompat.PRIORITY_MAX
        }
        notificationManager?.notify(0, builder.build());
    }
}