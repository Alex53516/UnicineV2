package com.example.unicine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class PagoConTarjeta extends AppCompatActivity {

    double precioEntrada = 6.80;
    TextView operacion, resultadoOperacion, fecha, pagar;
    EditText nom, codigo, numerTar;

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
        fecha = (TextView) findViewById(R.id.textViewFechaTarjeta);
        pagar = (TextView) findViewById(R.id.textViewRealizarPago);
        nom = (EditText) findViewById(R.id.my_edittextNombreTarjeta);
        codigo = (EditText) findViewById(R.id.my_edittextCodigoTarjeta);
        numerTar = (EditText) findViewById(R.id.my_editText_numeroTarjeta);

        Log.d("Datos", "idCine = " + idCine + "idSesion = " + idSesione + "nombreSala = " + nombreSala + "NumeroAsientos = " + numeroButacas.length);

        operacion.setText(precioEntrada + " X " + numeroBu + " = ");
        resultadoOperacion.setText(resultString + "€");

        pagar.setText("\n" + "Pagar " + resultString + "€");

        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(PagoConTarjeta.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        fecha.setText(selectedDate);
                    }
                }, year, month, dayOfMonth);

                datePickerDialog.show();
            }
        });


        pagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean avanzar = true;

                String numerTarString = numerTar.getText().toString();


                if (numerTarString.length() == 16){

                    avanzar = true;

                }else{

                    Toast.makeText(getApplicationContext(), "El numero de la tarjeta no es válido", Toast.LENGTH_SHORT).show();
                    avanzar = false;

                }

                if (codigo.getText().length() == 3){

                    avanzar = true;

                }else{

                    Toast.makeText(getApplicationContext(), "El codigo de seguridad de la tarjeta no es válido", Toast.LENGTH_SHORT).show();

                    avanzar = false;

                }

                if (nom.getText().length() < 1){

                    Toast.makeText(getApplicationContext(), "No puedes dejar el nombre vacio", Toast.LENGTH_SHORT).show();

                    avanzar = false;

                }

                if (fecha.getText().equals("")){

                    Toast.makeText(getApplicationContext(), "No puedes dejar la fecha vacia", Toast.LENGTH_SHORT).show();

                    avanzar = false;

                }


                if (avanzar == true){




                }



            }
        });
    }


}