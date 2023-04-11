package com.example.unicine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.core.content.ContextCompat;

public class Splash extends Activity {
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    @Override public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        int color = ContextCompat.getColor(this, R.color.azul);

        getWindow().setNavigationBarColor(color);

        setContentView(R.layout.splash_layout);
        new Handler().postDelayed(new Runnable(){
            @Override public void run() {
                Intent mainIntent = new
                        Intent(Splash.this, Login.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
