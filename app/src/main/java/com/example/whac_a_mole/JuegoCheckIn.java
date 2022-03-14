package com.example.whac_a_mole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class JuegoCheckIn extends AppCompatActivity {

    protected ImageView[] mal = new ImageView[9];
    protected ImageView cor1, cor2, cor3;
    protected TextView tiempo, gameOver, tiempoFinal;
    protected int[] typeMal = new int[9];
    protected int[] contClick = new int[9];
    protected int i, contCor = 3, ranNum, idAcierto, idFallo, idInicio, idFin;
    protected long[] tempMal = new long[9];
    protected static final long tCDTG = 10000, interval = 1000, interval2 = 200, interval3 = 100;
    protected long temp, tempBien = 500, tempNueva = 1500, tVerd = 2000, tAmar = 3000, tRoja = 4000, tNegra = 5000, tiemp1, tiemp2, tiemp3;
    protected CountDownTimer[] CDTmal = new CountDownTimer[9];
    protected CountDownTimer[] CDTbien = new CountDownTimer[9];
    protected CountDownTimer CDTG1, CDTG2, CDTNueva;
    protected Button volver;
    protected SoundPool sp;
    protected float volumeEf, volumeM;
    protected String nombre, nombre1,nombre2,nombre3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_check_in);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mal[0] = findViewById(R.id.mal1);
        mal[1] = findViewById(R.id.mal2);
        mal[2] = findViewById(R.id.mal3);
        mal[3] = findViewById(R.id.mal4);
        mal[4] = findViewById(R.id.mal5);
        mal[5] = findViewById(R.id.mal6);
        mal[6] = findViewById(R.id.mal7);
        mal[7] = findViewById(R.id.mal8);
        mal[8] = findViewById(R.id.mal9);
        cor1 = findViewById(R.id.cor1);
        cor2 = findViewById(R.id.cor2);
        cor3 = findViewById(R.id.cor3);
        tiempo = findViewById(R.id.tiempo);
        gameOver = findViewById(R.id.gameOver);
        tiempoFinal = findViewById(R.id.tiempoFinal);
        volver = findViewById(R.id.volver);

        SharedPreferences preferencias = getSharedPreferences("PREFERENCIAS",MODE_PRIVATE);

        temp = 0;
        tiempo.setText("0");
        iniMal();
        iniContClick();

        startTiempoG1();
        ranNum = (int) (Math.random() * 9);
        setMal(ranNum);

        if(preferencias.getBoolean("EFECTOS",true)){
            volumeEf = 1;
        }
        else{
            volumeEf = 0.0F;
        }

        if(preferencias.getBoolean("MUSICA", true)){
            volumeM = 0.05f;
        }
        else{
            volumeM = 0.0F;
        }

        MediaPlayer mp = MediaPlayer.create(JuegoCheckIn.this, R.raw.musicajuegos);
        mp.setVolume(volumeM, volumeM);
        mp.setLooping(true);
        mp.start();

        sp = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        idAcierto = sp.load(this, R.raw.acierto, 1);
        idFallo = sp.load(this, R.raw.fallo, 1);
        idInicio = sp.load(this, R.raw.megafonia, 1);
        idFin = sp.load(this, R.raw.megafoniasalir, 1);

        sp.play(idInicio, volumeEf, volumeEf, 1, 0, 1);

        mal[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(typeMal[0]==0 || typeMal[0]==5);
                else{
                    isClicked(0);
                    if(typeMal[0]==5){
                        pauseTiempo1();
                        startBien1(0);
                    }
                }
            }
        });

        mal[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(typeMal[1]==0 || typeMal[1]==5);
                else{
                    isClicked(1);
                    if(typeMal[1]==5){
                        pauseTiempo2();
                        startBien2(1);
                    }
                }
            }
        });

        mal[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(typeMal[2]==0 || typeMal[2]==5);
                else{
                    isClicked(2);
                    if(typeMal[2]==5){
                        pauseTiempo3();
                        startBien3(2);
                    }
                }
            }
        });

        mal[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(typeMal[3]==0 || typeMal[3]==5);
                else{
                    isClicked(3);
                    if(typeMal[3]==5){
                        pauseTiempo4();
                        startBien4(3);
                    }
                }
            }
        });

        mal[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(typeMal[4]==0 || typeMal[4]==5);
                else{
                    isClicked(4);
                    if(typeMal[4]==5){
                        pauseTiempo5();
                        startBien5(4);
                    }
                }
            }
        });

        mal[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(typeMal[5]==0 || typeMal[5]==5);
                else{
                    isClicked(5);
                    if(typeMal[5]==5){
                        pauseTiempo6();
                        startBien6(5);
                    }
                }
            }
        });

        mal[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(typeMal[6]==0 || typeMal[6]==5);
                else{
                    isClicked(6);
                    if(typeMal[6]==5){
                        pauseTiempo7();
                        startBien7(6);
                    }
                }
            }
        });

        mal[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(typeMal[7]==0 || typeMal[7]==5);
                else{
                    isClicked(7);
                    if(typeMal[7]==5){
                        pauseTiempo8();
                        startBien8(7);
                    }
                }
            }
        });

        mal[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(typeMal[8]==0 || typeMal[8]==5);
                else{
                    isClicked(8);
                    if(typeMal[8]==5){
                        pauseTiempo9();
                        startBien9(8);
                    }
                }
            }
        });

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    protected void startTiempoG1(){
        CDTG1 = new CountDownTimer(tCDTG,interval) {
            @Override
            public void onTick(long l) {
                temp++;
                tiempo.setText(Long.toString(temp));
            }

            @Override
            public void onFinish() {
                if(tVerd>200) tVerd -= 200;
                if(tAmar>200) tAmar -= 200;
                if(tRoja>200) tRoja -= 200;
                if(tNegra>200)  tNegra -= 200;
                if(tempNueva>100) tempNueva -= 100;

                startTiempoG2();
            }
        }.start();
    }

    protected void pauseTiempoG1(){
        CDTG1.cancel();
    }

    protected void startTiempoG2(){
        CDTG1 = new CountDownTimer(tCDTG,interval) {
            @Override
            public void onTick(long l) {
                temp++;
                tiempo.setText(Long.toString(temp));
            }

            @Override
            public void onFinish() {
                if(tVerd>200) tVerd -= 200;
                if(tAmar>200) tAmar -= 200;
                if(tRoja>200) tRoja -= 200;
                if(tNegra>200)  tNegra -= 200;
                if(tempNueva>100) tempNueva -= 100;

                startTiempoG1();
            }
        }.start();
    }

    protected void pauseTiempoG2(){
        CDTG2.cancel();
    }

    protected void startNueva(){
        CDTNueva = new CountDownTimer(tempNueva, interval3) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                ranNum = (int)(Math.random()*9);
                while(typeMal[ranNum]!=0){
                    ranNum = (int)(Math.random()*9);
                }
                setMal(ranNum);
            }
        }.start();
    }

    protected void pauseNueva(){
        CDTNueva.cancel();
    }

    protected void startBien1(int x){
        CDTbien[x] = new CountDownTimer(tempBien, interval3) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                mal[x].setVisibility(View.INVISIBLE);
                typeMal[x] = 0;
            }
        }.start();
    }

    protected void startBien2(int x){
        CDTbien[x] = new CountDownTimer(tempBien, interval3) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                mal[x].setVisibility(View.INVISIBLE);
                typeMal[x] = 0;
            }
        }.start();
    }

    protected void startBien3(int x){
        CDTbien[x] = new CountDownTimer(tempBien, interval3) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                mal[x].setVisibility(View.INVISIBLE);
                typeMal[x] = 0;
            }
        }.start();
    }

    protected void startBien4(int x){
        CDTbien[x] = new CountDownTimer(tempBien, interval3) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                mal[x].setVisibility(View.INVISIBLE);
                typeMal[x] = 0;
            }
        }.start();
    }

    protected void startBien5(int x){
        CDTbien[x] = new CountDownTimer(tempBien, interval3) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                mal[x].setVisibility(View.INVISIBLE);
                typeMal[x] = 0;
            }
        }.start();
    }

    protected void startBien6(int x){
        CDTbien[x] = new CountDownTimer(tempBien, interval3) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                mal[x].setVisibility(View.INVISIBLE);
                typeMal[x] = 0;
            }
        }.start();
    }

    protected void startBien7(int x){
        CDTbien[x] = new CountDownTimer(tempBien, interval3) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                mal[x].setVisibility(View.INVISIBLE);
                typeMal[x] = 0;
            }
        }.start();
    }

    protected void startBien8(int x){
        CDTbien[x] = new CountDownTimer(tempBien, interval3) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                mal[x].setVisibility(View.INVISIBLE);
                typeMal[x] = 0;
            }
        }.start();
    }

    protected void startBien9(int x){
        CDTbien[x] = new CountDownTimer(tempBien, interval3) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                mal[x].setVisibility(View.INVISIBLE);
                typeMal[x] = 0;
            }
        }.start();
    }

    protected void startTiempo1(){
        CDTmal[0] = new CountDownTimer(tempMal[0],interval2) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                mal[0].setVisibility(View.INVISIBLE);
                typeMal[0] = 0;
                contCor--;
                sp.play(idFallo, volumeEf, volumeEf, 1, 0, 1);
                isGameOver();
            }
        }.start();
    }

    protected void pauseTiempo1(){
        CDTmal[0].cancel();
    }

    protected void startTiempo2(){
        CDTmal[1] = new CountDownTimer(tempMal[1],interval2) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                mal[1].setVisibility(View.INVISIBLE);
                typeMal[1] = 0;
                contCor--;
                sp.play(idFallo, volumeEf, volumeEf, 1, 0, 1);
                isGameOver();
            }
        }.start();
    }

    protected void pauseTiempo2(){
        CDTmal[1].cancel();
    }

    protected void startTiempo3(){
        CDTmal[2] = new CountDownTimer(tempMal[2],interval2) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                mal[2].setVisibility(View.INVISIBLE);
                typeMal[2] = 0;
                contCor--;
                sp.play(idFallo, volumeEf, volumeEf, 1, 0, 1);
                isGameOver();
            }
        }.start();
    }

    protected void pauseTiempo3(){
        CDTmal[2].cancel();
    }

    protected void startTiempo4(){
        CDTmal[3] = new CountDownTimer(tempMal[3],interval2) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                mal[3].setVisibility(View.INVISIBLE);
                typeMal[3] = 0;
                contCor--;
                sp.play(idFallo, volumeEf, volumeEf, 1, 0, 1);
                isGameOver();
            }
        }.start();
    }

    protected void pauseTiempo4(){
        CDTmal[3].cancel();
    }

    protected void startTiempo5(){
        CDTmal[4] = new CountDownTimer(tempMal[4],interval2) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                mal[4].setVisibility(View.INVISIBLE);
                typeMal[4] = 0;
                contCor--;
                sp.play(idFallo, volumeEf, volumeEf, 1, 0, 1);
                isGameOver();
            }
        }.start();
    }

    protected void pauseTiempo5(){
        CDTmal[4].cancel();
    }

    protected void startTiempo6(){
        CDTmal[5] = new CountDownTimer(tempMal[5],interval2) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                mal[5].setVisibility(View.INVISIBLE);
                typeMal[5] = 0;
                contCor--;
                sp.play(idFallo, volumeEf, volumeEf, 1, 0, 1);
                isGameOver();
            }
        }.start();
    }

    protected void pauseTiempo6(){
        CDTmal[5].cancel();
    }

    protected void startTiempo7(){
        CDTmal[6] = new CountDownTimer(tempMal[6],interval2) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                mal[6].setVisibility(View.INVISIBLE);
                typeMal[6] = 0;
                contCor--;
                sp.play(idFallo, volumeEf, volumeEf, 1, 0, 1);
                isGameOver();
            }
        }.start();
    }

    protected void pauseTiempo7(){
        CDTmal[6].cancel();
    }

    protected void startTiempo8(){
        CDTmal[7] = new CountDownTimer(tempMal[7],interval2) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                mal[7].setVisibility(View.INVISIBLE);
                typeMal[7] = 0;
                contCor--;
                sp.play(idFallo, volumeEf, volumeEf, 1, 0, 1);
                isGameOver();
            }
        }.start();
    }

    protected void pauseTiempo8(){
        CDTmal[7].cancel();
    }

    protected void startTiempo9(){
        CDTmal[8] = new CountDownTimer(tempMal[8],interval2) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                mal[8].setVisibility(View.INVISIBLE);
                typeMal[8] = 0;
                contCor--;
                sp.play(idFallo, volumeEf, volumeEf, 1, 0, 1);
                isGameOver();
            }
        }.start();
    }

    protected void pauseTiempo9(){
        CDTmal[8].cancel();
    }

    protected void iniMal(){
        int i;
        for(i=0;i<mal.length;i++){
            mal[i].setVisibility(View.INVISIBLE);
            typeMal[i] = 0;
        }
        //typeMal 0 indica que no hay maleta
        //typeMal 1 indica que hay maleta verde
        //typeMal 2 indica que hay maleta amarilla
        //typeMal 3 indica que hay maleta roja
        //typeMal 4 indica que hay maleta negra
        //typeMal 5 indica que la maleta ha sido facturada
    }

    protected void iniContClick(){
        for(i=0;i<contClick.length;i++){
            contClick[i]=0;
        }
    }

    protected void isClicked(int x) {
        if(typeMal[x]==0);
        else if(typeMal[x]==1){
            mal[x].setImageResource(R.drawable.maleta_verde_facturada);
            sp.play(idAcierto, volumeEf, volumeEf, 1, 0, 1);
            typeMal[x] = 5;
        }
        else if(typeMal[x]==2){
            contClick[x]++;
            if(contClick[x]==5){
                mal[x].setImageResource(R.drawable.maleta_amarilla_facturada);
                sp.play(idAcierto, volumeEf, volumeEf, 1, 0, 1);
                typeMal[x] = 5;
                contClick[x] = 0;
            }
        }
        else if(typeMal[x]==3){
            contClick[x]++;
            if(contClick[x]==10){
                mal[x].setImageResource(R.drawable.maleta_roja_facturada);
                sp.play(idAcierto, volumeEf, volumeEf, 1, 0, 1);
                typeMal[x] = 5;
                contClick[x] = 0;
            }
        }
        else if(typeMal[x]==4){
            contClick[x]++;
            if(contClick[x]==20){
                mal[x].setImageResource(R.drawable.maleta_negra_facturada);
                sp.play(idAcierto, volumeEf, volumeEf, 1, 0, 1);
                typeMal[x] = 5;
                contClick[x] = 0;
                if(contCor<3){
                    contCor++;
                    isGameOver();
                }
            }
        }
        else{
            Log.e("Error","typeMal en mesa "+Integer.toString(x+1));
        }
    }

    protected void setMal(int x){
        int ran;
        ran = (int)(Math.random()*100);

        mal[x].setVisibility(View.VISIBLE);

        if(ran>=0 && ran<40){
            mal[x].setImageResource(R.drawable.maleta_verde);
            typeMal[x] = 1;
            tempMal[x] = tVerd;
        }
        else if(ran>=40 && ran<70){
            mal[x].setImageResource(R.drawable.maleta_amarilla);
            typeMal[x] = 2;
            tempMal[x] = tAmar;
        }
        else if(ran>=70 && ran<90) {
            mal[x].setImageResource(R.drawable.maleta_roja);
            typeMal[x] = 3;
            tempMal[x] = tRoja;
        }
        else if(ran>=90){
            mal[x].setImageResource(R.drawable.maleta_negra);
            typeMal[x] = 4;
            tempMal[x] = tNegra;
        }
        if(x==0){
            startTiempo1();
        }
        else if(x==1){
            startTiempo2();
        }
        else if(x==2){
            startTiempo3();
        }
        else if(x==3){
            startTiempo4();
        }
        else if(x==4){
            startTiempo5();
        }
        else if(x==5){
            startTiempo6();
        }
        else if(x==6){
            startTiempo7();
        }
        else if(x==7){
            startTiempo8();
        }
        else if(x==8){
            startTiempo9();
        }
        else{
            Log.e("Error","Mesa incorrecta en setMal (x)");
        }
        startNueva();
    }

    protected void newRecord(){
        SharedPreferences records = getSharedPreferences("RECORDS",MODE_PRIVATE);
        SharedPreferences.Editor editor = records.edit();

        nombre = records.getString("NOMBRE", "");
        nombre1 = records.getString("NOMBRECH1", "");
        nombre2 = records.getString("NOMBRECH2", "");
        nombre3 = records.getString("NOMBRECH3", "");
        tiemp1 = records.getLong("TIEMP1", 0);
        tiemp2 = records.getLong("TIEMP2", 0);
        tiemp3 = records.getLong("TIEMP3", 0);

        if(nombre1.isEmpty()){
            editor.putString("NOMBRE1",nombre);
            editor.putLong("TIEMP1",temp);
        }
        else{
            if(nombre2.isEmpty()){
                editor.putString("NOMBRE2",nombre);
                editor.putLong("TIEMP2",temp);
            }
            else{
                if(nombre3.isEmpty()){
                    editor.putString("NOMBRE3",nombre);
                    editor.putLong("TIEMP3",temp);
                }
                else{
                    if(temp>tiemp1){
                        editor.putString("NOMBRE1",nombre);
                        editor.putLong("TIEMP1",temp);
                        editor.putString("NOMBRE2",nombre1);
                        editor.putLong("TIEMP2",tiemp1);
                        editor.putString("NOMBRE3",nombre2);
                        editor.putLong("TIEMP3",tiemp2);
                    }
                    else{
                        if(temp>tiemp2){
                            editor.putString("NOMBRE2",nombre);
                            editor.putLong("TIEMP2",temp);
                            editor.putString("NOMBRE3",nombre2);
                            editor.putLong("TIEMP3",tiemp2);
                        }
                        else{
                            if(temp>tiemp3){
                                editor.putString("NOMBRE3",nombre);
                                editor.putLong("TIEMP3",temp);
                            }
                        }
                    }
                }
            }
        }
    }

    protected void isGameOver(){
        int j;
        if(contCor==3){
            cor1.setVisibility(View.VISIBLE);
            cor2.setVisibility(View.VISIBLE);
            cor3.setVisibility(View.VISIBLE);
        }
        else if(contCor==2){
            cor1.setVisibility(View.VISIBLE);
            cor2.setVisibility(View.VISIBLE);
            cor3.setVisibility(View.INVISIBLE);
        }
        else if(contCor==1){
            cor1.setVisibility(View.VISIBLE);
            cor2.setVisibility(View.INVISIBLE);
            cor3.setVisibility(View.INVISIBLE);
        }
        else if(contCor==0){
            cor1.setVisibility(View.INVISIBLE);
            cor2.setVisibility(View.INVISIBLE);
            cor3.setVisibility(View.INVISIBLE);
        }
        else{
            pauseTiempoG1();
            pauseTiempoG2();
            pauseNueva();
            pauseTiempo1();
            pauseTiempo2();
            pauseTiempo3();
            pauseTiempo4();
            pauseTiempo5();
            pauseTiempo6();
            pauseTiempo7();
            pauseTiempo8();
            pauseTiempo9();
            newRecord();
            for(j=0;j<mal.length;j++){
                mal[j].setVisibility(View.INVISIBLE);
            }
            sp.play(idFin, volumeEf, volumeEf, 1, 0, 1);
            gameOver.setVisibility(View.VISIBLE);
            tiempoFinal.setText("Enhorabuena, has aguantado "+Long.toString(temp)+" segundos");
            volver.setVisibility(View.VISIBLE);
        }
    }
}