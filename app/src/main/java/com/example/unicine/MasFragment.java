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
public class MasFragment extends Fragment {

    public MasFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MasFragment newInstance(String param1, String param2) {

        return new MasFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    TextView LLamar, MandarCorreo, facebook , twitter, youtube, instagram;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mas, container, false);

        LLamar = (TextView) view.findViewById(R.id.textViewLLamar);
        MandarCorreo = (TextView) view.findViewById(R.id.textViewMandarCorreo);
        facebook = (TextView) view.findViewById(R.id.textViewFacebook);
        twitter = (TextView) view.findViewById(R.id.textViewTwitter);
        youtube = (TextView) view.findViewById(R.id.textViewYoutube);
        instagram = (TextView) view.findViewById(R.id.textViewInstagram);

        LLamar.setOnClickListener(this::onClick);
        MandarCorreo.setOnClickListener(this::onClick);
        twitter.setOnClickListener(this::onClick);
        facebook.setOnClickListener(this::onClick);

        return view;
    }

    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.textViewLLamar:

                String phoneNumber = "tel:" + "647004975";
                Intent intenLLamar = new Intent(Intent.ACTION_CALL, Uri.parse(phoneNumber));
                    startActivity(intenLLamar);

                break;


            case R.id.textViewMandarCorreo:


                String destinatario = "alejandro2md32@gmail.com";
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + destinatario));
                startActivity(Intent.createChooser(intent, "Enviar correo electrónico"));



                break;


            case R.id.textViewFacebook:

                Toast.makeText(getContext(), "Este enlace no esta disponible por el momento", Toast.LENGTH_SHORT).show();

                break;


            case R.id.textViewTwitter:

                Intent intentTwitter = new Intent(Intent.ACTION_VIEW);
                intentTwitter.setData(Uri.parse("https://twitter.com/MinguezDur44540"));
                startActivity(intentTwitter);

                break;

            case R.id.textViewYoutube:

                Toast.makeText(getContext(), "Este enlace no esta disponible por el momento", Toast.LENGTH_SHORT).show();


                break;


            case R.id.textViewInstagram:


                Toast.makeText(getContext(), "Este enlace no esta disponible por el momento", Toast.LENGTH_SHORT).show();

                break;



        }

    }




}
