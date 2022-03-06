package com.example.whac_a_mole;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    protected Button btJugar, btJugar2;

    protected ImageView ivInfoPasaportes, ivInfoFacturacion;
    protected Spinner spDifPasaportes, spDifFacturacion;
    protected String[] nivelesDificultad={"Facil","Intermedio","Difícil"};


    public AlertDialog MensajeInfo(int modo){

        String mensaje,titulo;

        if(modo==1){
            titulo="Pasaportes";
            mensaje="En este modo deberás visar correctamente los pasaportes. Hay 2 pasaportes validos y uno no válido. Para aceptar o rechazar los pasaportes puedes cambiar la tinta del sello entre rojo o verde";
        }else{
            titulo="Facturación";
            mensaje="explicación facturación";
        }

            AlertDialog.Builder mi_builder = new AlertDialog.Builder
                    (MainActivity.this);
            mi_builder.setTitle(titulo)
                    .setMessage(mensaje)
                            .setPositiveButton("ACEPTAR",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick (DialogInterface dialog,
                                                             int which)
                                        {
                                            //no se hace nada
                                        }
                                    });
            return mi_builder.create();

        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();

        btJugar = findViewById(R.id.btJugar);
        btJugar2 = findViewById(R.id.btJugar2);
        ivInfoFacturacion = findViewById(R.id.ivInfoFacturacion);
        ivInfoPasaportes = findViewById(R.id.ivInfoPasaportes);
        spDifFacturacion = findViewById(R.id.spDificultadcheckin);
        spDifPasaportes = findViewById(R.id.spDificultadPasaportes);


        MediaPlayer mpMusica= MediaPlayer.create (MainActivity.this, R.raw.menuinicio);
        mpMusica.setVolume(0.75f, 0.75f);
        mpMusica.setLooping(true);
        mpMusica.start();

        //adaptador spinner//
        ArrayAdapter mi_adaptador = new ArrayAdapter(this, android.R.layout.simple_spinner_item,nivelesDificultad);
        //asociamos adaptador
        spDifPasaportes.setAdapter(mi_adaptador);
        spDifFacturacion.setAdapter(mi_adaptador);




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

        btJugar2.setOnClickListener(new View.OnClickListener() {
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

                Intent mi_intent = new Intent(view.getContext(), JuegoCheckIn.class);
                startActivity(mi_intent);



                mpMegafonia.stop();
                mpMegafonia.release();
            }
        });

        ivInfoPasaportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialogoPas = MensajeInfo(1);
                dialogoPas.show();
            }
        });

        ivInfoFacturacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialogoCheck = MensajeInfo(2);
                dialogoCheck.show();

            }
        });

    }
}