package com.lemon.vy3000.ui.fragments.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lemon.vy3000.R

class ProfileFragment : Fragment() {

    private var profileViewModel: ProfileViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        val textView = root.findViewById<TextView>(R.id.text_profile)

        profileViewModel!!.text.observe(viewLifecycleOwner, Observer { s -> textView.text = s })
        return root
    }
}