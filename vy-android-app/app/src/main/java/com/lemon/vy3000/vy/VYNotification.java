package com.lemon.vy3000.vy;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import com.lemon.vy3000.MainActivity;
import com.lemon.vy3000.R;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.NOTIFICATION_SERVICE;

class VYNotification {

    static void displayOffBoardingNotification(Context ctx) {

        Intent intentCorrect = new Intent(ctx, MainActivity.class);
        intentCorrect.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intentCorrect.putExtra("notiFeedback", "tripEnded");
        PendingIntent pIntentCorrect = PendingIntent.getActivity(ctx, 0, intentCorrect, 0);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx)
                .setSmallIcon(R.drawable.vy_logo)
                .setContentTitle("Vy")
                .setContentText("Ticket stopped in Tønsberg (302 kr)")
                .setChannelId("vy")
                .setOngoing(true)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_MAX);

        builder.addAction(android.R.drawable.ic_menu_close_clear_cancel, "Dismiss", pIntentCorrect);

        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.notify(2, builder.build());
    }


    static void displayOnBoardingNotification(Context ctx) {
        Intent intentCorrect = new Intent(ctx, MainActivity.class);
        intentCorrect.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intentCorrect.putExtra("notiFeedback", "correct");
        PendingIntent pIntentCorrect = PendingIntent.getActivity(ctx, 0, intentCorrect, 0);

        Intent intentAddPassengers = new Intent(ctx, MainActivity.class);
        intentAddPassengers.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intentAddPassengers.putExtra("notiFeedback", "addPassengers");
        PendingIntent pIntentAddPassengers = PendingIntent.getActivity(ctx, 0, intentAddPassengers, 0);

        Intent intentMoreInfo = new Intent(ctx, MainActivity.class);
        intentMoreInfo.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intentMoreInfo.putExtra("notiFeedback", "moreInfo");
        PendingIntent pIntentMoreInfo = PendingIntent.getActivity(ctx, 0, intentMoreInfo, 0);

        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx)
                .setSmallIcon(R.drawable.vy_logo)
                .setContentTitle("Vy")
                .setContentText("Ticket activated @ " + currentTime + " from Oslo")
                .setChannelId("vy")
                .setOngoing(true)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_MAX);

        builder.addAction(android.R.drawable.ic_menu_agenda, "Correct", pIntentCorrect);
        builder.addAction(android.R.drawable.ic_menu_add, "Add passengers", pIntentAddPassengers);
        builder.addAction(android.R.drawable.ic_menu_directions, "More info", pIntentMoreInfo);

        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.notify(1, builder.build());
    }
}
