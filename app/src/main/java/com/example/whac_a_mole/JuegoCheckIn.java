package com.example.whac_a_mole;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class JuegoCheckIn extends AppCompatActivity {

    protected ImageView[] mal = new ImageView[9];
    protected ImageView cor1, cor2, cor3;
    protected TextView tiempo;
    protected int[] typeMal = new int[9];
    protected int[] contClick = new int[9];
    protected int i, contCor = 3, ranNum;
    protected long[] tempMal = new long[9];
    protected static final long tCDTG = 10000, interval = 1000, interval2 = 200, interval3 = 100;
    protected long temp, tempBien = 500, tempNueva = 1500, tVerd = 2000, tAmar = 3000, tRoja = 4000, tNegra = 5000;
    protected CountDownTimer[] CDTmal = new CountDownTimer[9];
    protected CountDownTimer[] CDTbien = new CountDownTimer[9];
    protected CountDownTimer CDTG1, CDTG2, CDTNueva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_check_in);

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

        temp = 0;
        tiempo.setText("0");
        iniMal();
        iniContClick();

        startTiempoG1();
        ranNum = (int) (Math.random() * 9);
        setMal(ranNum);

        mal[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (typeMal[0] == 0) ;
                else{
                    pauseTiempo1();
                    startBien1(0);
                    isClicked(0);
                }
            }
        });

        mal[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (typeMal[1] == 0) ;
                else{
                    pauseTiempo2();
                    startBien2(1);
                    isClicked(1);
                }
            }
        });

        mal[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (typeMal[2] == 0) ;
                else{
                    pauseTiempo3();
                    startBien3(2);
                    isClicked(2);
                }
            }
        });

        mal[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (typeMal[3] == 0) ;
                else{
                    pauseTiempo4();
                    startBien4(3);
                    isClicked(3);
                }
            }
        });

        mal[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (typeMal[4] == 0) ;
                else{
                    pauseTiempo5();
                    startBien5(4);
                    isClicked(4);
                }
            }
        });

        mal[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (typeMal[5] == 0) ;
                else{
                    pauseTiempo6();
                    startBien6(5);
                    isClicked(5);
                }
            }
        });

        mal[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (typeMal[6] == 0) ;
                else{
                    pauseTiempo7();
                    startBien7(6);
                    isClicked(6);
                }
            }
        });

        mal[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (typeMal[7] == 0) ;
                else{
                    pauseTiempo8();
                    startBien8(7);
                    isClicked(7);
                }
            }
        });

        mal[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (typeMal[8] == 0) ;
                else{
                    pauseTiempo9();
                    startBien9(8);
                    isClicked(8);
                }
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
            typeMal[x] = 5;
        }
        else if(typeMal[x]==2){
            contClick[x]++;
            if(contClick[x]==5){
                mal[x].setImageResource(R.drawable.maleta_amarilla_facturada);
                typeMal[x] = 5;
                contClick[x] = 0;
            }
        }
        else if(typeMal[x]==3){
            contClick[x]++;
            if(contClick[x]==10){
                mal[x].setImageResource(R.drawable.maleta_roja_facturada);
                typeMal[x] = 5;
                contClick[x] = 0;
            }
        }
        else if(typeMal[x]==4){
            contClick[x]++;
            if(contClick[x]==20){
                mal[x].setImageResource(R.drawable.maleta_negra_facturada);
                typeMal[x] = 5;
                contClick[x] = 0;
                if(contCor<3){
                    contCor++;
                    isGameOver();
                }
            }
        }
        else{
            Log.e("Error","typeMal en mesa 1");
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

    protected void isGameOver(){
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
            finish();
        }
    }
}