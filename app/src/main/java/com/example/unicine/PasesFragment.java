package com.example.unicine;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.CorrectionInfo;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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

        if (getArguments() != null) {

            ArrayList<String> fechaPeliculas = getArguments().getStringArrayList("fechaPeliculas");
            ArrayList<String> horaPeliculas = getArguments().getStringArrayList("horaPeliculas");
            ArrayList<String> sala = getArguments().getStringArrayList("sala");
            ArrayList<String> idCines = getArguments().getStringArrayList("idCine");
            ArrayList<String> idSesion = getArguments().getStringArrayList("idSesion");



            for (int i = 0; i < fechaPeliculas.size(); i++) {
                String fecha = fechaPeliculas.get(i);
                String hora = horaPeliculas.get(i);
                String salaNombre = sala.get(i);

                Log.d("PasesInfo", "Fecha: " + fecha + ", Hora: " + hora + ", Sala: " + salaNombre);
            }




            DatosAdapter adapterDatos = new DatosAdapter(fechaPeliculas, horaPeliculas, sala, idCines, idSesion);
            RecyclerView recyclerView = view.findViewById(R.id.recycler_sesion);
            recyclerView.setAdapter(adapterDatos);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        }




        return view;
    }

    public void onClick(View v) {

        switch (v.getId()) {

        }

    }




}
