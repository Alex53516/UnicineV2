package com.example.unicine;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.CorrectionInfo;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PasesFragment extends Fragment {

    public PasesFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PasesFragment newInstance(String param1, String param2) {

        return new PasesFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pases, container, false);

        return view;
    }

    public void onClick(View v) {

        switch (v.getId()) {

        }

    }




}
