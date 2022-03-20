package com.example.whac_a_mole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Records extends AppCompatActivity {

    protected ImageView ivSalirRecords;
    protected TextView[] tvPasaportesNombres = new TextView[3];
    protected TextView[] tvCheckInNombres = new TextView[3];
    protected TextView[] tvPasaportesPuntuacion = new TextView[3];
    protected TextView[] tvCheckInPuntuacion = new TextView[3];
    protected TextView[] tvPasaportesCombo = new TextView[3];
    protected TextView[] tvCheckInClicks = new TextView[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
        getWindow().setNavigationBarColor(Color.rgb(44,120,138));
        getWindow().setStatusBarColor(Color.rgb(44,120,138));

        ivSalirRecords=findViewById(R.id.ivSalirRecords);

        tvPasaportesNombres[0]=findViewById(R.id.tvPNombre1);
        tvPasaportesNombres[1]=findViewById(R.id.tvPNombre2);
        tvPasaportesNombres[2]=findViewById(R.id.tvPNombre3);
        tvPasaportesPuntuacion[0]=findViewById(R.id.tvPPuntuacion1);
        tvPasaportesPuntuacion[1]=findViewById(R.id.tvPPuntuacion2);
        tvPasaportesPuntuacion[2]=findViewById(R.id.tvPPuntuacion3);
        tvPasaportesCombo[0]=findViewById(R.id.tvPCombo1);
        tvPasaportesCombo[1]=findViewById(R.id.tvPCombo2);
        tvPasaportesCombo[2]=findViewById(R.id.tvPCombo3);
        tvCheckInNombres[0]=findViewById(R.id.tvFNombre1);
        tvCheckInNombres[1]=findViewById(R.id.tvFNombre2);
        tvCheckInNombres[2]=findViewById(R.id.tvFNombre3);
        tvCheckInPuntuacion[0]=findViewById(R.id.tvFPuntuacion1);
        tvCheckInPuntuacion[1]=findViewById(R.id.tvFPuntuacion2);
        tvCheckInPuntuacion[2]=findViewById(R.id.tvFPuntuacion3);
        tvCheckInClicks[0]=findViewById(R.id.tvFCombo1);
        tvCheckInClicks[1]=findViewById(R.id.tvFCombo2);
        tvCheckInClicks[2]=findViewById(R.id.tvFCombo3);

        //Asociamos valores a
        SharedPreferences records = getSharedPreferences("RECORDS",MODE_PRIVATE);

        tvPasaportesNombres[0].setText(records.getString("NOMBREPAS1"," "));
        tvPasaportesNombres[1].setText(records.getString("NOMBREPAS2"," "));
        tvPasaportesNombres[2].setText(records.getString("NOMBREPAS3"," "));
        tvCheckInNombres[0].setText(records.getString("NOMBRE1"," "));
        tvCheckInNombres[1].setText(records.getString("NOMBRE2"," "));
        tvCheckInNombres[2].setText(records.getString("NOMBRE3"," "));

        if(records.getInt("PUNTUACIONPAS1",-1)!=-1) {
            tvPasaportesPuntuacion[0].setText("Puntuación: "+Integer.toString(records.getInt("PUNTUACIONPAS1", -1)));
        }
        if(records.getInt("PUNTUACIONPAS2",-1)!=-1) {
            tvPasaportesPuntuacion[1].setText("Puntuación: "+Integer.toString(records.getInt("PUNTUACIONPAS2", -1)));
        }
        if(records.getInt("PUNTUACIONPAS3",-1)!=-1) {
            tvPasaportesPuntuacion[2].setText("Puntuación: "+Integer.toString(records.getInt("PUNTUACIONPAS3", -1)));
        }

        if(records.getInt("COMBOPAS1",-1)!=-1) {
            tvPasaportesCombo[0].setText("Combo: "+Integer.toString(records.getInt("COMBOPAS1", -1)));
        }
        if(records.getInt("COMBOPAS2",-1)!=-1) {
            tvPasaportesCombo[1].setText("Combo: "+Integer.toString(records.getInt("COMBOPAS2", -1)));
        }
        if(records.getInt("COMBOPAS3",-1)!=-1) {
            tvPasaportesCombo[2].setText("Combo: "+Integer.toString(records.getInt("COMBOPAS3", -1)));
        }

        if(records.getLong("TIEMP1",-1)!=-1) {
            tvCheckInPuntuacion[0].setText("Tiempo: "+String.valueOf(records.getLong("TIEMP1",-1)));
        }
        if(records.getLong("TIEMP2",-1)!=-1) {
            tvCheckInPuntuacion[1].setText("Tiempo: "+String.valueOf(records.getLong("TIEMP2",-1)));
        }
        if(records.getLong("TIEMP3",-1)!=-1) {
            tvCheckInPuntuacion[2].setText("Tiempo: "+String.valueOf(records.getLong("TIEMP3",-1)));
        }

        if(records.getInt("NUMC1",-1)!=-1) {
            tvCheckInClicks[0].setText("Num. Clicks: "+Integer.toString(records.getInt("NUMC1",-1)));
        }
        if(records.getInt("NUMC2",-1)!=-1) {
            tvCheckInClicks[1].setText("Num. Clicks: "+Integer.toString(records.getInt("NUMC2",-1)));
        }
        if(records.getInt("NUMC3",-1)!=-1) {
            tvCheckInClicks[2].setText("Num. Clicks: "+Integer.toString(records.getInt("NUMC3",-1)));
        }

        ivSalirRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}