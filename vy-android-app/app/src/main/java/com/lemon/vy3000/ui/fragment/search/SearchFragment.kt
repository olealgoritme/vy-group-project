package com.lemon.vy3000.ui.fragment.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.lemon.vy3000.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SearchFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_search, container, false)

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        (root.findViewById<View>(R.id.textView) as TextView).text = welcomeText

        return root
    }

    private val welcomeText: String
        get() {
            var text: String? = null
            val dtf = DateTimeFormatter.ofPattern("HH")
            val currentHour = LocalDateTime.now()
            val currentHourFormatted = dtf.format(currentHour).toInt()

            if (currentHourFormatted in 4..11) text = "morgen" else
                if (currentHourFormatted in 12..14) text = "dag" else
                    if (currentHourFormatted in 15..17) text = "ettermiddag" else
                        if (currentHourFormatted in 18..23 || currentHourFormatted < 4) text = "kveld"
            return "God $text"
        }
}