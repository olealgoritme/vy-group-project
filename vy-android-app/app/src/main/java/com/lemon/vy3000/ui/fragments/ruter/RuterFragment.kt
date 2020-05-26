package com.lemon.vy3000.ui.fragments.ruter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lemon.vy3000.R

class RuterFragment : Fragment() {

    private var dashboardViewModel: RuterViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        dashboardViewModel = ViewModelProviders.of(this).get(RuterViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_ruter, container, false)
        val textView = root.findViewById<TextView>(R.id.text_ruter)

        dashboardViewModel!!.text.observe(viewLifecycleOwner, Observer { s -> textView.text = s })
        return root
    }
}