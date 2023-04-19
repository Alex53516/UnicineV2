package com.example.unicine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class PagoConTarjeta extends AppCompatActivity {

    double precioEntrada = 6.80;
    TextView operacion, resultadoOperacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago_con_tarjeta);

        String idCine = getIntent().getStringExtra("idCine");
        String idSesione = getIntent().getStringExtra("idSesione");
        String nombreSala = getIntent().getStringExtra("nombreSala");
        String[] numeroButacas = getIntent().getStringArrayExtra("numeroButacas");
        int numeroBu = numeroButacas.length;

        int color = ContextCompat.getColor(this, R.color.azul);

        getWindow().setNavigationBarColor(color);
        getWindow().setStatusBarColor(color);

        double result = (numeroBu * precioEntrada);
        String resultString = String.valueOf(result);

        operacion = (TextView) findViewById(R.id.textViewOperacion);
        resultadoOperacion = (TextView) findViewById(R.id.textViewOperacionResul);

        Log.d("Datos", "idCine = " + idCine + "idSesion = " + idSesione + "nombreSala = " + nombreSala + "NumeroAsientos = " + numeroButacas.length);

        operacion.setText(precioEntrada + " X " + numeroBu + " = ");
        resultadoOperacion.setText(resultString);


    }
}