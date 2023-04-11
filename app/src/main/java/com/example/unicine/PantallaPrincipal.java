package com.example.unicine;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class PantallaPrincipal extends AppCompatActivity {

    BottomNavigationView bottom;
    FrameLayout frm;
    BottomNavigationItemView FirstItem, SecondItem, ThirdItem;
    TextView textView, textView2, textView3;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

        int color = ContextCompat.getColor(this, R.color.rojo);

        getWindow().setNavigationBarColor(color);

        bottom = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

        //Esto es para hacer mas grande las letras al pinchar sobre ellas

        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottom.getChildAt(0);

        FirstItem = (BottomNavigationItemView) menuView.getChildAt(0);
        textView = (TextView) FirstItem.findViewById(com.google.android.material.R.id.navigation_bar_item_large_label_view);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

        SecondItem = (BottomNavigationItemView) menuView.getChildAt(1);
        textView2 = (TextView) SecondItem.findViewById(com.google.android.material.R.id.navigation_bar_item_large_label_view);
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

        ThirdItem = (BottomNavigationItemView) menuView.getChildAt(2);
        textView3 = (TextView) ThirdItem.findViewById(com.google.android.material.R.id.navigation_bar_item_large_label_view);
        textView3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

        String user = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        SecondItem.setTitle(user);

        SecondItem.requestFocus();







        bottom.setOnNavigationItemSelectedListener(mOnNavigationSelectedListener);


        frm = (FrameLayout) findViewById(R.id.framePrincipal);

        ComprarFragment fragmentComprar2 = new ComprarFragment();
        FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
        fragmentTransaction1.add(R.id.framePrincipal, fragmentComprar2);
        fragmentTransaction1.commit();


    }


   private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
       @Override
       public boolean onNavigationItemSelected(@NonNull MenuItem item) {

           String user = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

           ComprarFragment fragmentComprar = new ComprarFragment();
           UsuarioFragment fragmentUsuario = new UsuarioFragment();
           MasFragment fragmentMas = new MasFragment();


           item.setChecked(true);


           FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();


           switch(item.getItemId()){


               case R.id.navigation_comprar:


                   fragmentTransaction.replace(R.id.framePrincipal, fragmentComprar);
                   fragmentTransaction.commit();

                   break;

               case R.id.navigation_usuario:


                   fragmentTransaction.replace(R.id.framePrincipal, fragmentUsuario);
                   item.setTitle(user); // Aquí se actualiza el título del segundo elemento
                   fragmentTransaction.commit();

                   break;


               case R.id.navigation_mas:


                   fragmentTransaction.replace(R.id.framePrincipal, fragmentMas);
                   fragmentTransaction.commit();


                   break;
           }

           return false;
       }
   };





}