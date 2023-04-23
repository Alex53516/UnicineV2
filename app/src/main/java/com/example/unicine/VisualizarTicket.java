package com.example.unicine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;


public class VisualizarTicket extends AppCompatActivity {

    TextView cin, sal,fech, hor, asient, usuari, pelicu;

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

        String cine = getIntent().getStringExtra("cine");
        String sala = getIntent().getStringExtra("sala");
        String fecha = getIntent().getStringExtra("fecha");
        String hora = getIntent().getStringExtra("hora");
        String asientos = getIntent().getStringExtra("asientos");
        String usuario = getIntent().getStringExtra("usuario");
        String pelicula = getIntent().getStringExtra("pelicula");

        cin.setText(cine);
        sal.setText(sala);
        fech.setText(fecha);
        hor.setText(hora);
        asient.setText(asientos);
        usuari.setText(usuario);
        pelicu.setText(pelicula);

        String text = cine + "\n" +
                sala + "\n" +
                fecha + "\n" +
                hora + "\n" +
                asientos + "\n" +
                usuario + "\n";


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

    }
}