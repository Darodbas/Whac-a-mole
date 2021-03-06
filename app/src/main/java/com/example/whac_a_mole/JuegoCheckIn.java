package com.example.whac_a_mole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.AudioAttributes;
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
    protected ImageView cor1, cor2, cor3,ivPortafoliocheckin;
    protected TextView tiempo, gameOver, tiempoFinal,tvClicksTotales,tvMaletasVerdes,tvMaletasAmarillas,tvMaletasRojas,tvMaletasNegras;
    protected int[] typeMal = new int[9];
    protected int[] contClick = new int[9];
    protected int[] tempOn = new int[20];
    protected int contCor = 3, ranNum, idAcierto, idFallo, idInicio, idFin, numClicks, idMegafoniaFin,idBeep,idFinalBeep ;
    protected long[] tempMal = new long[9];
    protected static final long tCDTG = 20000, interval = 1000, interval2 = 200, interval3 = 100;
    protected long temp, tempBien = 500, tempNueva = 1500, tVerd = 2000, tAmar = 3000, tRoja = 4000, tNegra = 5000, tiemp1, tiemp2, tiemp3;
    protected CountDownTimer[] CDTmal = new CountDownTimer[9];
    protected CountDownTimer[] CDTbien = new CountDownTimer[9];
    protected CountDownTimer CDTG1, CDTNueva,cuentaAtras;
    protected Button volver;
    protected SoundPool sp;
    protected float volumeEf, volumeM, volumeEfMeg;
    protected int contVerdes=0,contAmarillas=0,contRojas=0,contNegras=0;
    protected boolean  backPressed=false,musicaParada=false,cuentaAtrasOn=true;
    protected int t;//solo se utiliza en la cuenta atras

    MediaPlayer mp;

    protected long tiempoAnimacion =50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_check_in);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
        //Para que la barra de navegacion y de notificaciones tenga el color de la App
        getWindow().setNavigationBarColor(Color.rgb(0,59,113));
        getWindow().setStatusBarColor(Color.rgb(0,59,113));

        //sonidos
        AudioAttributes audioAttributes = new
                AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION).setContentType(
                AudioAttributes.CONTENT_TYPE_SONIFICATION).build();

        sp = new SoundPool.Builder().setMaxStreams(5).setAudioAttributes(audioAttributes).build();

        idAcierto = sp.load(this, R.raw.acierto, 1);
        idFallo = sp.load(this, R.raw.fallo, 1);
        idInicio = sp.load(this, R.raw.megafonia, 1);
        idFin = sp.load(this, R.raw.finalpartida, 1);
        idMegafoniaFin = sp.load(this, R.raw.megafoniasalir, 1);
        idBeep = sp.load(this, R.raw.beep, 1);
        idFinalBeep = sp.load(this, R.raw.finalbeep, 1);

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

        //cosas que he a??adido
        ivPortafoliocheckin = findViewById(R.id.ivPortafolioCheckin);
        tvClicksTotales = findViewById(R.id.tvClicksTotales);
        tvMaletasVerdes = findViewById(R.id.tvMaletasVerdes);
        tvMaletasAmarillas = findViewById(R.id.tvMaletasAmarillas);
        tvMaletasRojas = findViewById(R.id.tvMaletasRojas);
        tvMaletasNegras = findViewById(R.id.tvMaletasNegras);

    //ocultamos las maletas
        for(int i=0;i<9;i++){
            mal[i].setVisibility(View.INVISIBLE);
        }


        temp = 0;
        numClicks = 0;
        tiempo.setText("0");
        //Empezamos con cuenta atras y cuando termine comenzar?? el juego
        tiempo.setText("3");
        tiempo.setTextColor(Color.GREEN);
        CountDownTimer tiempoEsperaCuentaAtras = new CountDownTimer(200,100) {//si no no suenan los beeps//
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                CuentaAtras();
            }
        }.start();




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
                numClicks++;
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
                numClicks++;
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
                numClicks++;
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
                numClicks++;
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
                numClicks++;
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
                numClicks++;
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
                numClicks++;
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
                numClicks++;
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
                SharedPreferences preferencias = getSharedPreferences("PREFERENCIAS",MODE_PRIVATE);

                if(cuentaAtrasOn){
                    cuentaAtras.cancel();
                    Intent mi_intent = new Intent(view.getContext(), MainActivity.class);
                    mi_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mi_intent);
                    finish();

                }else if(!backPressed&&preferencias.getBoolean("EFECTOS",true)){
                    sp.play(idMegafoniaFin, volumeEfMeg, volumeEfMeg, 1, 0, 1);
                    CountDownTimer esperaMegafono = new CountDownTimer(2500,500) {
                        @Override
                        public void onTick(long l) {

                        }

                        @Override
                        public void onFinish() {
                            Intent mi_intent = new Intent(view.getContext(), MainActivity.class);
                            mi_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(mi_intent);
                            finish();
                        }
                    }.start();
                }else{
                    Intent mi_intent = new Intent(view.getContext(), MainActivity.class);
                    mi_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mi_intent);
                    finish();
                }

            }
        });
    }

    @Override
    public void onBackPressed(){

        if(!cuentaAtrasOn){
            if(!musicaParada){ //no se puede comprobar isPlaying despues de un Release//
                mp.stop();
                mp.release();
            }
        }

        contCor=-1;
        isGameOver(true);
        backPressed=true;
        volver.performClick();
    }

    protected void startTiempoG1(){
        CDTG1 = new CountDownTimer(tCDTG,interval) {
            @Override
            public void onTick(long l) {
                temp++;
                tiempo.setText(Long.toString(temp));
                tempOn[18]=1;
            }

            @Override
            public void onFinish() {
                if(tVerd>600) tVerd -= 200;
                if(tAmar>800) tAmar -= 200;
                if(tRoja>1000) tRoja -= 200;
                if(tNegra>1500)  tNegra -= 200;
                if(tempNueva>500) tempNueva -= 100;
                tempOn[18]=0;

                startTiempoG1();
            }
        }.start();
    }

    protected void pauseTiempoG1(){
        tempOn[18]=0;
        CDTG1.cancel();
    }

    protected void startNueva(){
        CDTNueva = new CountDownTimer(tempNueva, interval3) {
            @Override
            public void onTick(long l) {
                tempOn[19]=1;
            }

            @Override
            public void onFinish() {
                ranNum = (int)(Math.random()*9);
                while(typeMal[ranNum]!=0){
                    ranNum = (int)(Math.random()*9);
                }
                tempOn[19]=0;
                setMal(ranNum);
            }
        }.start();
    }

    protected void pauseNueva(){
        tempOn[19]=0;
        CDTNueva.cancel();
    }

    protected void startBien1(int x){
        CDTbien[x] = new CountDownTimer(tempBien, interval3) {
            @Override
            public void onTick(long l) {
                tempOn[9]=1;
            }

            @Override
            public void onFinish() {
                tempOn[9]=0;
                mal[x].setVisibility(View.INVISIBLE);
                typeMal[x] = 0;
            }
        }.start();
    }

    protected void pauseBien1(int x){
        tempOn[9]=0;
        CDTbien[x].cancel();
    }

    protected void startBien2(int x){
        CDTbien[x] = new CountDownTimer(tempBien, interval3) {
            @Override
            public void onTick(long l) {
                tempOn[10]=1;
            }

            @Override
            public void onFinish() {
                tempOn[10]=0;
                mal[x].setVisibility(View.INVISIBLE);
                typeMal[x] = 0;
            }
        }.start();
    }

    protected void pauseBien2(int x){
        tempOn[10]=0;
        CDTbien[x].cancel();
    }

    protected void startBien3(int x){
        CDTbien[x] = new CountDownTimer(tempBien, interval3) {
            @Override
            public void onTick(long l) {
                tempOn[11]=1;
            }

            @Override
            public void onFinish() {
                tempOn[11]=0;
                mal[x].setVisibility(View.INVISIBLE);
                typeMal[x] = 0;
            }
        }.start();
    }

    protected void pauseBien3(int x){
        tempOn[11]=0;
        CDTbien[x].cancel();
    }

    protected void startBien4(int x){
        CDTbien[x] = new CountDownTimer(tempBien, interval3) {
            @Override
            public void onTick(long l) {
                tempOn[12]=1;
            }

            @Override
            public void onFinish() {
                tempOn[12]=0;
                mal[x].setVisibility(View.INVISIBLE);
                typeMal[x] = 0;
            }
        }.start();
    }

    protected void pauseBien4(int x){
        tempOn[12]=0;
        CDTbien[x].cancel();
    }

    protected void startBien5(int x){
        CDTbien[x] = new CountDownTimer(tempBien, interval3) {
            @Override
            public void onTick(long l) {
                tempOn[13]=1;
            }

            @Override
            public void onFinish() {
                tempOn[13]=0;
                mal[x].setVisibility(View.INVISIBLE);
                typeMal[x] = 0;
            }
        }.start();
    }

    protected void pauseBien5(int x){
        tempOn[13]=0;
        CDTbien[x].cancel();
    }

    protected void startBien6(int x){
        CDTbien[x] = new CountDownTimer(tempBien, interval3) {
            @Override
            public void onTick(long l) {
                tempOn[14]=1;
            }

            @Override
            public void onFinish() {
                tempOn[14]=0;
                mal[x].setVisibility(View.INVISIBLE);
                typeMal[x] = 0;
            }
        }.start();
    }

    protected void pauseBien6(int x){
        tempOn[14]=0;
        CDTbien[x].cancel();
    }

    protected void startBien7(int x){
        CDTbien[x] = new CountDownTimer(tempBien, interval3) {
            @Override
            public void onTick(long l) {
                tempOn[15]=1;
            }

            @Override
            public void onFinish() {
                tempOn[15]=0;
                mal[x].setVisibility(View.INVISIBLE);
                typeMal[x] = 0;
            }
        }.start();
    }

    protected void pauseBien7(int x){
        tempOn[15]=0;
        CDTbien[x].cancel();
    }

    protected void startBien8(int x){
        CDTbien[x] = new CountDownTimer(tempBien, interval3) {
            @Override
            public void onTick(long l) {
                tempOn[16]=1;
            }

            @Override
            public void onFinish() {
                tempOn[16]=0;
                mal[x].setVisibility(View.INVISIBLE);
                typeMal[x] = 0;
            }
        }.start();
    }

    protected void pauseBien8(int x){
        tempOn[16]=0;
        CDTbien[x].cancel();
    }

    protected void startBien9(int x){
        CDTbien[x] = new CountDownTimer(tempBien, interval3) {
            @Override
            public void onTick(long l) {
                tempOn[17]=1;
            }

            @Override
            public void onFinish() {
                tempOn[17]=0;
                mal[x].setVisibility(View.INVISIBLE);
                typeMal[x] = 0;
            }
        }.start();
    }

    protected void pauseBien9(int x){
        tempOn[17]=0;
        CDTbien[x].cancel();
    }

    protected void startTiempo1(){
        CDTmal[0] = new CountDownTimer(tempMal[0],interval2) {
            @Override
            public void onTick(long l) {
                tempOn[0]=1;
            }

            @Override
            public void onFinish() {
                tempOn[0]=0;
                mal[0].setVisibility(View.INVISIBLE);
                typeMal[0] = 0;
                contCor--;
                sp.play(idFallo, volumeEf, volumeEf, 1, 0, 1);
                isGameOver(false);
            }
        }.start();
    }

    protected void pauseTiempo1(){
        tempOn[0]=0;
        CDTmal[0].cancel();
    }

    protected void startTiempo2(){
        CDTmal[1] = new CountDownTimer(tempMal[1],interval2) {
            @Override
            public void onTick(long l) {
                tempOn[1]=1;
            }

            @Override
            public void onFinish() {
                tempOn[1]=0;
                mal[1].setVisibility(View.INVISIBLE);
                typeMal[1] = 0;
                contCor--;
                sp.play(idFallo, volumeEf, volumeEf, 1, 0, 1);
                isGameOver(false);
            }
        }.start();
    }

    protected void pauseTiempo2(){
        tempOn[1]=0;
        CDTmal[1].cancel();
    }

    protected void startTiempo3(){
        CDTmal[2] = new CountDownTimer(tempMal[2],interval2) {
            @Override
            public void onTick(long l) {
                tempOn[2]=1;
            }

            @Override
            public void onFinish() {
                tempOn[2]=0;
                mal[2].setVisibility(View.INVISIBLE);
                typeMal[2] = 0;
                contCor--;
                sp.play(idFallo, volumeEf, volumeEf, 1, 0, 1);
                isGameOver(false);
            }
        }.start();
    }

    protected void pauseTiempo3(){
        tempOn[2]=0;
        CDTmal[2].cancel();
    }

    protected void startTiempo4(){
        CDTmal[3] = new CountDownTimer(tempMal[3],interval2) {
            @Override
            public void onTick(long l) {
                tempOn[3]=1;
            }

            @Override
            public void onFinish() {
                tempOn[3]=0;
                mal[3].setVisibility(View.INVISIBLE);
                typeMal[3] = 0;
                contCor--;
                sp.play(idFallo, volumeEf, volumeEf, 1, 0, 1);
                isGameOver(false);
            }
        }.start();
    }

    protected void pauseTiempo4(){
        tempOn[3]=0;
        CDTmal[3].cancel();
    }

    protected void startTiempo5(){
        CDTmal[4] = new CountDownTimer(tempMal[4],interval2) {
            @Override
            public void onTick(long l) {
                tempOn[4]=1;
            }

            @Override
            public void onFinish() {
                tempOn[4]=0;
                mal[4].setVisibility(View.INVISIBLE);
                typeMal[4] = 0;
                contCor--;
                sp.play(idFallo, volumeEf, volumeEf, 1, 0, 1);
                isGameOver(false);
            }
        }.start();
    }

    protected void pauseTiempo5(){
        tempOn[4]=0;
        CDTmal[4].cancel();
    }

    protected void startTiempo6(){
        CDTmal[5] = new CountDownTimer(tempMal[5],interval2) {
            @Override
            public void onTick(long l) {
                tempOn[5]=1;
            }

            @Override
            public void onFinish() {
                tempOn[5]=0;
                mal[5].setVisibility(View.INVISIBLE);
                typeMal[5] = 0;
                contCor--;
                sp.play(idFallo, volumeEf, volumeEf, 1, 0, 1);
                isGameOver(false);
            }
        }.start();
    }

    protected void pauseTiempo6(){
        tempOn[5]=0;
        CDTmal[5].cancel();
    }

    protected void startTiempo7(){
        CDTmal[6] = new CountDownTimer(tempMal[6],interval2) {
            @Override
            public void onTick(long l) {
                tempOn[6]=1;
            }

            @Override
            public void onFinish() {
                tempOn[6]=0;
                mal[6].setVisibility(View.INVISIBLE);
                typeMal[6] = 0;
                contCor--;
                sp.play(idFallo, volumeEf, volumeEf, 1, 0, 1);
                isGameOver(false);
            }
        }.start();
    }

    protected void pauseTiempo7(){
        tempOn[6]=0;
        CDTmal[6].cancel();
    }

    protected void startTiempo8(){
        CDTmal[7] = new CountDownTimer(tempMal[7],interval2) {
            @Override
            public void onTick(long l) {
                tempOn[7]=1;
            }

            @Override
            public void onFinish() {
                tempOn[7]=0;
                mal[7].setVisibility(View.INVISIBLE);
                typeMal[7] = 0;
                contCor--;
                sp.play(idFallo, volumeEf, volumeEf, 1, 0, 1);
                isGameOver(false);
            }
        }.start();
    }

    protected void pauseTiempo8(){
        tempOn[7]=0;
        CDTmal[7].cancel();
    }

    protected void startTiempo9(){
        CDTmal[8] = new CountDownTimer(tempMal[8],interval2) {
            @Override
            public void onTick(long l) {
                tempOn[8]=1;
            }

            @Override
            public void onFinish() {
                tempOn[8]=0;
                mal[8].setVisibility(View.INVISIBLE);
                typeMal[8] = 0;
                contCor--;
                sp.play(idFallo, volumeEf, volumeEf, 1, 0, 1);
                isGameOver(false);
            }
        }.start();
    }

    protected void pauseTiempo9(){
        tempOn[8]=0;
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
        int i;
        for(i=0;i<contClick.length;i++){
            contClick[i]=0;
        }
    }

    protected void iniTempOn(){
        int i;
        /*De la posici??n 0 a la 8 hace referencia a los temporizadores CDTmal
        * De la posici??n 9 a la 17 hace referencia a los temporizadores CDTbien
        * La posici??n 18 y 19 hacen referencia a los temporizadores CDTG1 y CDTG2
        * Finalmente la posici??n 20 hace referencia al temporizacor CDTNueva */
        for(i=0;i<tempOn.length;i++){
            tempOn[i]=0;
            //Si tempOn[i]=0 significa que el temporizador no est?? activo
        }
    }

    protected void isClicked(int x) {

        CountDownTimer cuentaAnimaci??n[] = new CountDownTimer[9];//vector de cuentas atras Falta a??adir un sonido cada vez que se le da a cada maleta!!


        if(typeMal[x]==0);
        else if(typeMal[x]==1){

            mal[x].setScaleY((float) 1.2); //prueba animacion
            mal[x].setScaleX((float) 1.2);


            cuentaAnimaci??n[x]=new CountDownTimer(tiempoAnimacion,10) {
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    mal[x].setScaleY((float) 1);
                    mal[x].setScaleX((float) 1);
                }
            }.start();

            mal[x].setImageResource(R.drawable.maleta_verde_facturada);
            sp.play(idAcierto, volumeEf, volumeEf, 1, 0, 1);
            contVerdes++;
            typeMal[x] = 5;
        }
        else if(typeMal[x]==2){
            contClick[x]++;

            mal[x].setScaleY((float) 1.2); //prueba animacion
            mal[x].setScaleX((float) 1.2);
            // AQUI VA EL SONIDO A PONER//

            cuentaAnimaci??n[x]=new CountDownTimer(tiempoAnimacion,10) {
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    mal[x].setScaleY((float) 1);
                    mal[x].setScaleX((float) 1);
                }
            }.start(); // Solo he a??adido hasta aqu??

            if(contClick[x]==3){
                mal[x].setImageResource(R.drawable.maleta_amarilla_facturada);
                sp.play(idAcierto, volumeEf, volumeEf, 1, 0, 1);
                contAmarillas++;
                typeMal[x] = 5;
                contClick[x] = 0;
            }
        }
        else if(typeMal[x]==3){
            contClick[x]++;

            mal[x].setScaleY((float) 1.2); //prueba animacion
            mal[x].setScaleX((float) 1.2);
            // AQUI VA EL SONIDO A PONER//

            cuentaAnimaci??n[x]=new CountDownTimer(tiempoAnimacion,10) {
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    mal[x].setScaleY((float) 1);
                    mal[x].setScaleX((float) 1);
                }
            }.start(); // Solo he a??adido hasta aqu??



            if(contClick[x]==5){
                mal[x].setImageResource(R.drawable.maleta_roja_facturada);
                sp.play(idAcierto, volumeEf, volumeEf, 1, 0, 1);
                contRojas++;
                typeMal[x] = 5;
                contClick[x] = 0;
            }
        }
        else if(typeMal[x]==4){
            contClick[x]++;

            mal[x].setScaleY((float) 1.2); //prueba animacion
            mal[x].setScaleX((float) 1.2);
            // AQUI VA EL SONIDO A PONER//

            cuentaAnimaci??n[x]=new CountDownTimer(tiempoAnimacion,10) {
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    mal[x].setScaleY((float) 1);
                    mal[x].setScaleX((float) 1);
                }
            }.start(); // Solo he a??adido hasta aqu??

            if(contClick[x]==10){
                mal[x].setImageResource(R.drawable.maleta_negra_facturada);
                sp.play(idAcierto, volumeEf, volumeEf, 1, 0, 1);
                contNegras++;
                typeMal[x] = 5;
                contClick[x] = 0;
                if(contCor<3){
                    contCor++;
                    isGameOver(false);
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
        String nombre, nombre1,nombre2,nombre3;
        int numC1, numC2, numC3;
        SharedPreferences records = getSharedPreferences("RECORDS",MODE_PRIVATE);
        SharedPreferences.Editor editor = records.edit();

        nombre = records.getString("NOMBRE", " ");
        nombre1 = records.getString("NOMBRE1", "");
        nombre2 = records.getString("NOMBRE2", "");
        nombre3 = records.getString("NOMBRE3", "");
        tiemp1 = records.getLong("TIEMP1", -1);
        tiemp2 = records.getLong("TIEMP2", -1);
        tiemp3 = records.getLong("TIEMP3", -1);
        numC1 = records.getInt("NUMC1",-1);
        numC2 = records.getInt("NUMC2",-1);
        numC3 = records.getInt("NUMC3",-1);

        numClicks=contVerdes+contAmarillas*3+contRojas*5+contNegras*10;

                        if(temp>tiemp1     ||    (temp==tiemp1)&&numClicks>numC1     ){ //si los tiempos son iguales se desempata por el numero de clics

                            editor.putString("NOMBRE3",nombre2);
                            editor.putLong("TIEMP3",tiemp2);
                            editor.putInt("NUMC3",numC2);
                            editor.putString("NOMBRE2",nombre1);
                            editor.putLong("TIEMP2",tiemp1);
                            editor.putInt("NUMC2",numC1);
                            editor.putString("NOMBRE1",nombre);
                            editor.putLong("TIEMP1",temp);
                            editor.putInt("NUMC1",numClicks);


                            editor.commit();
                        }
                        else if(temp>tiemp2      ||    (temp==tiemp2)&&numClicks>numC2){

                            editor.putString("NOMBRE3",nombre2);
                            editor.putLong("TIEMP3",tiemp2);
                            editor.putInt("NUMC3",numC2);

                                editor.putString("NOMBRE2",nombre);
                                editor.putLong("TIEMP2",temp);
                                editor.putInt("NUMC2",numClicks);

                                editor.commit();
                            }
                            else if(temp>tiemp3      ||    (temp==tiemp3)&&numClicks>numC3){
                                    editor.putString("NOMBRE3",nombre);
                                    editor.putLong("TIEMP3",temp);
                                    editor.putInt("NUMC3",numClicks);
                                    editor.commit();
                                }
                            }



    protected void isGameOver(boolean isBackPressed){
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
            if(tempOn[0]==1) pauseTiempo1();
            if(tempOn[1]==1) pauseTiempo2();
            if(tempOn[2]==1) pauseTiempo3();
            if(tempOn[3]==1) pauseTiempo4();
            if(tempOn[4]==1) pauseTiempo5();
            if(tempOn[5]==1) pauseTiempo6();
            if(tempOn[6]==1) pauseTiempo7();
            if(tempOn[7]==1) pauseTiempo8();
            if(tempOn[8]==1) pauseTiempo9();
            if(tempOn[9]==1) pauseBien1(0);
            if(tempOn[10]==1) pauseBien2(1);
            if(tempOn[11]==1) pauseBien3(2);
            if(tempOn[12]==1) pauseBien4(3);
            if(tempOn[13]==1) pauseBien5(4);
            if(tempOn[14]==1) pauseBien6(5);
            if(tempOn[15]==1) pauseBien7(6);
            if(tempOn[16]==1) pauseBien8(7);
            if(tempOn[17]==1) pauseBien9(8);
            if(tempOn[18]==1) pauseTiempoG1();
            if(tempOn[19]==1) pauseNueva();
            for(j=0;j<mal.length;j++){
                mal[j].setVisibility(View.INVISIBLE);
            }
            if(isBackPressed==false){
                newRecord();

                if(mp.isPlaying()){ //Se puede hacer de esta forma porque suena siempre, pero sin volumen en caso de desactivar la musica
                    mp.stop();
                    mp.release();
                    musicaParada=true;
                }
                sp.play(idFin, volumeEf, volumeEf, 1, 0, 1);//recomendado volumen 0.02
                gameOver.setVisibility(View.VISIBLE);

                //A??adir portapapeles con resultados//
                //numClicks=contVerdes+contAmarillas*3+contRojas*5+contNegras*10;

                tiempoFinal.setText("Tiempo facturando: "+Long.toString(temp)+" segundos");
                tvClicksTotales.setText("Clics totales: "+numClicks);
                tvMaletasVerdes.setText("Maletas verdes facturadas: "+contVerdes);
                tvMaletasAmarillas.setText("Maletas amarillas facturadas: "+contAmarillas);
                tvMaletasRojas.setText("Maletas rojas facturadas: "+contRojas);
                tvMaletasNegras.setText("Maletas negras facturadas: "+contNegras);



                volver.setVisibility(View.VISIBLE);
                ivPortafoliocheckin.setVisibility(View.VISIBLE);

            }
        }
    }
    //Cuenta atras al empezar
    protected void CuentaAtras(){
        SharedPreferences preferencias = getSharedPreferences("PREFERENCIAS",MODE_PRIVATE);
        if(preferencias.getBoolean("EFECTOS",true)){
            volumeEf = 1;
            volumeEfMeg = 0.3f;//
        }
        else{
            volumeEf = 0.0f;
            volumeEfMeg = 0.0f;
        }

        if(preferencias.getBoolean("MUSICA", true)){
            volumeM = 0.05f;
        }
        else{
            volumeM = 0.0F;
        }
        t=3;


        tiempo.setTextColor(Color.GREEN);
        cuentaAtras = new CountDownTimer(4000,1000) {
            @Override
            public void onTick(long l) {

                if(t>0){
                    tiempo.setText(Integer.toString(t));
                    sp.play(idBeep, (float) (volumeEf*0.02), (float) (volumeEf*0.02), 1, 0, 1);
                    t--;
                }else{
                    tiempo.setText("??YA!");
                    sp.play(idFinalBeep, (float) (volumeEf*0.02), (float) (volumeEf*0.02), 1, 0, 1);
                }

            }

            @Override
            public void onFinish() {
                cuentaAtrasOn=false;
                tiempo.setTextColor(Color.rgb(166,237,255));
                iniMal();
                iniContClick();
                iniTempOn();

                startTiempoG1();
                ranNum = (int) (Math.random() * 9);
                setMal(ranNum);



                mp = MediaPlayer.create(JuegoCheckIn.this, R.raw.musicajuegos);
                mp.setVolume(volumeM, volumeM);
                mp.setLooping(true);
                mp.start();

            }
        }.start();


    }
}