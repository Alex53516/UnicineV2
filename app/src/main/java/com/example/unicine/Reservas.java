package com.example.unicine;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Reservas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas);

        // Obtiene la instancia actual de FirebaseAuth
        FirebaseAuth auth = FirebaseAuth.getInstance();
        // Obtiene el usuario conectado
        FirebaseUser user = auth.getCurrentUser();

        String userId = user.getDisplayName();






    }
}