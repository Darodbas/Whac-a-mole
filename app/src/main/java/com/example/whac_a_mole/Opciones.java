package com.example.whac_a_mole;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

public class Opciones extends AppCompatActivity {

    protected ImageView ivSalir, ivBorrarRecords;
    protected Switch swMusica, swEfectos;
    protected EditText etTiempoPasaportes;
    protected Button btAplicar;

    public AlertDialog MensajeBorrar(){

        AlertDialog.Builder mi_builder = new AlertDialog.Builder
                (Opciones.this);
        mi_builder.setTitle("Atención")
                .setMessage("Esta seguro de que quiere borrar todos los récords")
                .setPositiveButton("Si",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick (DialogInterface dialog,
                                                 int which)
                            {
                                //borra los records
                                SharedPreferences records = getSharedPreferences("RECORDS",MODE_PRIVATE);
                                SharedPreferences.Editor editor = records.edit();

                                editor.putString("NOMBREPAS1","");
                                editor.putString("NOMBREPAS2","");
                                editor.putString("NOMBREPAS3","");
                                editor.putString("NOMBRE1","");
                                editor.putString("NOMBRE2","");
                                editor.putString("NOMBRE3","");
                                editor.putInt("PUNTUACIONPAS1",-1);
                                editor.putInt("PUNTUACIONPAS2",-1);
                                editor.putInt("PUNTUACIONPAS3",-1);
                                editor.putLong("TIEMP1",-1);
                                editor.putLong("TIEMP2",-1);
                                editor.putLong("TIEMP3",-1);
                                editor.putInt("COMBOPAS1",-1);
                                editor.putInt("COMBOPAS2",-1);
                                editor.putInt("COMBOPAS3",-1);
                                editor.putInt("NUMC1",-1);
                                editor.putInt("NUMC2",-1);
                                editor.putInt("NUMC3",-1);

                                editor.commit();

                            }
                        });
        mi_builder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick (DialogInterface dialog,
                                         int which)
                    {
                        //no hace nada
                    }
                });
        return mi_builder.create();

    }

    public AlertDialog MensajeInfo(){
        //mostramos dialogo
        AlertDialog.Builder mi_builder = new AlertDialog.Builder
                (Opciones.this);
        mi_builder.setTitle("Atención")
                .setMessage("Únicamente se podrán guardar récords jugando con 60 segundos")
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
        setContentView(R.layout.activity_opciones);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();

        ivSalir=findViewById(R.id.ivSalir);
        ivBorrarRecords=findViewById(R.id.ivBorrarRecords);
        swMusica=findViewById(R.id.swMusica);
        swEfectos=findViewById(R.id.swEfectos);
        etTiempoPasaportes=findViewById(R.id.etTiempoPasaportes);
        btAplicar=findViewById(R.id.btAplicar);



        //ponemos los valores guardados en los controles
        SharedPreferences preferencias = getSharedPreferences("PREFERENCIAS",MODE_PRIVATE);

        etTiempoPasaportes.setText(Long.toString(preferencias.getLong("TIEMPOPASAPORTES",60)));
        swMusica.setChecked(preferencias.getBoolean("MUSICA",true));
        swEfectos.setChecked(preferencias.getBoolean("EFECTOS",true));




        ivBorrarRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialogoPas = MensajeBorrar();
                dialogoPas.show();

            }
        });

        swMusica.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences preferencias = getSharedPreferences("PREFERENCIAS",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();
                if(swMusica.isChecked()){
                    editor.putBoolean("MUSICA",true);
                }else{
                    editor.putBoolean("MUSICA",false);
                }
                editor.commit();
            }
        });

        swEfectos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences preferencias = getSharedPreferences("PREFERENCIAS",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();
                if(swEfectos.isChecked()){
                    editor.putBoolean("EFECTOS",true);
                }else{
                    editor.putBoolean("EFECTOS",false);
                }
                editor.commit();
            }
        });

        btAplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferencias = getSharedPreferences("PREFERENCIAS",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();

                if(etTiempoPasaportes.getText().length()>0){
                    editor.putLong("TIEMPOPASAPORTES",Long.parseLong(etTiempoPasaportes.getText().toString()));

                    if(Long.parseLong(etTiempoPasaportes.getText().toString())!=60){
                        AlertDialog dialogoPas = MensajeInfo();
                        dialogoPas.show();
                    }
                    editor.commit();
                }

            }
        });

        ivSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });


    }
}