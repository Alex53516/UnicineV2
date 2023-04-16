package com.example.unicine;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CrearCuenta extends AppCompatActivity {

    EditText correo, usuario, contrasenyaNormal, contrasenyaNoVer, contrasenyaVer, telefono;
    TextView crearCuenta;
    ImageView ver, noVer;
    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);

        correo = (EditText) findViewById(R.id.editTextCorreoCrear);
        usuario = (EditText) findViewById(R.id.editTextUsuCrear);
        contrasenyaNormal = (EditText) findViewById(R.id.editTextTextContrasenyaCrear);
        contrasenyaNoVer = (EditText) findViewById(R.id.editTextTextContrasenyaVeriCrear);
        contrasenyaVer = (EditText) findViewById(R.id.editTextTextVerContrasenyaCrear);
        ver = (ImageView) findViewById(R.id.imageViewVerContraCrear);
        noVer = (ImageView) findViewById(R.id.imageViewNoVerContraCrear);
        crearCuenta = (TextView) findViewById(R.id.textViewCrearCuentaEnCrear);
        telefono = (EditText) findViewById(R.id.editTextTelefonoCrear);


        ver.setOnClickListener(this::onClick);
        noVer.setOnClickListener(this::onClick);
        crearCuenta.setOnClickListener(this::onClick);


        correo.requestFocus();

        noVer.setVisibility(View.INVISIBLE);
        contrasenyaVer.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();


    }

    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.imageViewVerContraCrear:

                noVer.setVisibility(View.VISIBLE);
                ver.setVisibility(View.INVISIBLE);
                contrasenyaVer.setText(contrasenyaNoVer.getText());
                contrasenyaNoVer.setVisibility(View.INVISIBLE);
                contrasenyaVer.setVisibility(View.VISIBLE);
                contrasenyaVer.requestFocus();
                break;

            case R.id.imageViewNoVerContraCrear:


                noVer.setVisibility(View.INVISIBLE);
                ver.setVisibility(View.VISIBLE);
                contrasenyaNoVer.setText(contrasenyaVer.getText());
                contrasenyaVer.setVisibility(View.INVISIBLE);
                contrasenyaNoVer.setVisibility(View.VISIBLE);
                contrasenyaNoVer.requestFocus();
                break;

            case R.id.textViewCrearCuentaEnCrear:

                String contra = contrasenyaNormal.getText().toString();
                String contraVer = contrasenyaVer.getText().toString();
                String contraNoVer = contrasenyaNoVer.getText().toString();

                if (contra.equals(contraVer) || contra.equals(contraNoVer)) {
                    String email = correo.getText().toString();
                    String password = contrasenyaNormal.getText().toString();
                    String displayName = usuario.getText().toString();

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        assert user != null;
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(displayName)
                                                .build();
                                        user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "El registro fue exitoso", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(getApplicationContext(), "El registro falló", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                        user.sendEmailVerification()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(getApplicationContext(), "Se ha enviado un correo electrónico de verificación a " + user.getEmail(), Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(getApplicationContext(), "Error al enviar el correo electrónico de verificación", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });


                                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                                        String email = correo.getText().toString();
                                        String name = usuario.getText().toString();
                                        String phone = telefono.getText().toString();
                                        FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();

                                        if (users != null) {
                                            String uid = users.getUid();
                                            Map<String, Object> userData = new HashMap<>();
                                            userData.put("email", email);
                                            userData.put("name", name);
                                            userData.put("phone", phone);
                                            db.collection("users").document(uid).set(userData)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Log.d(TAG, "User data added to Firestore.");
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Log.w(TAG, "Error adding user data to Firestore", e);
                                                        }
                                                    });

                                            Intent volverInicio = new Intent(getApplicationContext(), Login.class);
                                            startActivity(volverInicio);

                                        } else {
                                            Log.w(TAG, "User is not authenticated.");
                                        }

                                    } else {
                                        Toast.makeText(getApplicationContext(), "El registro falló", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }


                break;

        }

    }


}