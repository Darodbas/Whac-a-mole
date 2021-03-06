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
import android.os.CountDownTimer;
import android.preference.EditTextPreference;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    protected Button btJugar, btJugar2;
    protected TextView tvColorDificultad;

    protected ImageView ivInfoPasaportes, ivInfoFacturacion, ivOpciones, ivPodio,ivSalirPrincipal, ivSpeaker;
    protected Spinner spDifPasaportes;
    protected String[] nivelesDificultad={"Fácil","Medio","Difícil"};
    protected EditText etNombre;
    protected int aceptaSinNombre=0,musicaSonando=1;
    protected boolean btpas,btfac;
    protected float volumeM= (float) 0.05;
    protected float volumeMeg= (float) 0.02;
    protected long tiempoEsperaMegafonia=1900;


    //Se muestra un mensaje al pulsar info
    public AlertDialog MensajeInfo(int modo){

        String mensaje,titulo;

        if(modo==1){
            titulo="Pasaportes";
            mensaje="En este modo deberás visar correctamente los pasaportes. Hay 2 pasaportes válidos y uno no válido. Para aceptar o rechazar los pasaportes puedes cambiar la tinta del sello entre rojo y verde";
        }else{
            titulo="Facturación";
            mensaje="En este modo deberás facturar correctamente las maletas. Hay 4 tipos de maletas: la verde (Hay que darle 1 click), la amarilla (Hay que darle 3 clicks), la roja (Hay que darle 5 clicks), y la negra (Hay " +
                    "que darle 10 clicks). Además por cada maleta no facturada perderás una vida, pero facturar una maleta negra te devuelve 1 vida.";
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

    public AlertDialog MensajeAlertaNombre(int modo){

        AlertDialog.Builder mi_builder = new AlertDialog.Builder
                (MainActivity.this);
        mi_builder.setTitle("Atención")
                .setMessage("Si deja en blanco el campo nombre aparecerá en los récords como \"Jugador\"")
                .setPositiveButton("Entendido",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick (DialogInterface dialog,
                                                 int which)
                            {
                                aceptaSinNombre=1;
                                CountDownTimer espera = new CountDownTimer(500,100) {
                                    @Override
                                    public void onTick(long l) {

                                    }

                                    @Override
                                    public void onFinish() {
                                        if(modo==1){
                                            btJugar.performClick();
                                        }else if(modo==2){
                                            btJugar2.performClick();
                                        }

                                    }
                                } .start();

                            }
                        });
       mi_builder.setNegativeButton("Volver",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialog,
                                         int which)
                    {
                        aceptaSinNombre=0;
                        etNombre.requestFocus();
                    }
                });
        return mi_builder.create();

    }

    public void onBackPressed(){

        ivSalirPrincipal.performClick();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(Color.rgb(255,255,255));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  text status negro
        //getWindow().setNavigationBarColor(Color.rgb(255,255,255));


        btJugar = findViewById(R.id.btJugar);
        btJugar2 = findViewById(R.id.btJugar2);
        ivInfoFacturacion = findViewById(R.id.ivInfoFacturacion);
        ivInfoPasaportes = findViewById(R.id.ivInfoPasaportes);
        spDifPasaportes = findViewById(R.id.spDificultadPasaportes);
        ivOpciones = findViewById(R.id.ivConfig);
        ivPodio = findViewById(R.id.ivPodio);
        etNombre = findViewById(R.id.etNombre);
        ivSalirPrincipal = findViewById(R.id.ivSalirPrincipal);
        tvColorDificultad=findViewById(R.id.tvColorDificultad);
        ivSpeaker=findViewById(R.id.ivSpeaker);

        btpas=false;
        btfac=false;

        //ponemos el Nombre anterior si hay
        SharedPreferences records = getSharedPreferences("RECORDS", MODE_PRIVATE);
        SharedPreferences.Editor editor = records.edit();
        etNombre.setText(records.getString("NOMBRE",""));

        SharedPreferences preferencias = getSharedPreferences("PREFERENCIAS", MODE_PRIVATE);




        MediaPlayer mpMusica= MediaPlayer.create (MainActivity.this, R.raw.menuinicio);
        mpMusica.setVolume(volumeM, volumeM);
        mpMusica.setLooping(true);
        mpMusica.start();

        //adaptador spinner//
        ArrayAdapter mi_adaptador = new ArrayAdapter(this, android.R.layout.simple_spinner_item,nivelesDificultad);
        //asociamos adaptador
        spDifPasaportes.setAdapter(mi_adaptador);


        btJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!btpas){

                if (etNombre.getText().length() > 0) {

                    editor.putString("NOMBRE", String.valueOf(etNombre.getText()));
                    aceptaSinNombre = 1;
                    editor.commit();

                } else {

                    if (aceptaSinNombre == 0) {
                        AlertDialog dialogoPas = MensajeAlertaNombre(1);
                        dialogoPas.show();
                        editor.putString("NOMBRE", "Jugador");
                        editor.commit();
                    }
                }

                if (aceptaSinNombre == 1) {


                    if (mpMusica.isPlaying()) {
                        mpMusica.stop();
                        mpMusica.release();
                    }

                    //Solo si en preferencias esta habilitado
                    if (preferencias.getBoolean("EFECTOS", true)) {

                        MediaPlayer mpMegafonia = MediaPlayer.create(MainActivity.this, R.raw.megafonia);
                        mpMegafonia.setVolume(volumeMeg, volumeMeg);
                        mpMegafonia.start();
                        btpas=true;

                        CountDownTimer esperaMegafono = new CountDownTimer(tiempoEsperaMegafonia, 1000) {
                            @Override
                            public void onTick(long l) {

                            }

                            @Override
                            public void onFinish() {
                                mpMegafonia.stop();
                                mpMegafonia.release();
                                Intent mi_intent = new Intent(view.getContext(), JuegoPasaportes.class);
                                startActivity(mi_intent);
                            }
                        }.start();


                    } else {
                        Intent mi_intent = new Intent(view.getContext(), JuegoPasaportes.class);
                        startActivity(mi_intent);
                    }


                }
                }
            }

        });

        btJugar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!btfac) {

                    //pasamos nombre
                    SharedPreferences records = getSharedPreferences("RECORDS", MODE_PRIVATE);
                    SharedPreferences preferencias = getSharedPreferences("PREFERENCIAS", MODE_PRIVATE);
                    SharedPreferences.Editor editor = records.edit();

                    if (etNombre.getText().length() > 0) {

                        editor.putString("NOMBRE", String.valueOf(etNombre.getText()));
                        aceptaSinNombre = 1;
                        editor.commit();

                    } else {

                        if (aceptaSinNombre == 0) {
                            AlertDialog dialogoPas = MensajeAlertaNombre(2);
                            dialogoPas.show();
                            editor.putString("NOMBRE", "Jugador");
                            editor.commit();
                        }

                    }

                    if (aceptaSinNombre == 1) {


                        if (mpMusica.isPlaying()) {
                            mpMusica.stop();
                            mpMusica.release();
                        }

                        //Solo si en preferencias esta habilitado
                        if (preferencias.getBoolean("EFECTOS", true)) {


                            MediaPlayer mpMegafonia = MediaPlayer.create(MainActivity.this, R.raw.megafonia);
                            mpMegafonia.setVolume(volumeMeg, volumeMeg);
                            mpMegafonia.start();
                            btfac=true;

                            CountDownTimer esperaMegafono = new CountDownTimer(tiempoEsperaMegafonia, 1000) {
                                @Override
                                public void onTick(long l) {

                                }

                                @Override
                                public void onFinish() {
                                    mpMegafonia.stop();
                                    mpMegafonia.release();
                                    Intent mi_intent = new Intent(view.getContext(), JuegoCheckIn.class);
                                    startActivity(mi_intent);
                                }
                            }.start();


                        } else {
                            Intent mi_intent = new Intent(view.getContext(), JuegoCheckIn.class);
                            startActivity(mi_intent);
                        }


                    }
                }
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
                    tvColorDificultad.setBackgroundColor(Color.argb(70,35,182,166));
                }else if(i==1){
                    tvColorDificultad.setBackgroundColor(Color.argb(70,255,241,0));
                }else if(i==2){
                    tvColorDificultad.setBackgroundColor(Color.argb(70,237,81,81));
                }
                SharedPreferences.Editor editor = opcionesPasaporte.edit();
                editor.putInt("DIFICULTAD",i);
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ivSalirPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    mpMusica.stop();
                    mpMusica.release();

                finish();

            }
        });

        ivSpeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(musicaSonando==1){
                    mpMusica.pause();
                    ivSpeaker.setImageResource(R.drawable.speakeroff);
                    musicaSonando=0;
                }else{
                    mpMusica.start();
                    ivSpeaker.setImageResource(R.drawable.speakeron);
                    musicaSonando=1;
                }

            }
        });
    }
}