package com.lemon.vy3000.ui.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.lemon.vy3000.R;

import org.w3c.dom.Text;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SearchFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_search, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        ((TextView) root.findViewById(R.id.textView)).setText(getWelcomeText());

        return root;
    }

    private String getWelcomeText() {
        String text;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH");
        LocalDateTime currentHour = LocalDateTime.now();
        int currentHourFormatted = Integer.parseInt(dtf.format(currentHour));

        text = "";

        if (currentHourFormatted >= 4 && currentHourFormatted < 12) text = "morgen";
        else if (currentHourFormatted >= 12 && currentHourFormatted < 15) text = "dag";
        else if (currentHourFormatted >= 15 && currentHourFormatted < 18) text = "ettermiddag";
        else if ((currentHourFormatted >= 18 && currentHourFormatted < 24) || currentHourFormatted < 4) text = "kveld";

        return "God " + text;
    }
}