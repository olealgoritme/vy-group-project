package com.lemon.vy3000.ui.activity

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lemon.vy3000.R
import com.ramotion.paperonboarding.PaperOnboardingFragment
import com.ramotion.paperonboarding.PaperOnboardingPage
import java.util.*

class AutoTicketActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_autoticket)

        // Vy OnBoarding
        val scr1 = PaperOnboardingPage("Få den beste opplevelsen med Vy",
                "Prøv vår nye tjeneste: Automatisk billettregistrering",
                Color.parseColor("#CA606060"), R.drawable.app_images_profile_vystripes, R.drawable.vy_logo)

        // Automatic Ticket OnBoarding
        val scr2 = PaperOnboardingPage("Automatisk billettregistrering",
                "Gjør togreisen enklere med automatisk registrering av billett via telefonen",
                Color.parseColor("#008577"), R.drawable.onboarding_train, R.drawable.onboarding_train)


        // Location OnBoarding
        val scr3 = PaperOnboardingPage("Banks",
                "We carefully verify all banks before add them into the app",
                Color.parseColor("#008577"), R.drawable.onboarding_location, R.drawable.onboarding_location)


        // Bluetooth OnBoarding
        val scr4 = PaperOnboardingPage("Stores",
                "All local stores are categorized for your convenience",
                Color.parseColor("#008577"), R.drawable.onboaring_bluetooth, R.drawable.onboaring_bluetooth)
        val elements = ArrayList<PaperOnboardingPage>()
        elements.add(scr1)
        elements.add(scr2)
        elements.add(scr3)
        elements.add(scr4)
        val onBoardingFragment = PaperOnboardingFragment.newInstance(elements)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragment_container, onBoardingFragment)
        fragmentTransaction.commit()
        onBoardingFragment.setOnRightOutListener { /*FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment bf = new BlankFragment();
                    fragmentTransaction.replace(R.id.container, bf);
                    fragmentTransaction.commit();
               */
        }
    }
}