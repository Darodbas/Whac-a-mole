package com.example.whac_a_mole;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    protected Button btJugar, btJugar2;

    protected ImageView ivInfoPasaportes, ivInfoFacturacion, ivOpciones, ivPodio;
    protected Spinner spDifPasaportes, spDifFacturacion;
    protected String[] nivelesDificultad={"Fácil","Intermedio","Difícil"};


    //Se muestra un mensaje al pulsar info
    public AlertDialog MensajeInfo(int modo){

        String mensaje,titulo;

        if(modo==1){
            titulo="Pasaportes";
            mensaje="En este modo deberás visar correctamente los pasaportes. Hay 2 pasaportes válidos y uno no válido. Para aceptar o rechazar los pasaportes puedes cambiar la tinta del sello entre rojo y verde";
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
        ivOpciones = findViewById(R.id.ivConfig);
        ivPodio=findViewById(R.id.ivPodio);



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

        ivOpciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mi_intent = new Intent(view.getContext(), Opciones.class);
                startActivity(mi_intent);

            }
        });

        ivPodio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mi_intent = new Intent(view.getContext(), Records.class);
                startActivity(mi_intent);
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

        spDifPasaportes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                SharedPreferences opcionesPasaporte = getSharedPreferences("OPCIONESPASAPORTES",MODE_PRIVATE);


                //La dificultad va de 0(facil) a 2(dificil)
                if(i==0){
                    btJugar.setBackgroundColor(Color.rgb(45,126,110));
                }else if(i==1){
                    btJugar.setBackgroundColor(Color.rgb(200,150,50));
                }else if(i==2){
                    btJugar.setBackgroundColor(Color.rgb(200,50,50));
                }
                SharedPreferences.Editor editor = opcionesPasaporte.edit();
                editor.putInt("DIFICULTAD",i);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spDifFacturacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SharedPreferences opcionesFacturacion = getSharedPreferences("OPCIONESFacturacion",MODE_PRIVATE);

                if(i==0){
                    btJugar2.setBackgroundColor(Color.rgb(45,126,110));
                }else if(i==1){
                    btJugar2.setBackgroundColor(Color.rgb(200,150,50));
                }else if(i==2){
                    btJugar2.setBackgroundColor(Color.rgb(200,50,50));
                }
                SharedPreferences.Editor editor = opcionesFacturacion.edit();
                editor.putInt("DIFICULTAD",i);
                editor.commit();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}