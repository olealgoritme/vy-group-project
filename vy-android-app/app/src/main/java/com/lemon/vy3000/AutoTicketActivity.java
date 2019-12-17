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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import com.lemon.vy3000.ui.ruter.RuterFragment;
import com.lemon.vy3000.ui.search.SearchFragment;
import com.lemon.vy3000.ui.tickets.TicketsFragment;
import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnRightOutListener;

import java.util.ArrayList;

public class AutoTicketActivity extends AppCompatActivity {


        private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
        private static final String TAG = "Vy3000";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_autoticket);

            // Vy OnBoarding
            PaperOnboardingPage scr1 = new PaperOnboardingPage("Få den beste opplevelsen med Vy",
                    "Prøv vår nye tjeneste: Automatisk billettregistrering",
                    Color.parseColor("#CA606060"), R.drawable.app_images_profile_vystripes, R.drawable.vy_logo);

            // Automatic Ticket OnBoarding
            PaperOnboardingPage scr2 = new PaperOnboardingPage("Automatisk billettregistrering",
                    "Gjør togreisen enklere med automatisk registrering av billett via telefonen",
                    Color.parseColor("#008577"), R.drawable.onboarding_train, R.drawable.onboarding_train);


            // Location OnBoarding
            PaperOnboardingPage scr3 = new PaperOnboardingPage("Banks",
                    "We carefully verify all banks before add them into the app",
                    Color.parseColor("#008577"), R.drawable.onboarding_location, R.drawable.onboarding_location);


            // Bluetooth OnBoarding
            PaperOnboardingPage scr4 = new PaperOnboardingPage("Stores",
                    "All local stores are categorized for your convenience",
                    Color.parseColor("#008577"), R.drawable.onboaring_bluetooth, R.drawable.onboaring_bluetooth);

            ArrayList<PaperOnboardingPage> elements = new ArrayList<>();
            elements.add(scr1);
            elements.add(scr2);
            elements.add(scr3);
            elements.add(scr4);

            PaperOnboardingFragment onBoardingFragment = PaperOnboardingFragment.newInstance(elements);

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, onBoardingFragment);
            fragmentTransaction.commit();

            onBoardingFragment.setOnRightOutListener(new PaperOnboardingOnRightOutListener() {
                @Override
                public void onRightOut() {
                    /*FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment bf = new BlankFragment();
                    fragmentTransaction.replace(R.id.container, bf);
                    fragmentTransaction.commit();
               */
                }
            });



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
            builder.setTitle("VY trenger posisjonen din");
            builder.setMessage("Vennligst oppgi din posisjon for bedre brukeropplevelse!");
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
