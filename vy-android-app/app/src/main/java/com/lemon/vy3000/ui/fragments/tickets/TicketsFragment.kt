package com.lemon.vy3000.ui.fragments.tickets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lemon.vy3000.R

class TicketsFragment : Fragment() {

    private var notificationsViewModel: TicketsViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        notificationsViewModel = ViewModelProviders.of(this).get(TicketsViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_tickets, container, false)
        val textView = root.findViewById<TextView>(R.id.text_tickets)

        notificationsViewModel!!.text.observe(viewLifecycleOwner, Observer { s -> textView.text = s })
        return root
    }
}