package com.example.whac_a_mole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    protected Button btJugar;
    //PUTIN NO TIENE RAZÓN

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();

        btJugar = findViewById(R.id.btJugar);
        MediaPlayer mpMusica= MediaPlayer.create (MainActivity.this, R.raw.menuinicio);
        mpMusica.setVolume(0.75f, 0.75f);
        mpMusica.setLooping(true);
        mpMusica.start();




        btJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mpMusica.isPlaying()) {
                    mpMusica.stop();
                    mpMusica.release();
                }

                MediaPlayer mpMegafonia= MediaPlayer.create (MainActivity.this, R.raw.megafonia);
                mpMegafonia.setVolume(0.75f, 0.75f);
                mpMegafonia.start();

                while(mpMegafonia.isPlaying()){
                //Se puede poner una animación
                }

                    Intent mi_intent = new Intent(view.getContext(), JuegoPasaportes.class);
                    startActivity(mi_intent);



                mpMegafonia.stop();
                mpMegafonia.release();

            }
        });
    }
}