package com.lemon.vy3000.ui.fragment.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.lemon.vy3000.R

class FavouritesFragment : Fragment() {

    private var favouritesViewModel: FavouritesViewModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        favouritesViewModel = ViewModelProviders.of(this).get(FavouritesViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_favourites, container, false)
        val textView = root.findViewById<TextView>(R.id.text_favourites)

        favouritesViewModel!!.text.observe(viewLifecycleOwner, Observer { s -> textView.text = s })
        return root
    }
}