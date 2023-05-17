package com.example.unicine;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;


public class VisualizarTicket extends AppCompatActivity {

    TextView cin, sal,fech, hor, asient, usuari, pelicu, cancelarReserva, idTickets, preci;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_ticket);

        int color = ContextCompat.getColor(this, R.color.azul);
        getWindow().setNavigationBarColor(color);
        getWindow().setStatusBarColor(color);

        ImageView imageView = findViewById(R.id.qr_code);

        cin = findViewById(R.id.textViewCineTicket);
        sal = findViewById(R.id.textViewSalaTicket);
        fech = findViewById(R.id.textViewFechaTicket);
        hor = findViewById(R.id.textViewHoraTicket);
        asient = findViewById(R.id.textViewAsientosTicket);
        usuari = findViewById(R.id.textViewClienteTicket);
        pelicu = findViewById(R.id.textViewPeliculaTicket);
        cancelarReserva = findViewById(R.id.textViewCancerlarReserva);
        idTickets = findViewById(R.id.textViewIdTicket);
        preci = findViewById(R.id.textViewPrecioTicket);

        String cine = getIntent().getStringExtra("cine");
        String sala = getIntent().getStringExtra("sala");
        String fecha = getIntent().getStringExtra("fecha");
        String hora = getIntent().getStringExtra("hora");
        String asientos = getIntent().getStringExtra("asientos");
        String usuario = getIntent().getStringExtra("usuario");
        String pelicula = getIntent().getStringExtra("pelicula");
        String idTicket = getIntent().getStringExtra("idTicket");
        String idSesiones= getIntent().getStringExtra("idSesiones");
        String precio;


        cin.setText(cine);
        sal.setText(sala);
        fech.setText(fecha);
        hor.setText(hora);
        asient.setText(asientos);
        usuari.setText(usuario);
        pelicu.setText(pelicula);
        idTickets.setText(idTicket);

        String numeros = asientos;
        String numerosSinEspacios = numeros.replace(" ", ""); // Elimina los espacios en blanco
        String[] numerosArray = numerosSinEspacios.split(","); // Separa los números utilizando la coma como delimitador
        double numeroDeNumeros = numerosArray.length * 6.8; // Obtiene el número de elementos en el arreglo
        precio = String.valueOf(numeroDeNumeros);

        System.out.println("El número de números es: " + numeroDeNumeros);

        preci.setText(precio + "€");

        Log.d("Firestore", "IdTicket" + ": " + idTicket);
        Log.d("Firestore", "IdSesion" + ": " + idSesiones);



        String text = cine + "\n" +
                sala + "\n" +
                pelicula + "\n" +
                fecha + "\n" +
                hora + "\n" +
                asientos + "\n" +
                usuario + "\n" +
                precio+ "\n" +
                idTicket;

        int size = 500; // Tamaño del código QR

        // Crea un objeto QRCodeWriter y genera el código QR
        QRCodeWriter  writer = new QRCodeWriter ();
        try {
            BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, size, size);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? getResources().getColor(R.color.negro) : getResources().getColor(R.color.blanco_blanco));
                }
            }
            imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        cancelarReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("ticket").document(idTicket)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        // Aquí puedes obtener los atributos del documento
                                        List<String> asientosReser = (List<String>) document.get("Asientos");
                                        // Borrar los asientos del documento de "sesiones"
                                        db.collection("sesiones").document(idSesiones)
                                                .update("AsientosReservados", FieldValue.arrayRemove(asientosReser.toArray()))
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d(TAG, "Asientos eliminados con éxito de la colección \"sesiones\"");
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w(TAG, "Error al eliminar los asientos de la colección \"sesiones\"", e);
                                                    }
                                                });

                                        // Borrar el documento de "ticket"
                                        db.collection("ticket").document(idTicket)
                                                .delete()
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d(TAG, "Documento eliminado con éxito de la colección \"ticket\"");
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w(TAG, "Error al eliminar el documento de la colección \"ticket\"", e);
                                                    }
                                                });
                                    } else {
                                        Log.d(TAG, "El documento no existe");
                                    }
                                } else {
                                    Log.w(TAG, "Error al obtener el documento", task.getException());
                                }
                            }
                        });

                db.collection("users")
                        .whereEqualTo("name", usuario)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    // Si la consulta se realizó con éxito
                                    if (!task.getResult().isEmpty()) {
                                        // Obtener el id del primer documento que encontró
                                        String userId = task.getResult().getDocuments().get(0).getId();

                                        db.collection("users").document(userId)
                                                .update("tickets", FieldValue.arrayRemove(idTicket))
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d(TAG, "ID de ticket eliminado con éxito del usuario");
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w(TAG, "Error al eliminar ID de ticket del usuario", e);
                                                    }
                                                });

                                    }
                                }
                            }
                        });

                Toast.makeText(getApplicationContext(), "Ha cancelado su ticket con éxito", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), PantallaPrincipal.class);
                startActivity(intent);


            }
        });


    }
}