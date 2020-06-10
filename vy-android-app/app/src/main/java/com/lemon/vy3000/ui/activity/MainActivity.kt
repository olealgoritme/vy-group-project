package com.lemon.vy3000.ui.activity

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.lemon.vy3000.R
import com.lemon.vy3000.app.VYApp
import com.lemon.vy3000.misc.SnackbarWrapper
import com.lemon.vy3000.ui.fragment.search.SearchFragment
import com.lemon.vy3000.vy.notifications.VYNotification
import com.lemon.vy3000.vy.notifications.VYNotification.enableNotifications


@RequiresApi(api = Build.VERSION_CODES.Q)
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
        val appBarConfiguration = AppBarConfiguration.Builder(
                R.id.navigation_search,
                R.id.navigation_ruter,
                R.id.navigation_tickets,
                R.id.navigation_favourites,
                R.id.navigation_profile)
                .build()
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(navView, navController)

        enableNotifications()
        checkForNotificationIntent()
        requestPermission()

    }

    private fun checkForNotificationIntent() {
        val feedback = intent.getStringExtra("onNotificationClick")

        // Definitely launched from notify (pending) intent
        if (feedback != null) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            when (feedback) {
                "onClickBoard" -> {
                    val searchFragment = SearchFragment()
                    fragmentTransaction.replace(android.R.id.content, searchFragment)
                }

                "onClickDisembark" -> {
                    VYNotification.cancelAll()
                    VYNotification.showDisembarkingNotification(applicationContext, VYApp.getTicketManager().getCurrentTrip())
                    VYApp.getTicketManager().getCurrentTrip().stop()
                }

                "onClickContinue" -> {
                    VYNotification.cancelAll()
                    SnackbarWrapper.make(this, "Fortsetter reisen videre. Nyt turen!", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun requestPermission() {

            if (this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                reqLocationDialog()
            if (this.checkSelfPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    reqLocationDialog()
    }

    private fun reqLocationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Vy trenger posisjonen din")
        builder.setMessage("Vennligst oppgi din posisjon for bedre brukeropplevelse!")
        builder.setPositiveButton(android.R.string.ok, null)
        builder.setOnDismissListener {
            requestPermissions(arrayOf(
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                    PERMISSION_REQUEST_BACKGROUND_LOCATION)
        }
        builder.show()

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        when (requestCode) {
            PERMISSION_REQUEST_BACKGROUND_LOCATION -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e(TAG, "background location permission granted")
                } else {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Begrenset funksjonalitet")
                    builder.setMessage("Uten tilgang til Bluetooth og posisjonering vil ikke appen kunne spore beacons i bakgrunnen.")
                    builder.setPositiveButton(android.R.string.ok, null)
                    builder.setOnDismissListener {
                    }
                    builder.show()
                }
            }
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_BACKGROUND_LOCATION = 1337
        private const val TAG = "MainActivity"
    }


}