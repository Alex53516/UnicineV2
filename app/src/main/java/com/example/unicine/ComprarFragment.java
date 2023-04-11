package com.example.unicine;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComprarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComprarFragment extends Fragment {

    public ComprarFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ComprarFragment newInstance(String param1, String param2) {

            return new ComprarFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    TextView buscarCines, saludo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comprar, container, false);

        buscarCines = view.findViewById(R.id.textViewBuscarCines);
        buscarCines.setOnClickListener(this::onClick);
        saludo = view.findViewById(R.id.textViewSaludo);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        String user = Objects.requireNonNull(auth.getCurrentUser()).getDisplayName();

        saludo.setText("Hola " + user + ",");

        return view;
    }


    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.textViewBuscarCines:

                Intent buscar = new Intent(getContext(), ListaCines.class);
                startActivity(buscar);

                break;
        }

    }

}