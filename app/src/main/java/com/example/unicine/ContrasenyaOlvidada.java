package com.example.unicine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.checkerframework.checker.nullness.qual.NonNull;

public class ContrasenyaOlvidada extends AppCompatActivity {

    TextView volverIni, enviarCorreoVeri;
    EditText correoVeri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrasenya_olvidada);

        volverIni = (TextView) findViewById(R.id.textViewVolverIni);
        enviarCorreoVeri = (TextView) findViewById(R.id.textViewEnviarCorreoVeri);
        correoVeri = (EditText) findViewById(R.id.editTextCorreoVeri);
        
        volverIni.setOnClickListener(this::onClick);
        enviarCorreoVeri.setOnClickListener(this::onClick);


    }



    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.textViewVolverIni:

                Intent volver = new Intent(this, Login.class);
                startActivity(volver);
               
                break;

            case R.id.textViewEnviarCorreoVeri:

                FirebaseAuth auth = FirebaseAuth.getInstance();
                String emailAddress = correoVeri.getText().toString();

                auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Se ha enviado un correo electrónico para restablecer la contraseña.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                break;


        }

    }
    
}