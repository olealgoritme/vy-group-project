package com.lemon.vy3000;

import android.Manifest;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lemon.vy3000.ui.ruter.RuterFragment;
import com.lemon.vy3000.ui.search.SearchFragment;
import com.lemon.vy3000.ui.tickets.TicketsFragment;
import com.lemon.vy3000.vy.VYApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {


        private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
        private static final String TAG = "Vy3000";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);


            BottomNavigationView navView = findViewById(R.id.nav_view);
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_search,
                    R.id.navigation_ruter,
                    R.id.navigation_tickets,
                    R.id.navigation_favourites,
                    R.id.navigation_profile)
                    .build();

            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(navView, navController);

            checkForNotificationFeedback();
            enableNotifications();
            enablePosition();
        }

    private void checkForNotificationFeedback() {
        // Launched from Notification intent?
        String feedback = getIntent().getStringExtra("notiFeedback");

        // Definitely launched from notify (pending) intent
        if (feedback != null) {

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            switch (feedback) {

                case "tripEnded":
                    SearchFragment sFrag = new SearchFragment();
                    fragmentTransaction.replace(android.R.id.content, sFrag);
                    break;
                case "correct":
                    SearchFragment searchFragment = new SearchFragment();
                    fragmentTransaction.replace(android.R.id.content, searchFragment);
                    break;

                case "addPassengers":
                    TicketsFragment notificationFragment = new TicketsFragment();
                    fragmentTransaction.replace(android.R.id.content, notificationFragment);

                    break;
                case "moreInfo":
                    RuterFragment dashboardFragment = new RuterFragment();
                    fragmentTransaction.replace(android.R.id.content, dashboardFragment);
                    break;
            }

            // Remove notifications
            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
            notificationManager.cancelAll();
        }
    }

    private void enablePosition() {

        // Android M Permission check 
        if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("VY needs your position");
            builder.setMessage("Please enable your position to let us give you a better experience");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                }
            });
            builder.show();
        }
    }

    private void enableNotifications() {

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                String CHANNEL_ID = "vy";
                CharSequence name = "vy_channel";
                String Description = "VY3000 Channel";
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                mChannel.setDescription(Description);
                mChannel.enableLights(true);
                mChannel.setLightColor(Color.RED);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{400, 200, 200, 400});
                mChannel.setShowBadge(true);
                assert notificationManager != null;
                notificationManager.createNotificationChannel(mChannel);
            }

        }


        @Override
        public void onRequestPermissionsResult(int requestCode,
                                               String permissions[], int[] grantResults) {
            if (requestCode == PERMISSION_REQUEST_COARSE_LOCATION) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "coarse location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
            }
        }
}
