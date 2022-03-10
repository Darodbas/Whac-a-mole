package com.example.whac_a_mole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class JuegoPasaportes extends AppCompatActivity {

    CountDownTimer CDT;
    CountDownTimer CDTpant;
    CountDownTimer[] tiempoEspera= new CountDownTimer[9];
    long tiempoMilisegundos =20000;
    long intervalAparicionPas;
    long tiempoDesaparecePas;
    long tiempoRestante;
    CountDownTimer[] espera1= new CountDownTimer[9];
    CountDownTimer[] espera2= new CountDownTimer[9];
    //protected SharedPreferences opcionesPasaportes= getSharedPreferences("OPCIONESPASAPORTES",MODE_PRIVATE);

    protected SoundPool sp;
    protected int idAcierto,idFallo,idStamp,idFinalPartida;


    protected ImageView ivPas1;
    protected ImageView ivPas2;
    protected ImageView ivPas3;
    protected ImageView ivPas4;
    protected ImageView ivPas5;
    protected ImageView ivPas6;
    protected ImageView ivPas7;
    protected ImageView ivPas8;
    protected ImageView ivPas9;
    protected ImageView ivTintaVerde;
    protected ImageView ivTintaRoja;
    protected TextView tvCountdown;
    protected TextView tvPuntuacion;
    protected Button btVolver;

    protected int estadoGlobal;
    protected int[] estados ={0,0,0,0,0,0,0,0,0};
    protected int tinta = 1;//1=verde 2=rojo 0=no hay tinta
    protected int puntuacion=0;
    protected int legalesAceptados;
    protected int legalesRechazados;
    protected int ilegalesAceptados;
    protected int ilegalesRechazados;
    protected int sinAtender;
    protected int combo;
    protected int combomax;
    protected int tintaAlPulsar;
    protected int dificultad;

    //resumen
    protected ImageView ivPortafolio;
    protected TextView tvLegalesAceptados;
    protected TextView tvLegalesRechazados;
    protected TextView tvIlegalesAceptados;
    protected TextView tvIlegalesRechazados;
    protected TextView tvCombo;
    protected TextView tvPuntuacionResumen;
    protected TextView tvResumen;
    protected TextView tvSinAtender;
    protected TextView tvRate;

    //Funcion que oculta todos los pasaportes
    protected  void OcultaPasyResum(){
        ivPas1.setVisibility(View.INVISIBLE);
        ivPas2.setVisibility(View.INVISIBLE);
        ivPas3.setVisibility(View.INVISIBLE);
        ivPas4.setVisibility(View.INVISIBLE);
        ivPas5.setVisibility(View.INVISIBLE);
        ivPas6.setVisibility(View.INVISIBLE);
        ivPas7.setVisibility(View.INVISIBLE);
        ivPas8.setVisibility(View.INVISIBLE);
        ivPas9.setVisibility(View.INVISIBLE);

        btVolver.setVisibility(View.INVISIBLE);

        ivPortafolio.setVisibility(View.INVISIBLE);
        tvLegalesAceptados.setVisibility(View.INVISIBLE);
        tvLegalesRechazados.setVisibility(View.INVISIBLE);
        tvIlegalesAceptados.setVisibility(View.INVISIBLE);
        tvIlegalesRechazados.setVisibility(View.INVISIBLE);
        tvCombo.setVisibility(View.INVISIBLE);
        tvPuntuacionResumen.setVisibility(View.INVISIBLE);
        tvResumen.setVisibility(View.INVISIBLE);
        tvSinAtender.setVisibility(View.INVISIBLE);
        tvRate.setVisibility(View.INVISIBLE);


    }

    //Funcion que muestra el resumen y el boton volver
    protected void MuestraResum(){

        ivPortafolio.setVisibility(View.VISIBLE);
        tvLegalesAceptados.setVisibility(View.VISIBLE);
        tvLegalesRechazados.setVisibility(View.VISIBLE);
        tvIlegalesAceptados.setVisibility(View.VISIBLE);
        tvIlegalesRechazados.setVisibility(View.VISIBLE);
        tvCombo.setVisibility(View.VISIBLE);
        tvPuntuacionResumen.setVisibility(View.VISIBLE);
        tvResumen.setVisibility(View.VISIBLE);
        tvSinAtender.setVisibility(View.VISIBLE);
        tvRate.setVisibility(View.VISIBLE);
        ivTintaVerde.setScaleY(1);
        ivTintaVerde.setScaleX(1);
        ivTintaRoja.setScaleY(1);
        ivTintaRoja.setScaleX(1);
        //Hacer visible el botón volver
        btVolver.setVisibility(View.VISIBLE);

        int total = legalesAceptados+legalesRechazados+ilegalesAceptados+ilegalesRechazados;
        float velMedia=(float)total/(float)(tiempoMilisegundos/1000);
        String strVelMedia=String.format("%.2f", velMedia);

        tvSinAtender.setText("Pasaportes sin atender: "+Integer.toString(sinAtender));
        tvLegalesAceptados.setText("Pasaportes legales aceptados: "+Integer.toString(legalesAceptados));
        tvIlegalesRechazados.setText("Pasaportes ilegales rechazados: "+Integer.toString(ilegalesRechazados));
        tvLegalesRechazados.setText("Pasaportes legales rechazados: "+Integer.toString(legalesRechazados));
        tvIlegalesAceptados.setText("Pasaportes ilegales aceptados: "+Integer.toString(ilegalesAceptados));

        String fullCombo="";
        if(legalesRechazados+ilegalesAceptados+sinAtender==0){
            fullCombo="(Full Combo)";
        }
        tvCombo.setText("Combo máximo: "+Integer.toString(combomax)+" pasaportes seguidos");
        tvPuntuacionResumen.setText("Puntuación total: "+Integer.toString(puntuacion)+" puntos "+fullCombo);

        tvRate.setText("Ratio: "+strVelMedia+" pasaportes/segundo");

    }

    //Funcion timer Por pantalla
    protected void startPantTimer(){
        tiempoRestante=tiempoMilisegundos;
        //countDownInterval: Tiempo que tarda en generarse* un nuevo pasaporte (puede no generarse si toca una posicion que esta ocupada)
        CDTpant = new CountDownTimer(tiempoMilisegundos, 1000) {
            @Override
            public void onTick(long l) {
                tvCountdown.setText(String.format("%2d",l/1000));
                }


            @Override
            public void onFinish() {

                estadoGlobal=0;
                tvCountdown.setText("0");
                sp.play(idFinalPartida, 1, 1, 1, 0, 1);

                OcultaPasyResum();
                MuestraResum();


            }
        }.start();
    }

    //Función inicia el CDT (Aquí se definen los tiempos)
    protected void startTimer(){
        tiempoRestante=tiempoMilisegundos;
        //countDownInterval: Tiempo que tarda en generarse* un nuevo pasaporte (puede no generarse si toca una posicion que esta ocupada)
        CDT = new CountDownTimer(tiempoMilisegundos, intervalAparicionPas) {
            @Override
            public void onTick(long l) {

                //Tiempo espera: tiempo en que desaparece el pasaporte
                tiempoRestante-=intervalAparicionPas;
                if(tiempoRestante>=tiempoDesaparecePas) {
                    GeneraPas(tiempoDesaparecePas);
                }

            }
            @Override
            public void onFinish() {

            }
        }.start();
    }

    //Función finaliza el CDT
    protected void pauseTimer() {
        CDT.cancel();
    }

    //Funcion actualiza la puntuacion
    protected void ActualizaPuntuacion(){

        if(puntuacion<=0){
            puntuacion=0;
        }
        tvPuntuacion.setText(Integer.toString(puntuacion));
    }

    //Función genera un Pasaporte Aleatorio en posicion aleatoria
    protected void GeneraPas(long TiempoEspera){


        //Generamos numero de mesa y de pasaporte
        int numMesa =  (int)(Math.random()*9);
        int numPas = (int)((Math.random()*3)+1);



        //asociamos el pasaporte a la mesa solo si no hay ya pasaporte
        if(estados[numMesa]==0){

            estados[numMesa]=numPas;


            //muestra el pasaporte en la mesa
            switch(numMesa){

                case 0:
                    if(numPas==1){
                        ivPas1.setVisibility(View.VISIBLE);
                        ivPas1.setImageResource(R.drawable.pas1empty);
                    }else if(numPas==2){
                        ivPas1.setVisibility(View.VISIBLE);
                        ivPas1.setImageResource(R.drawable.pas2empty);
                    }else{
                        ivPas1.setVisibility(View.VISIBLE);
                        ivPas1.setImageResource(R.drawable.pas3empty);
                    }

                    tiempoEspera[0] = new CountDownTimer (TiempoEspera, 100) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            // Código con cada tic del timer
                        }

                        @Override
                        public void onFinish() {
                            // Código cuando finalice el timer vuelve invisible el pasaporte y pone el estado de la mesa a 0 si no le han dado antes
                            if (estados[0] != 0 && estados[0] != 4) {
                                estados[0] = 0;
                                ivPas1.setVisibility(View.INVISIBLE);
                                sinAtender++;
                                combo=0;
                            }
                        }
                    }.start();

                    break;

                case 1:

                    if(numPas==1){
                        ivPas2.setVisibility(View.VISIBLE);
                        ivPas2.setImageResource(R.drawable.pas1empty);
                    }else if(numPas==2){
                        ivPas2.setVisibility(View.VISIBLE);
                        ivPas2.setImageResource(R.drawable.pas2empty);
                    }else{
                        ivPas2.setVisibility(View.VISIBLE);
                        ivPas2.setImageResource(R.drawable.pas3empty);
                    }

                    tiempoEspera[1] = new CountDownTimer (TiempoEspera, 100) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            // Código con cada tic del timer
                        }

                        @Override
                        public void onFinish() {
                            // Código cuando finalice el timer vuelve invisible el pasaporte y pone el estado de la mesa a 0 si no le han dado antes
                            if (estados[1] != 0 && estados[1] != 4) {
                                estados[0] = 0;
                                ivPas2.setVisibility(View.INVISIBLE);
                                sinAtender++;
                                combo=0;
                            }
                        }
                    }.start();

                    break;

                case 2:

                    if(numPas==1){
                        ivPas3.setVisibility(View.VISIBLE);
                        ivPas3.setImageResource(R.drawable.pas1empty);
                    }else if(numPas==2){
                        ivPas3.setVisibility(View.VISIBLE);
                        ivPas3.setImageResource(R.drawable.pas2empty);
                    }else{
                        ivPas3.setVisibility(View.VISIBLE);
                        ivPas3.setImageResource(R.drawable.pas3empty);
                    }

                    tiempoEspera[2] = new CountDownTimer (TiempoEspera, 100) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            // Código con cada tic del timer
                        }

                        @Override
                        public void onFinish() {
                            // Código cuando finalice el timer vuelve invisible el pasaporte y pone el estado de la mesa a 0 si no le han dado antes
                            if (estados[2] != 0 && estados[2] != 4) {
                                estados[2] = 0;
                                ivPas3.setVisibility(View.INVISIBLE);
                                sinAtender++;
                                combo=0;
                            }
                        }
                    }.start();
                    break;
                case 3:
                    if(numPas==1){
                        ivPas4.setVisibility(View.VISIBLE);
                        ivPas4.setImageResource(R.drawable.pas1empty);
                    }else if(numPas==2){
                        ivPas4.setVisibility(View.VISIBLE);
                        ivPas4.setImageResource(R.drawable.pas2empty);
                    }else{
                        ivPas4.setVisibility(View.VISIBLE);
                        ivPas4.setImageResource(R.drawable.pas3empty);
                    }

                    tiempoEspera[3] = new CountDownTimer (TiempoEspera, 100) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            // Código con cada tic del timer
                        }

                        @Override
                        public void onFinish() {
                            // Código cuando finalice el timer vuelve invisible el pasaporte y pone el estado de la mesa a 0 si no le han dado antes
                            if (estados[3] != 0 && estados[3] != 4) {
                                estados[3] = 0;
                                ivPas4.setVisibility(View.INVISIBLE);
                                sinAtender++;
                                combo=0;
                            }
                        }
                    }.start();
                    break;
                case 4:
                    if(numPas==1){
                        ivPas5.setVisibility(View.VISIBLE);
                        ivPas5.setImageResource(R.drawable.pas1empty);
                    }else if(numPas==2){
                        ivPas5.setVisibility(View.VISIBLE);
                        ivPas5.setImageResource(R.drawable.pas2empty);
                    }else{
                        ivPas5.setVisibility(View.VISIBLE);
                        ivPas5.setImageResource(R.drawable.pas3empty);
                    }

                    tiempoEspera[4] = new CountDownTimer (TiempoEspera, 100) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            // Código con cada tic del timer
                        }

                        @Override
                        public void onFinish() {
                            // Código cuando finalice el timer vuelve invisible el pasaporte y pone el estado de la mesa a 0 si no le han dado antes
                            if (estados[4] != 0 && estados[4] != 4) {
                                estados[4] = 0;
                                ivPas5.setVisibility(View.INVISIBLE);
                                sinAtender++;
                                combo=0;
                            }
                        }
                    }.start();
                    break;
                case 5:
                    if(numPas==1){
                        ivPas6.setVisibility(View.VISIBLE);
                        ivPas6.setImageResource(R.drawable.pas1empty);
                    }else if(numPas==2){
                        ivPas6.setVisibility(View.VISIBLE);
                        ivPas6.setImageResource(R.drawable.pas2empty);
                    }else{
                        ivPas6.setVisibility(View.VISIBLE);
                        ivPas6.setImageResource(R.drawable.pas3empty);
                    }

                    tiempoEspera[5] = new CountDownTimer (TiempoEspera, 100) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            // Código con cada tic del timer
                        }

                        @Override
                        public void onFinish() {
                            // Código cuando finalice el timer vuelve invisible el pasaporte y pone el estado de la mesa a 0 si no le han dado antes
                            if (estados[5] != 0 && estados[5] != 4) {
                                estados[5] = 0;
                                ivPas6.setVisibility(View.INVISIBLE);
                                sinAtender++;
                                combo=0;
                            }
                        }
                    }.start();
                    break;
                case 6:
                    if(numPas==1){
                        ivPas7.setVisibility(View.VISIBLE);
                        ivPas7.setImageResource(R.drawable.pas1empty);
                    }else if(numPas==2){
                        ivPas7.setVisibility(View.VISIBLE);
                        ivPas7.setImageResource(R.drawable.pas2empty);
                    }else{
                        ivPas7.setVisibility(View.VISIBLE);
                        ivPas7.setImageResource(R.drawable.pas3empty);
                    }

                    tiempoEspera[6] = new CountDownTimer (TiempoEspera, 100) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            // Código con cada tic del timer
                        }

                        @Override
                        public void onFinish() {
                            // Código cuando finalice el timer vuelve invisible el pasaporte y pone el estado de la mesa a 0 si no le han dado antes
                            if (estados[6] != 0 && estados[6] != 4) {
                                estados[6] = 0;
                                ivPas7.setVisibility(View.INVISIBLE);
                                sinAtender++;
                                combo=0;
                            }
                        }
                    }.start();
                    break;
                case 7:
                    if(numPas==1){
                        ivPas8.setVisibility(View.VISIBLE);
                        ivPas8.setImageResource(R.drawable.pas1empty);
                    }else if(numPas==2){
                        ivPas8.setVisibility(View.VISIBLE);
                        ivPas8.setImageResource(R.drawable.pas2empty);
                    }else{
                        ivPas8.setVisibility(View.VISIBLE);
                        ivPas8.setImageResource(R.drawable.pas3empty);
                    }

                    tiempoEspera[7] = new CountDownTimer (TiempoEspera, 100) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            // Código con cada tic del timer
                        }

                        @Override
                        public void onFinish() {
                            // Código cuando finalice el timer vuelve invisible el pasaporte y pone el estado de la mesa a 0 si no le han dado antes
                            if (estados[7] != 0 && estados[7] != 4) {
                                estados[7] = 0;
                                ivPas8.setVisibility(View.INVISIBLE);
                                sinAtender++;
                                combo=0;
                            }
                        }
                    }.start();
                    break;
                case 8:
                    if(numPas==1){
                        ivPas9.setVisibility(View.VISIBLE);
                        ivPas9.setImageResource(R.drawable.pas1empty);
                    }else if(numPas==2){
                        ivPas9.setVisibility(View.VISIBLE);
                        ivPas9.setImageResource(R.drawable.pas2empty);
                    }else{
                        ivPas9.setVisibility(View.VISIBLE);
                        ivPas9.setImageResource(R.drawable.pas3empty);
                    }

                    tiempoEspera[8] = new CountDownTimer (TiempoEspera, 100) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            // Código con cada tic del timer
                        }

                        @Override
                        public void onFinish() {
                            // Código cuando finalice el timer vuelve invisible el pasaporte y pone el estado de la mesa a 0 si no le han dado antes
                            if (estados[8] != 0 && estados[8] != 4) {
                                estados[8] = 0;
                                ivPas9.setVisibility(View.INVISIBLE);
                                sinAtender++;
                                combo=0;
                            }
                        }
                    }.start();
                    break;
            }

        }
    }

    //Funcion estampa cuando se pueda y actualiza la puntuación
    protected void Estampa(int numMesa){


        if((estados[numMesa]!=0)&&(estados[numMesa]!=4)&&estadoGlobal==1){

            //guardamos con que tinta hemos pulsado para que no pueda cambiar durante la animación;
            tintaAlPulsar=tinta;

            switch (numMesa){
                case 0:

                    //en caso de pasaporte 1
                    if (estados[0] == 1) {
                        //hace que no puedas darle mas de una vez
                        estados[0]=4;
                        //pone la estampa
                        ivPas1.setImageResource(R.drawable.pas1st);
                        sp.play(idStamp, 1, 1, 1, 0, 1);
                        //espera 0.2s para que se vea la estampa
                        espera1[0] = new CountDownTimer(100, 100) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                // Código con cada tic del timer
                            }

                            @Override
                            public void onFinish() {
                                if (tintaAlPulsar == 1) {
                                    ivPas1.setImageResource(R.drawable.pas1v);
                                    puntuacion ++;
                                    combo++;
                                    legalesAceptados++;
                                    sp.play(idAcierto, 1, 1, 1, 0, 1);
                                } else if (tintaAlPulsar == 2) {
                                    ivPas1.setImageResource(R.drawable.pas1nv);
                                    puntuacion --;
                                    combo=0;
                                    legalesRechazados++;
                                    sp.play(idFallo, 1, 1, 1, 0, 1);
                                }
                                ActualizaPuntuacion();

                                //segunda espera para que se vea el sello
                                espera2[0] = new CountDownTimer(500, 100) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        // Código con cada tic del timer
                                    }

                                    @Override
                                    public void onFinish() {
                                        ivPas1.setVisibility(View.INVISIBLE);
                                        tiempoEspera[0].cancel();
                                        estados[0] = 0;
                                    }
                                }.start();
                            }
                        }.start();
                        //si el pasaporte es el 2
                    } else if (estados[0] == 2) {
                        estados[0]=4;
                        //pone la estampa
                        ivPas1.setImageResource(R.drawable.pas2st);
                        sp.play(idStamp, 1, 1, 1, 0, 1);
                        //espera 0.2s para que se vea la estampa
                        espera1[0] = new CountDownTimer(100, 100) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                // Código con cada tic del timer
                            }

                            @Override
                            public void onFinish() {
                                if (tintaAlPulsar == 1) {
                                    ivPas1.setImageResource(R.drawable.pas2v);
                                    puntuacion ++;
                                    combo++;
                                    legalesAceptados++;
                                    sp.play(idAcierto, 1, 1, 1, 0, 1);
                                } else if (tintaAlPulsar == 2) {
                                    ivPas1.setImageResource(R.drawable.pas2nv);
                                    puntuacion --;
                                    combo=0;
                                    legalesRechazados++;
                                    sp.play(idFallo, 1, 1, 1, 0, 1);
                                }
                                ActualizaPuntuacion();
                                //segunda espera para que se vea el sello

                                espera2[0]= new CountDownTimer(500, 100) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        // Código con cada tic del timer
                                    }

                                    @Override
                                    public void onFinish() {
                                        ivPas1.setVisibility(View.INVISIBLE);
                                        tiempoEspera[0].cancel();
                                        estados[0] = 0;
                                    }
                                }.start();
                            }
                        }.start();

                        //pasaporte 3
                    } else if(estados[0]==3) {
                        estados[0]=4;
                        //pone la estampa
                        ivPas1.setImageResource(R.drawable.pas3st);
                        sp.play(idStamp, 1, 1, 1, 0, 1);
                        //espera 0.2s para que se vea la estampa

                        espera1[0] = new CountDownTimer(100, 100) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                // Código con cada tic del timer
                            }

                            @Override
                            public void onFinish() {
                                if (tintaAlPulsar == 1) {
                                    ivPas1.setImageResource(R.drawable.pas3v);
                                    puntuacion --;
                                    combo=0;
                                    ilegalesAceptados++;
                                    sp.play(idFallo, 1, 1, 1, 0, 1);
                                } else if (tintaAlPulsar == 2) {
                                    ivPas1.setImageResource(R.drawable.pas3nv);
                                    puntuacion ++;
                                    combo++;
                                    ilegalesRechazados++;
                                    sp.play(idAcierto, 1, 1, 1, 0, 1);
                                }
                                ActualizaPuntuacion();
                                //segunda espera para que se vea el sello

                                espera2[0] = new CountDownTimer(500, 100) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        // Código con cada tic del timer
                                    }

                                    @Override
                                    public void onFinish() {
                                        ivPas1.setVisibility(View.INVISIBLE);
                                        tiempoEspera[0].cancel();
                                        estados[0] = 0;
                                    }
                                }.start();
                            }
                        }.start();

                    }
                    break;
                case 1:
                    //en caso de pasaporte 1
                    if (estados[1] == 1) {
                        //hace que no puedas darle mas de una vez
                        estados[1]=4;
                        //pone la estampa
                        ivPas2.setImageResource(R.drawable.pas1st);
                        sp.play(idStamp, 1, 1, 1, 0, 1);
                        //espera 0.2s para que se vea la estampa
                        espera1[1] = new CountDownTimer(100, 100) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                // Código con cada tic del timer
                            }

                            @Override
                            public void onFinish() {
                                if (tintaAlPulsar == 1) {
                                    ivPas2.setImageResource(R.drawable.pas1v);
                                    puntuacion ++;
                                    legalesAceptados++;
                                    combo++;
                                    sp.play(idAcierto, 1, 1, 1, 0, 1);
                                } else if (tintaAlPulsar == 2) {
                                    ivPas2.setImageResource(R.drawable.pas1nv);
                                    puntuacion --;
                                    legalesRechazados++;
                                    combo=0;
                                    sp.play(idFallo, 1, 1, 1, 0, 1);
                                }
                                ActualizaPuntuacion();

                                //segunda espera para que se vea el sello
                                espera2[1] = new CountDownTimer(500, 100) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        // Código con cada tic del timer
                                    }

                                    @Override
                                    public void onFinish() {
                                        ivPas2.setVisibility(View.INVISIBLE);
                                        tiempoEspera[1].cancel();
                                        estados[1] = 0;
                                    }
                                }.start();
                            }
                        }.start();
                        //si el pasaporte es el 2
                    } else if (estados[1] == 2) {
                        estados[1]=4;
                        //pone la estampa
                        ivPas2.setImageResource(R.drawable.pas2st);
                        sp.play(idStamp, 1, 1, 1, 0, 1);
                        //espera 0.2s para que se vea la estampa
                        espera1[1] = new CountDownTimer(100, 100) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                // Código con cada tic del timer
                            }

                            @Override
                            public void onFinish() {
                                if (tintaAlPulsar == 1) {
                                    ivPas2.setImageResource(R.drawable.pas2v);
                                    puntuacion ++;
                                    legalesAceptados++;
                                    combo++;
                                    sp.play(idAcierto, 1, 1, 1, 0, 1);
                                } else if (tintaAlPulsar == 2) {
                                    ivPas2.setImageResource(R.drawable.pas2nv);
                                    puntuacion --;
                                    legalesRechazados++;
                                    combo=0;
                                    sp.play(idFallo, 1, 1, 1, 0, 1);
                                }
                                ActualizaPuntuacion();
                                //segunda espera para que se vea el sello

                                espera2[1]= new CountDownTimer(500, 100) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        // Código con cada tic del timer
                                    }

                                    @Override
                                    public void onFinish() {
                                        ivPas2.setVisibility(View.INVISIBLE);
                                        tiempoEspera[1].cancel();
                                        estados[1] = 0;
                                    }
                                }.start();
                            }
                        }.start();

                        //pasaporte 3
                    } else if(estados[1]==3) {
                        estados[1]=4;
                        //pone la estampa
                        ivPas2.setImageResource(R.drawable.pas3st);
                        sp.play(idStamp, 1, 1, 1, 0, 1);
                        //espera 0.2s para que se vea la estampa

                        espera1[1] = new CountDownTimer(100, 100) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                // Código con cada tic del timer
                            }

                            @Override
                            public void onFinish() {
                                if (tintaAlPulsar == 1) {
                                    ivPas2.setImageResource(R.drawable.pas3v);
                                    puntuacion --;
                                    ilegalesAceptados++;
                                    combo=0;
                                    sp.play(idFallo, 1, 1, 1, 0, 1);
                                } else if (tintaAlPulsar == 2) {
                                    ivPas2.setImageResource(R.drawable.pas3nv);
                                    puntuacion += 1;
                                    ilegalesRechazados++;
                                    combo++;
                                    sp.play(idAcierto, 1, 1, 1, 0, 1);
                                }
                                ActualizaPuntuacion();
                                //segunda espera para que se vea el sello

                                espera2[1] = new CountDownTimer(500, 100) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        // Código con cada tic del timer
                                    }

                                    @Override
                                    public void onFinish() {
                                        ivPas2.setVisibility(View.INVISIBLE);
                                        tiempoEspera[1].cancel();
                                        estados[1] = 0;
                                    }
                                }.start();
                            }
                        }.start();

                    }
                    break;

                case 2:

                    //en caso de pasaporte 1
                    if (estados[2] == 1) {
                        //hace que no puedas darle mas de una vez
                        estados[2]=4;
                        //pone la estampa
                        ivPas3.setImageResource(R.drawable.pas1st);
                        sp.play(idStamp, 1, 1, 1, 0, 1);
                        //espera 0.2s para que se vea la estampa
                        espera1[2] = new CountDownTimer(100, 100) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                // Código con cada tic del timer
                            }

                            @Override
                            public void onFinish() {
                                if (tintaAlPulsar == 1) {
                                    ivPas3.setImageResource(R.drawable.pas1v);
                                    puntuacion += 1;
                                    legalesAceptados++;
                                    combo++;
                                    sp.play(idAcierto, 1, 1, 1, 0, 1);
                                } else if (tintaAlPulsar == 2) {
                                    ivPas3.setImageResource(R.drawable.pas1nv);
                                    puntuacion -= 1;
                                    legalesRechazados++;
                                    combo=0;
                                    sp.play(idFallo, 1, 1, 1, 0, 1);
                                }
                                ActualizaPuntuacion();

                                //segunda espera para que se vea el sello
                                espera2[2] = new CountDownTimer(500, 100) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        // Código con cada tic del timer
                                    }

                                    @Override
                                    public void onFinish() {
                                        ivPas3.setVisibility(View.INVISIBLE);
                                        tiempoEspera[2].cancel();
                                        estados[2] = 0;
                                    }
                                }.start();
                            }
                        }.start();
                        //si el pasaporte es el 2
                    } else if (estados[2] == 2) {
                        estados[2]=4;
                        //pone la estampa
                        ivPas3.setImageResource(R.drawable.pas2st);
                        sp.play(idStamp, 1, 1, 1, 0, 1);
                        //espera 0.2s para que se vea la estampa
                        espera1[2] = new CountDownTimer(100, 100) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                // Código con cada tic del timer
                            }

                            @Override
                            public void onFinish() {
                                if (tintaAlPulsar == 1) {
                                    ivPas3.setImageResource(R.drawable.pas2v);
                                    puntuacion += 1;
                                    legalesAceptados++;
                                    combo++;
                                    sp.play(idAcierto, 1, 1, 1, 0, 1);
                                } else if (tintaAlPulsar == 2) {
                                    ivPas3.setImageResource(R.drawable.pas2nv);
                                    puntuacion -= 1;
                                    legalesRechazados++;
                                    combo=0;
                                    sp.play(idFallo, 1, 1, 1, 0, 1);
                                }
                                ActualizaPuntuacion();
                                //segunda espera para que se vea el sello

                                espera2[2]= new CountDownTimer(500, 100) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        // Código con cada tic del timer
                                    }

                                    @Override
                                    public void onFinish() {
                                        ivPas3.setVisibility(View.INVISIBLE);
                                        tiempoEspera[2].cancel();
                                        estados[2] = 0;
                                    }
                                }.start();
                            }
                        }.start();

                        //pasaporte 3
                    } else if(estados[2]==3) {
                        estados[2]=4;
                        //pone la estampa
                        ivPas3.setImageResource(R.drawable.pas3st);
                        sp.play(idStamp, 1, 1, 1, 0, 1);
                        //espera 0.2s para que se vea la estampa

                        espera1[2] = new CountDownTimer(100, 100) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                // Código con cada tic del timer
                            }

                            @Override
                            public void onFinish() {
                                if (tintaAlPulsar == 1) {
                                    ivPas3.setImageResource(R.drawable.pas3v);
                                    puntuacion -= 1;
                                    ilegalesAceptados++;
                                    combo=0;
                                    sp.play(idFallo, 1, 1, 1, 0, 1);
                                } else if (tintaAlPulsar == 2) {
                                    ivPas3.setImageResource(R.drawable.pas3nv);
                                    puntuacion += 1;
                                    ilegalesRechazados++;
                                    combo++;
                                    sp.play(idAcierto, 1, 1, 1, 0, 1);
                                }
                                ActualizaPuntuacion();
                                //segunda espera para que se vea el sello

                                espera2[2] = new CountDownTimer(500, 100) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        // Código con cada tic del timer
                                    }

                                    @Override
                                    public void onFinish() {
                                        ivPas3.setVisibility(View.INVISIBLE);
                                        tiempoEspera[2].cancel();
                                        estados[2] = 0;
                                    }
                                }.start();
                            }
                        }.start();

                    }
                    break;

                case 3:

                    //en caso de pasaporte 1
                    if (estados[3] == 1) {
                        //hace que no puedas darle mas de una vez
                        estados[3]=4;
                        //pone la estampa
                        ivPas4.setImageResource(R.drawable.pas1st);
                        sp.play(idStamp, 1, 1, 1, 0, 1);
                        //espera 0.2s para que se vea la estampa
                        espera1[3] = new CountDownTimer(100, 100) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                // Código con cada tic del timer
                            }

                            @Override
                            public void onFinish() {
                                if (tintaAlPulsar == 1) {
                                    ivPas4.setImageResource(R.drawable.pas1v);
                                    puntuacion += 1;
                                    legalesAceptados++;
                                    combo++;
                                    sp.play(idAcierto, 1, 1, 1, 0, 1);
                                } else if (tintaAlPulsar == 2) {
                                    ivPas4.setImageResource(R.drawable.pas1nv);
                                    puntuacion -= 1;
                                    legalesRechazados++;
                                    combo=0;
                                    sp.play(idFallo, 1, 1, 1, 0, 1);
                                }
                                ActualizaPuntuacion();

                                //segunda espera para que se vea el sello
                                espera2[3] = new CountDownTimer(500, 100) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        // Código con cada tic del timer
                                    }

                                    @Override
                                    public void onFinish() {
                                        ivPas4.setVisibility(View.INVISIBLE);
                                        tiempoEspera[3].cancel();
                                        estados[3] = 0;
                                    }
                                }.start();
                            }
                        }.start();
                        //si el pasaporte es el 2
                    } else if (estados[3] == 2) {
                        estados[3]=4;
                        //pone la estampa
                        ivPas4.setImageResource(R.drawable.pas2st);
                        sp.play(idStamp, 1, 1, 1, 0, 1);
                        //espera 0.2s para que se vea la estampa
                        espera1[3] = new CountDownTimer(100, 100) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                // Código con cada tic del timer
                            }

                            @Override
                            public void onFinish() {
                                if (tintaAlPulsar == 1) {
                                    ivPas4.setImageResource(R.drawable.pas2v);
                                    puntuacion += 1;
                                    legalesAceptados++;
                                    combo++;
                                    sp.play(idAcierto, 1, 1, 1, 0, 1);
                                } else if (tintaAlPulsar == 2) {
                                    ivPas4.setImageResource(R.drawable.pas2nv);
                                    puntuacion -= 1;
                                    legalesRechazados++;
                                    combo=0;
                                    sp.play(idFallo, 1, 1, 1, 0, 1);
                                }
                                ActualizaPuntuacion();
                                //segunda espera para que se vea el sello

                                espera2[3]= new CountDownTimer(500, 100) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        // Código con cada tic del timer
                                    }

                                    @Override
                                    public void onFinish() {
                                        ivPas4.setVisibility(View.INVISIBLE);
                                        tiempoEspera[3].cancel();
                                        estados[3] = 0;
                                    }
                                }.start();
                            }
                        }.start();

                        //pasaporte 3
                    } else if(estados[3]==3) {
                        estados[3]=4;
                        //pone la estampa
                        ivPas4.setImageResource(R.drawable.pas3st);
                        sp.play(idStamp, 1, 1, 1, 0, 1);
                        //espera 0.2s para que se vea la estampa

                        espera1[3] = new CountDownTimer(100, 100) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                // Código con cada tic del timer
                            }

                            @Override
                            public void onFinish() {
                                if (tintaAlPulsar == 1) {
                                    ivPas4.setImageResource(R.drawable.pas3v);
                                    puntuacion -= 1;
                                    ilegalesAceptados++;
                                    combo=0;
                                    sp.play(idFallo, 1, 1, 1, 0, 1);
                                } else if (tintaAlPulsar == 2) {
                                    ivPas4.setImageResource(R.drawable.pas3nv);
                                    puntuacion += 1;
                                    ilegalesRechazados++;
                                    combo++;
                                    sp.play(idAcierto, 1, 1, 1, 0, 1);
                                }
                                ActualizaPuntuacion();
                                //segunda espera para que se vea el sello

                                espera2[3] = new CountDownTimer(500, 100) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        // Código con cada tic del timer
                                    }

                                    @Override
                                    public void onFinish() {
                                        ivPas4.setVisibility(View.INVISIBLE);
                                        tiempoEspera[3].cancel();
                                        estados[3] = 0;
                                    }
                                }.start();
                            }
                        }.start();

                    }

                    break;


                case 4:

                    //en caso de pasaporte 1
                    if (estados[4] == 1) {
                        //hace que no puedas darle mas de una vez
                        estados[4]=4;
                        //pone la estampa
                        ivPas5.setImageResource(R.drawable.pas1st);
                        sp.play(idStamp, 1, 1, 1, 0, 1);
                        //espera 0.2s para que se vea la estampa
                        espera1[4] = new CountDownTimer(100, 100) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                // Código con cada tic del timer
                            }

                            @Override
                            public void onFinish() {
                                if (tintaAlPulsar == 1) {
                                    ivPas5.setImageResource(R.drawable.pas1v);
                                    puntuacion += 1;
                                    legalesAceptados++;
                                    combo++;
                                    sp.play(idAcierto, 1, 1, 1, 0, 1);
                                } else if (tintaAlPulsar == 2) {
                                    ivPas5.setImageResource(R.drawable.pas1nv);
                                    puntuacion -= 1;
                                    legalesRechazados++;
                                    combo=0;
                                    sp.play(idFallo, 1, 1, 1, 0, 1);
                                }
                                ActualizaPuntuacion();

                                //segunda espera para que se vea el sello
                                espera2[4] = new CountDownTimer(500, 100) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        // Código con cada tic del timer
                                    }

                                    @Override
                                    public void onFinish() {
                                        ivPas5.setVisibility(View.INVISIBLE);
                                        tiempoEspera[4].cancel();
                                        estados[4] = 0;
                                    }
                                }.start();
                            }
                        }.start();
                        //si el pasaporte es el 2
                    } else if (estados[4] == 2) {
                        estados[4]=4;
                        //pone la estampa
                        ivPas5.setImageResource(R.drawable.pas2st);
                        sp.play(idStamp, 1, 1, 1, 0, 1);
                        //espera 0.2s para que se vea la estampa
                        espera1[4] = new CountDownTimer(100, 100) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                // Código con cada tic del timer
                            }

                            @Override
                            public void onFinish() {
                                if (tintaAlPulsar == 1) {
                                    ivPas5.setImageResource(R.drawable.pas2v);
                                    puntuacion += 1;
                                    legalesAceptados++;
                                    combo++;
                                    sp.play(idAcierto, 1, 1, 1, 0, 1);
                                } else if (tintaAlPulsar == 2) {
                                    ivPas5.setImageResource(R.drawable.pas2nv);
                                    puntuacion -= 1;
                                    legalesRechazados++;
                                    combo=0;
                                    sp.play(idFallo, 1, 1, 1, 0, 1);
                                }
                                ActualizaPuntuacion();
                                //segunda espera para que se vea el sello

                                espera2[4]= new CountDownTimer(500, 100) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        // Código con cada tic del timer
                                    }

                                    @Override
                                    public void onFinish() {
                                        ivPas5.setVisibility(View.INVISIBLE);
                                        tiempoEspera[4].cancel();
                                        estados[4] = 0;
                                    }
                                }.start();
                            }
                        }.start();

                        //pasaporte 3
                    } else if(estados[4]==3) {
                        estados[4]=4;
                        //pone la estampa
                        ivPas5.setImageResource(R.drawable.pas3st);
                        sp.play(idStamp, 1, 1, 1, 0, 1);
                        //espera 0.2s para que se vea la estampa

                        espera1[4] = new CountDownTimer(100, 100) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                // Código con cada tic del timer
                            }

                            @Override
                            public void onFinish() {
                                if (tintaAlPulsar == 1) {
                                    ivPas5.setImageResource(R.drawable.pas3v);
                                    puntuacion -= 1;
                                    ilegalesAceptados++;
                                    combo=0;
                                    sp.play(idFallo, 1, 1, 1, 0, 1);
                                } else if (tintaAlPulsar == 2) {
                                    ivPas5.setImageResource(R.drawable.pas3nv);
                                    puntuacion += 1;
                                    ilegalesRechazados++;
                                    combo++;
                                    sp.play(idAcierto, 1, 1, 1, 0, 1);
                                }
                                ActualizaPuntuacion();
                                //segunda espera para que se vea el sello

                                espera2[4] = new CountDownTimer(500, 100) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        // Código con cada tic del timer
                                    }

                                    @Override
                                    public void onFinish() {
                                        ivPas5.setVisibility(View.INVISIBLE);
                                        tiempoEspera[4].cancel();
                                        estados[4] = 0;
                                    }
                                }.start();
                            }
                        }.start();

                    }


                    break;

                case 5:


                    //en caso de pasaporte 1
                    if (estados[5] == 1) {
                        //hace que no puedas darle mas de una vez
                        estados[5]=4;
                        //pone la estampa
                        ivPas6.setImageResource(R.drawable.pas1st);
                        sp.play(idStamp, 1, 1, 1, 0, 1);
                        //espera 0.2s para que se vea la estampa
                        espera1[5] = new CountDownTimer(100, 100) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                // Código con cada tic del timer
                            }

                            @Override
                            public void onFinish() {
                                if (tintaAlPulsar == 1) {
                                    ivPas6.setImageResource(R.drawable.pas1v);
                                    puntuacion += 1;
                                    legalesAceptados++;
                                    combo++;
                                    sp.play(idAcierto, 1, 1, 1, 0, 1);
                                } else if (tintaAlPulsar == 2) {
                                    ivPas6.setImageResource(R.drawable.pas1nv);
                                    puntuacion -= 1;
                                    legalesRechazados++;
                                    combo=0;
                                    sp.play(idFallo, 1, 1, 1, 0, 1);
                                }
                                ActualizaPuntuacion();

                                //segunda espera para que se vea el sello
                                espera2[5] = new CountDownTimer(500, 100) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        // Código con cada tic del timer
                                    }

                                    @Override
                                    public void onFinish() {
                                        ivPas6.setVisibility(View.INVISIBLE);
                                        tiempoEspera[5].cancel();
                                        estados[5] = 0;
                                    }
                                }.start();
                            }
                        }.start();
                        //si el pasaporte es el 2
                    } else if (estados[5] == 2) {
                        estados[5]=4;
                        //pone la estampa
                        ivPas6.setImageResource(R.drawable.pas2st);
                        sp.play(idStamp, 1, 1, 1, 0, 1);
                        //espera 0.2s para que se vea la estampa
                        espera1[5] = new CountDownTimer(100, 100) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                // Código con cada tic del timer
                            }

                            @Override
                            public void onFinish() {
                                if (tintaAlPulsar == 1) {
                                    ivPas6.setImageResource(R.drawable.pas2v);
                                    puntuacion += 1;
                                    legalesAceptados++;
                                    combo++;
                                    sp.play(idAcierto, 1, 1, 1, 0, 1);
                                } else if (tintaAlPulsar == 2) {
                                    ivPas6.setImageResource(R.drawable.pas2nv);
                                    puntuacion -= 1;
                                    legalesRechazados++;
                                    combo=0;
                                    sp.play(idFallo, 1, 1, 1, 0, 1);
                                }
                                ActualizaPuntuacion();
                                //segunda espera para que se vea el sello

                                espera2[5]= new CountDownTimer(500, 100) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        // Código con cada tic del timer
                                    }

                                    @Override
                                    public void onFinish() {
                                        ivPas6.setVisibility(View.INVISIBLE);
                                        tiempoEspera[5].cancel();
                                        estados[5] = 0;
                                    }
                                }.start();
                            }
                        }.start();

                        //pasaporte 3
                    } else if(estados[5]==3) {
                        estados[5]=4;
                        //pone la estampa
                        ivPas6.setImageResource(R.drawable.pas3st);
                        sp.play(idStamp, 1, 1, 1, 0, 1);
                        //espera 0.2s para que se vea la estampa

                        espera1[5] = new CountDownTimer(100, 100) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                // Código con cada tic del timer
                            }

                            @Override
                            public void onFinish() {
                                if (tintaAlPulsar == 1) {
                                    ivPas6.setImageResource(R.drawable.pas3v);
                                    puntuacion -= 1;
                                    ilegalesAceptados++;
                                    combo=0;
                                    sp.play(idFallo, 1, 1, 1, 0, 1);
                                } else if (tintaAlPulsar == 2) {
                                    ivPas6.setImageResource(R.drawable.pas3nv);
                                    puntuacion += 1;
                                    ilegalesRechazados++;
                                    combo++;
                                    sp.play(idAcierto, 1, 1, 1, 0, 1);
                                }
                                ActualizaPuntuacion();
                                //segunda espera para que se vea el sello

                                espera2[5] = new CountDownTimer(500, 100) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        // Código con cada tic del timer
                                    }

                                    @Override
                                    public void onFinish() {
                                        ivPas6.setVisibility(View.INVISIBLE);
                                        tiempoEspera[5].cancel();
                                        estados[5] = 0;
                                    }
                                }.start();
                            }
                        }.start();

                    }

                    break;

                case 6:

                    //en caso de pasaporte 1
                    if (estados[6] == 1) {
                        //hace que no puedas darle mas de una vez
                        estados[6]=4;
                        //pone la estampa
                        ivPas7.setImageResource(R.drawable.pas1st);
                        sp.play(idStamp, 1, 1, 1, 0, 1);
                        //espera 0.2s para que se vea la estampa
                        espera1[6] = new CountDownTimer(100, 100) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                // Código con cada tic del timer
                            }

                            @Override
                            public void onFinish() {
                                if (tintaAlPulsar == 1) {
                                    ivPas7.setImageResource(R.drawable.pas1v);
                                    puntuacion += 1;
                                    legalesAceptados++;
                                    combo++;
                                    sp.play(idAcierto, 1, 1, 1, 0, 1);
                                } else if (tintaAlPulsar == 2) {
                                    ivPas7.setImageResource(R.drawable.pas1nv);
                                    puntuacion -= 1;
                                    legalesRechazados++;
                                    combo=0;
                                    sp.play(idFallo, 1, 1, 1, 0, 1);
                                }
                                ActualizaPuntuacion();

                                //segunda espera para que se vea el sello
                                espera2[6] = new CountDownTimer(500, 100) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        // Código con cada tic del timer
                                    }

                                    @Override
                                    public void onFinish() {
                                        ivPas7.setVisibility(View.INVISIBLE);
                                        tiempoEspera[6].cancel();
                                        estados[6] = 0;
                                    }
                                }.start();
                            }
                        }.start();
                        //si el pasaporte es el 2
                    } else if (estados[6] == 2) {
                        estados[6]=4;
                        //pone la estampa
                        ivPas7.setImageResource(R.drawable.pas2st);
                        sp.play(idStamp, 1, 1, 1, 0, 1);
                        //espera 0.2s para que se vea la estampa
                        espera1[6] = new CountDownTimer(100, 100) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                // Código con cada tic del timer
                            }

                            @Override
                            public void onFinish() {
                                if (tintaAlPulsar == 1) {
                                    ivPas7.setImageResource(R.drawable.pas2v);
                                    puntuacion += 1;
                                    legalesAceptados++;
                                    combo++;
                                    sp.play(idAcierto, 1, 1, 1, 0, 1);
                                } else if (tintaAlPulsar == 2) {
                                    ivPas7.setImageResource(R.drawable.pas2nv);
                                    puntuacion -= 1;
                                    legalesRechazados++;
                                    combo=0;
                                    sp.play(idFallo, 1, 1, 1, 0, 1);
                                }
                                ActualizaPuntuacion();
                                //segunda espera para que se vea el sello

                                espera2[6]= new CountDownTimer(500, 100) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        // Código con cada tic del timer
                                    }

                                    @Override
                                    public void onFinish() {
                                        ivPas7.setVisibility(View.INVISIBLE);
                                        tiempoEspera[6].cancel();
                                        estados[6] = 0;
                                    }
                                }.start();
                            }
                        }.start();

                        //pasaporte 3
                    } else if(estados[6]==3) {
                        estados[6]=4;
                        //pone la estampa
                        ivPas7.setImageResource(R.drawable.pas3st);
                        sp.play(idStamp, 1, 1, 1, 0, 1);
                        //espera 0.2s para que se vea la estampa

                        espera1[6] = new CountDownTimer(100, 100) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                // Código con cada tic del timer
                            }

                            @Override
                            public void onFinish() {
                                if (tintaAlPulsar == 1) {
                                    ivPas7.setImageResource(R.drawable.pas3v);
                                    puntuacion -= 1;
                                    ilegalesAceptados++;
                                    combo=0;
                                    sp.play(idFallo, 1, 1, 1, 0, 1);
                                } else if (tintaAlPulsar == 2) {
                                    ivPas7.setImageResource(R.drawable.pas3nv);
                                    puntuacion += 1;
                                    ilegalesRechazados++;
                                    combo++;
                                    sp.play(idAcierto, 1, 1, 1, 0, 1);
                                }
                                ActualizaPuntuacion();
                                //segunda espera para que se vea el sello

                                espera2[6] = new CountDownTimer(500, 100) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        // Código con cada tic del timer
                                    }

                                    @Override
                                    public void onFinish() {
                                        ivPas7.setVisibility(View.INVISIBLE);
                                        tiempoEspera[6].cancel();
                                        estados[6] = 0;
                                    }
                                }.start();
                            }
                        }.start();

                    }

                    break;

                case 7:

                    //en caso de pasaporte 1
                    if (estados[7] == 1) {
                        //hace que no puedas darle mas de una vez
                        estados[7]=4;
                        //pone la estampa
                        ivPas8.setImageResource(R.drawable.pas1st);
                        sp.play(idStamp, 1, 1, 1, 0, 1);
                        //espera 0.2s para que se vea la estampa
                        espera1[7] = new CountDownTimer(100, 100) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                // Código con cada tic del timer
                            }

                            @Override
                            public void onFinish() {
                                if (tintaAlPulsar == 1) {
                                    ivPas8.setImageResource(R.drawable.pas1v);
                                    puntuacion += 1;
                                    legalesAceptados++;
                                    combo++;
                                    sp.play(idAcierto, 1, 1, 1, 0, 1);
                                } else if (tintaAlPulsar == 2) {
                                    ivPas8.setImageResource(R.drawable.pas1nv);
                                    puntuacion -= 1;
                                    legalesRechazados++;
                                    combo=0;
                                    sp.play(idFallo, 1, 1, 1, 0, 1);
                                }
                                ActualizaPuntuacion();

                                //segunda espera para que se vea el sello
                                espera2[7] = new CountDownTimer(500, 100) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        // Código con cada tic del timer
                                    }

                                    @Override
                                    public void onFinish() {
                                        ivPas8.setVisibility(View.INVISIBLE);
                                        tiempoEspera[7].cancel();
                                        estados[7] = 0;
                                    }
                                }.start();
                            }
                        }.start();
                        //si el pasaporte es el 2
                    } else if (estados[7] == 2) {
                        estados[7]=4;
                        //pone la estampa
                        ivPas8.setImageResource(R.drawable.pas2st);
                        sp.play(idStamp, 1, 1, 1, 0, 1);
                        //espera 0.2s para que se vea la estampa
                        espera1[7] = new CountDownTimer(100, 100) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                // Código con cada tic del timer
                            }

                            @Override
                            public void onFinish() {
                                if (tintaAlPulsar == 1) {
                                    ivPas8.setImageResource(R.drawable.pas2v);
                                    puntuacion += 1;
                                    legalesAceptados++;
                                    combo++;
                                    sp.play(idAcierto, 1, 1, 1, 0, 1);
                                } else if (tintaAlPulsar == 2) {
                                    ivPas8.setImageResource(R.drawable.pas2nv);
                                    puntuacion -= 1;
                                    legalesRechazados++;
                                    combo=0;
                                    sp.play(idFallo, 1, 1, 1, 0, 1);
                                }
                                ActualizaPuntuacion();
                                //segunda espera para que se vea el sello

                                espera2[7]= new CountDownTimer(500, 100) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        // Código con cada tic del timer
                                    }

                                    @Override
                                    public void onFinish() {
                                        ivPas8.setVisibility(View.INVISIBLE);
                                        tiempoEspera[7].cancel();
                                        estados[7] = 0;
                                    }
                                }.start();
                            }
                        }.start();

                        //pasaporte 3
                    } else if(estados[7]==3) {
                        estados[7]=4;
                        //pone la estampa
                        ivPas8.setImageResource(R.drawable.pas3st);
                        sp.play(idStamp, 1, 1, 1, 0, 1);
                        //espera 0.2s para que se vea la estampa

                        espera1[7] = new CountDownTimer(100, 100) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                // Código con cada tic del timer
                            }

                            @Override
                            public void onFinish() {
                                if (tintaAlPulsar == 1) {
                                    ivPas8.setImageResource(R.drawable.pas3v);
                                    puntuacion -= 1;
                                    ilegalesAceptados++;
                                    combo=0;
                                    sp.play(idFallo, 1, 1, 1, 0, 1);
                                } else if (tintaAlPulsar == 2) {
                                    ivPas8.setImageResource(R.drawable.pas3nv);
                                    puntuacion += 1;
                                    ilegalesRechazados++;
                                    combo++;
                                    sp.play(idAcierto, 1, 1, 1, 0, 1);
                                }
                                ActualizaPuntuacion();
                                //segunda espera para que se vea el sello

                                espera2[7] = new CountDownTimer(500, 100) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        // Código con cada tic del timer
                                    }

                                    @Override
                                    public void onFinish() {
                                        ivPas8.setVisibility(View.INVISIBLE);
                                        tiempoEspera[7].cancel();
                                        estados[7] = 0;
                                    }
                                }.start();
                            }
                        }.start();

                    }
                    break;

                case 8:

                    //en caso de pasaporte 1
                    if (estados[8] == 1) {
                        //hace que no puedas darle mas de una vez
                        estados[8]=4;
                        //pone la estampa
                        ivPas9.setImageResource(R.drawable.pas1st);
                        sp.play(idStamp, 1, 1, 1, 0, 1);
                        //espera 0.2s para que se vea la estampa
                        espera1[8] = new CountDownTimer(100, 100) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                // Código con cada tic del timer
                            }

                            @Override
                            public void onFinish() {
                                if (tintaAlPulsar == 1) {
                                    ivPas9.setImageResource(R.drawable.pas1v);
                                    puntuacion += 1;
                                    legalesAceptados++;
                                    combo++;
                                    sp.play(idAcierto, 1, 1, 1, 0, 1);
                                } else if (tintaAlPulsar == 2) {
                                    ivPas9.setImageResource(R.drawable.pas1nv);
                                    puntuacion -= 1;
                                    legalesRechazados++;
                                    combo=0;
                                    sp.play(idFallo, 1, 1, 1, 0, 1);
                                }
                                ActualizaPuntuacion();

                                //segunda espera para que se vea el sello
                                espera2[8] = new CountDownTimer(500, 100) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        // Código con cada tic del timer
                                    }

                                    @Override
                                    public void onFinish() {
                                        ivPas9.setVisibility(View.INVISIBLE);
                                        tiempoEspera[8].cancel();
                                        estados[8] = 0;
                                    }
                                }.start();
                            }
                        }.start();
                        //si el pasaporte es el 2
                    } else if (estados[8] == 2) {
                        estados[8]=4;
                        //pone la estampa
                        ivPas9.setImageResource(R.drawable.pas2st);
                        sp.play(idStamp, 1, 1, 1, 0, 1);
                        //espera 0.2s para que se vea la estampa
                        espera1[8] = new CountDownTimer(100, 100) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                // Código con cada tic del timer
                            }

                            @Override
                            public void onFinish() {
                                if (tintaAlPulsar == 1) {
                                    ivPas9.setImageResource(R.drawable.pas2v);
                                    puntuacion += 1;
                                    legalesAceptados++;
                                    combo++;
                                    sp.play(idAcierto, 1, 1, 1, 0, 1);
                                } else if (tintaAlPulsar == 2) {
                                    ivPas9.setImageResource(R.drawable.pas2nv);
                                    puntuacion -= 1;
                                    legalesRechazados++;
                                    combo=0;
                                    sp.play(idFallo, 1, 1, 1, 0, 1);
                                }
                                ActualizaPuntuacion();
                                //segunda espera para que se vea el sello

                                espera2[8]= new CountDownTimer(500, 100) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        // Código con cada tic del timer
                                    }

                                    @Override
                                    public void onFinish() {
                                        ivPas9.setVisibility(View.INVISIBLE);
                                        tiempoEspera[8].cancel();
                                        estados[8] = 0;
                                    }
                                }.start();
                            }
                        }.start();

                        //pasaporte 3
                    } else if(estados[8]==3) {
                        estados[8]=4;
                        //pone la estampa
                        ivPas9.setImageResource(R.drawable.pas3st);
                        sp.play(idStamp, 1, 1, 1, 0, 1);
                        //espera 0.2s para que se vea la estampa

                        espera1[8] = new CountDownTimer(100, 100) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                // Código con cada tic del timer
                            }

                            @Override
                            public void onFinish() {
                                if (tintaAlPulsar == 1) {
                                    ivPas9.setImageResource(R.drawable.pas3v);
                                    puntuacion -= 1;
                                    ilegalesAceptados++;
                                    combo=0;
                                    sp.play(idFallo, 1, 1, 1, 0, 1);
                                } else if (tintaAlPulsar == 2) {
                                    ivPas9.setImageResource(R.drawable.pas3nv);
                                    puntuacion += 1;
                                    ilegalesRechazados++;
                                    combo++;
                                    sp.play(idAcierto, 1, 1, 1, 0, 1);
                                }
                                ActualizaPuntuacion();
                                //segunda espera para que se vea el sello

                                espera2[8] = new CountDownTimer(500, 100) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        // Código con cada tic del timer
                                    }

                                    @Override
                                    public void onFinish() {
                                        ivPas9.setVisibility(View.INVISIBLE);
                                        tiempoEspera[8].cancel();
                                        estados[8] = 0;
                                    }
                                }.start();
                            }
                        }.start();

                    }
                    break;

            }

            //guarda el combo maximo
            if(combo>combomax){
                combomax=combo;
            }

            }
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_pasaportes);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();


        ivPas1=findViewById(R.id.ivPas1);
        ivPas2=findViewById(R.id.ivPas2);
        ivPas3=findViewById(R.id.ivPas3);
        ivPas4=findViewById(R.id.ivPas4);
        ivPas5=findViewById(R.id.ivPas5);
        ivPas6=findViewById(R.id.ivPas6);
        ivPas7=findViewById(R.id.ivPas7);
        ivPas8=findViewById(R.id.ivPas8);
        ivPas9=findViewById(R.id.ivPas9);
        ivTintaVerde=findViewById(R.id.ivTintaVerde);
        ivTintaRoja=findViewById(R.id.ivTintaRoja);
        tvCountdown=findViewById(R.id.tvCountdown);
        tvPuntuacion=findViewById(R.id.tvPuntuacion);
        btVolver=findViewById(R.id.btVolver);
        ivPortafolio = findViewById(R.id.ivPortafolio);
        tvLegalesAceptados= findViewById(R.id.tvLegalesAceptados);
        tvLegalesRechazados= findViewById(R.id.tvLegalesRechazados);
        tvIlegalesAceptados= findViewById(R.id.tvIlegalesAceptados);
        tvIlegalesRechazados= findViewById(R.id.tvIlegalesRechazados);
        tvCombo= findViewById(R.id.tvCombo);
        tvPuntuacionResumen= findViewById(R.id.tvPuntuacionResumen);
        tvResumen=findViewById(R.id.tvResumen);
        tvRate=findViewById(R.id.tvRate);
        tvSinAtender=findViewById(R.id.tvSinAtender);



        //sonidos
        AudioAttributes audioAttributes = new
                AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION).setContentType(
                AudioAttributes.CONTENT_TYPE_SONIFICATION).build();
        sp = new SoundPool.Builder().setMaxStreams(5).setAudioAttributes(audioAttributes).build();

        idStamp= sp.load(this,R.raw.stamp,1);
        idAcierto= sp.load(this,R.raw.acierto,1);
        idFallo= sp.load(this,R.raw.fallo,1);
        idFinalPartida= sp.load(this,R.raw.finalpartida,1);



        //Hacemos los pasaportes , el boton volver invisibles y el resumen oculto
        OcultaPasyResum();

        //inicializamos las variables
        legalesAceptados=0;
        legalesRechazados=0;
        ilegalesAceptados=0;
        ilegalesRechazados=0;
        sinAtender=0;
        combo=0;
        combomax=0;

        //Establecemos la dificultad

        SharedPreferences opciones= getSharedPreferences("OPCIONESPASAPORTES",MODE_PRIVATE);
        dificultad=opciones.getInt("DIFICULTAD",1);

        if(dificultad==0){
            intervalAparicionPas=2000;
            tiempoDesaparecePas=3000;
        }else if(dificultad==1){
            intervalAparicionPas=1000;
            tiempoDesaparecePas=1500;
        }else if(dificultad==2){
            intervalAparicionPas=500;
            tiempoDesaparecePas=1000;
        }


        //marco la tinta verde que es la seleccionada
        ivTintaVerde.setScaleX(1.3F);
        ivTintaVerde.setScaleY(1.3F);

        //Ponemos el estado Global en 1 (Jugando) y iniciamos el contador
        estadoGlobal=1;
        startTimer();
        startPantTimer();





        ivPas1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Estampa(0);
            }
        });

        ivPas2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Estampa(1);
            }
        });

        ivPas3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Estampa(2);
            }
        });

        ivPas4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Estampa(3);
            }
        });

        ivPas5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Estampa(4);
            }
        });

        ivPas6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Estampa(5);
            }
        });

        ivPas7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Estampa(6);
            }
        });

        ivPas8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Estampa(7);
            }
        });

        ivPas9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Estampa(8);
            }
        });

        ivTintaVerde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tinta=1;
                if(estadoGlobal==1){
                        ivTintaVerde.setScaleX(1.3F);
                        ivTintaVerde.setScaleY(1.3F);
                        ivTintaRoja.setScaleX(1F);
                        ivTintaRoja.setScaleY(1F);
                }


            }
        });

        ivTintaRoja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tinta=2;
                if(estadoGlobal==1) {
                    ivTintaVerde.setScaleX(1F);
                    ivTintaVerde.setScaleY(1F);
                    ivTintaRoja.setScaleX(1.3F);
                    ivTintaRoja.setScaleY(1.3F);
                }
            }
        });


    btVolver.setOnClickListener(new View.OnClickListener() {
        @Override

        public void onClick(View view) {

            MediaPlayer mpMegafoniaSalir= MediaPlayer.create (JuegoPasaportes.this, R.raw.megafoniasalir);
                mpMegafoniaSalir.setVolume(0.75f, 0.75f);
                mpMegafoniaSalir.setLooping(false);
                mpMegafoniaSalir.start();


        while(mpMegafoniaSalir.isPlaying()){
        //Se puede poner una animación
        }
            Intent mi_intent = new Intent(view.getContext(), MainActivity.class);
            mi_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mi_intent);
            finish();

        }
    });

    }
}