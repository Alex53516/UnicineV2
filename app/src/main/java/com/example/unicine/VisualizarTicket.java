package com.example.unicine;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;


public class VisualizarTicket extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_ticket);


        ImageView imageView = findViewById(R.id.qr_code);

        String text = "Hello, World!"; // Texto para codificar en el QR
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