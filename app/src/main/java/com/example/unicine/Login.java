package com.example.unicine;

import static android.content.ContentValues.TAG;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class Login extends AppCompatActivity {

    TextView emailTextView, telefonoTextView, men1, men2, olvi, men3, crearCuenta, login;
    EditText correo, contrasenya, telefono, editTextVerContra;
    ImageView verContra, noVerContra;
    private FirebaseAuth mAuth;
    boolean usarCorreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int color = ContextCompat.getColor(this, R.color.blanco_blanco);

        getWindow().setNavigationBarColor(color);

        FirebaseApp.initializeApp(this);

        emailTextView = (TextView) findViewById(R.id.textViewEmail);
        telefonoTextView = (TextView) findViewById(R.id.textViewTelefono);
        correo = (EditText) findViewById(R.id.editTextTextEmail);
        contrasenya = (EditText) findViewById(R.id.editTextTextContrasenya);
        telefono = (EditText) findViewById(R.id.editTextTelefono);
        men1 = (TextView) findViewById(R.id.textViewMensaje1);
        men2 = (TextView) findViewById(R.id.textViewMensaje2);
        olvi = (TextView) findViewById(R.id.textViewMensajeOlvidado);
        men3 = (TextView) findViewById(R.id.textViewMensaje3);
        verContra = (ImageView) findViewById(R.id.imageViewVerContra);
        editTextVerContra = (EditText) findViewById(R.id.editTextTextVerContrasenya);
        noVerContra = (ImageView) findViewById(R.id.imageViewNoVerContra);
        crearCuenta = (TextView) findViewById(R.id.textViewCrearCuenta);
        login = (TextView) findViewById(R.id.textViewLogin);

        men3.setVisibility(View.INVISIBLE);
        telefono.setVisibility(View.INVISIBLE);
        noVerContra.setVisibility(View.INVISIBLE);
        editTextVerContra.setVisibility(View.INVISIBLE);


        emailTextView.setOnClickListener(this::onClick);
        telefonoTextView.setOnClickListener(this::onClick);
        olvi.setOnClickListener(this::onClick);
        verContra.setOnClickListener(this::onClick);
        noVerContra.setOnClickListener(this::onClick);
        crearCuenta.setOnClickListener(this::onClick);
        login.setOnClickListener(this::onClick);

        correo.requestFocus();

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            // El usuario ya ha iniciado sesión, redirigirlo a la pantalla principal
            startActivity(new Intent(this, PantallaPrincipal.class));
            finish();
        }

        usarCorreo = true;


    }

    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.textViewEmail:

                // Obtengo un objeto Drawable del ID de recurso drawable utilizando ContextCompat
                Drawable naranja1 = ContextCompat.getDrawable(this, R.drawable.round_button_rojo);

                // Obtengo un objeto Drawable del ID de recurso drawable utilizando ContextCompat
                Drawable verde1 = ContextCompat.getDrawable(this, R.drawable.round_button_azul);

                emailTextView.setBackground(naranja1);
                telefonoTextView.setBackground(verde1);

                men1.setVisibility(View.VISIBLE);
                correo.setVisibility(View.VISIBLE);
                men3.setVisibility(View.INVISIBLE);
                telefono.setVisibility(View.INVISIBLE);


                break;

            case R.id.textViewTelefono:

                // Obtengo un objeto Drawable del ID de recurso drawable utilizando ContextCompat
                Drawable naranja2 = ContextCompat.getDrawable(this, R.drawable.round_button_rojo);

                // Obtengo un objeto Drawable del ID de recurso drawable utilizando ContextCompat
                Drawable verde2 = ContextCompat.getDrawable(this, R.drawable.round_button_azul);

                emailTextView.setBackground(verde2);
                telefonoTextView.setBackground(naranja2);

                men1.setVisibility(View.INVISIBLE);
                correo.setVisibility(View.INVISIBLE);
                men3.setVisibility(View.VISIBLE);
                telefono.setVisibility(View.VISIBLE);
                usarCorreo = false;


                break;

            case R.id.textViewMensajeOlvidado:

                Intent recuperarContra = new Intent(this, ContrasenyaOlvidada.class);
                startActivity(recuperarContra);

                break;


            case R.id.imageViewVerContra:

                editTextVerContra.setText(contrasenya.getText());
                verContra.setVisibility(View.INVISIBLE);
                noVerContra.setVisibility(View.VISIBLE);
                contrasenya.setVisibility(View.INVISIBLE);
                editTextVerContra.setVisibility(View.VISIBLE);
                editTextVerContra.requestFocus();

                break;


            case R.id.imageViewNoVerContra:

                verContra.setVisibility(View.VISIBLE);
                noVerContra.setVisibility(View.INVISIBLE);
                contrasenya.setVisibility(View.VISIBLE);
                editTextVerContra.setVisibility(View.INVISIBLE);
                contrasenya.requestFocus();
                contrasenya.setText(editTextVerContra.getText());

                break;

            case R.id.textViewCrearCuenta:

                Intent irCrearCuenta = new Intent(this, CrearCuenta.class);
                startActivity(irCrearCuenta);


                break;


            case R.id.textViewLogin:

                if (usarCorreo == true){


                    // Obtener el correo electrónico y la contraseña ingresados por el usuario
                    String email = correo.getText().toString();
                    String password = editTextVerContra.getText().toString();

                    if (password.isEmpty()){

                        password = contrasenya.getText().toString();

                    }


                    Intent irPantallaPrincipal = new Intent(this, PantallaPrincipal.class);

                    // Autenticar al usuario utilizando Firebase Authentication
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        assert user != null;
                                        if (user.isEmailVerified()) {
                                            // El inicio de sesión fue exitoso y el correo electrónico está verificado, continuar con la aplicación
                                            startActivity(irPantallaPrincipal);
                                        } else {
                                            // El correo electrónico no está verificado, mostrar un mensaje de error
                                            Toast.makeText(getApplicationContext(), "Debe verificar su correo electrónico antes de iniciar sesión", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        // El inicio de sesión falló, mostrar un mensaje de error
                                        Toast.makeText(getApplicationContext(), "Credenciales inválidas", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }else{

                    String tel = telefono.getText().toString();
                    String contra = editTextVerContra.getText().toString();

                    if (contra.isEmpty()){

                        contra = contrasenya.getText().toString();

                    }


                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                    // Obtener una referencia a la colección de usuarios
                    CollectionReference usersRef = db.collection("users");

                    // Hacer una consulta para encontrar el documento que contiene el número de teléfono que estás buscando
                    Query query = usersRef.whereEqualTo("phone", tel);
                    String finalContra = contra;
                    query.get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (!querySnapshot.isEmpty()) {
                                // Obtener el primer documento que cumple con la condición de consulta
                                DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                                // Obtener los valores de correo y contraseña del documento
                                String correoData = documentSnapshot.getString("email");

                                Log.d("TAG", "Correo: " + correoData);

                                    Intent irPantallaPrincipal = new Intent(this, PantallaPrincipal.class);

                                    // Autenticar al usuario utilizando Firebase Authentication
                                    assert correoData != null;
                                mAuth.signInWithEmailAndPassword(correoData, finalContra)
                                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()) {
                                                        FirebaseUser user = mAuth.getCurrentUser();
                                                        assert user != null;
                                                        if (user.isEmailVerified()) {
                                                            // El inicio de sesión fue exitoso y el correo electrónico está verificado, continuar con la aplicación
                                                            startActivity(irPantallaPrincipal);
                                                        } else {
                                                            // El correo electrónico no está verificado, mostrar un mensaje de error
                                                            Toast.makeText(getApplicationContext(), "Debe verificar su correo electrónico antes de iniciar sesión", Toast.LENGTH_SHORT).show();
                                                        }
                                                    } else {
                                                        // El inicio de sesión falló, mostrar un mensaje de error
                                                        Toast.makeText(getApplicationContext(), "Credenciales inválidas", Toast.LENGTH_SHORT).show();

                                                    }
                                                }
                                            });
                            } else {
                                // No se encontró ningún documento que cumpla con la condición de consulta
                                Toast.makeText(getApplicationContext(), "Credenciales inválidas", Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            // Error al hacer la consulta
                        }
                    });



                }

                break;



        }

    }
}