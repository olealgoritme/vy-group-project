package com.lemon.vy3000.vy;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Html;

import androidx.core.app.NotificationCompat;
import com.lemon.vy3000.MainActivity;
import com.lemon.vy3000.R;
import static android.content.Context.NOTIFICATION_SERVICE;

public class VYNotification {

    public static void displayOffBoardingNotification(Context ctx) {

        NotificationCompat.InboxStyle inboxStyle= new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle(Html.fromHtml("<b>Avstigning: Tønsberg stasjon - R11 til Skien</b>"));
        inboxStyle.addLine(Html.fromHtml("Billett avsluttet<br>Betalt: 258,-"));


        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx)
                .setStyle(inboxStyle)
                .setChannelId("vy")
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.vy_logo)
                .setPriority(NotificationCompat.PRIORITY_MAX);


        // OK Button
        //builder.addAction(android.R.drawable.ic_menu_close_clear_cancel, "OK", pOffBoarded);


        // stop looking for beacons
        VYApp.dontLookForBeacons();

        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(0);
        notificationManager.notify(0, builder.build());
    }


    public static void displayOnBoardingNotification(Context ctx) {



        Intent boarded = new Intent(ctx, MainActivity.class);
        boarded.putExtra("onNotificationClick", "onClickBoarded");
        PendingIntent pBoarded = PendingIntent.getActivity(ctx, 0, boarded, 0);

        NotificationCompat.InboxStyle inboxStyle= new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle(Html.fromHtml("<b>Påstigning: Tog R11 til Skien</b>"));
        inboxStyle.addLine(Html.fromHtml("Billett aktivert"));


        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx)
                .setStyle(inboxStyle)
                .setChannelId("vy")
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.vy_logo)
                .setPriority(NotificationCompat.PRIORITY_MAX);

        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }
}
