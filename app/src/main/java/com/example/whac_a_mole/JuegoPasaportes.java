package com.example.whac_a_mole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class JuegoPasaportes extends AppCompatActivity {

    CountDownTimer CDT;
    CountDownTimer tiempoEspera;
    long tiempoMilisegundos =60000;

    CountDownTimer espera1; //= new CountDownTimer[10];
    CountDownTimer espera2;//= new CountDownTimer[10];
    protected ImageView[] ivPas = new ImageView[10];

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
    protected int i=0;
    protected int tintaAlPulsar;

    //Función inicia el CDT (Aquí se definene los tiempos)
    protected void startTimer(){
        //countDownInterval: Tiempo que tarda en generarse* un nuevo pasaporte (puede no generarse si toca una posicion que esta ocupada)
        CDT = new CountDownTimer(tiempoMilisegundos, 500) {
            @Override
            public void onTick(long l) {
                tvCountdown.setText(String.format("%2d",l/1000));
                //Tiempo espera: tiempo en que desaparece el pasaporte
                GeneraPas(1500);
            }
            @Override
            public void onFinish() {
                tvCountdown.setText("0");
            //Hacer visible el botón volver
                btVolver.setVisibility(View.VISIBLE);
                estadoGlobal=0;
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

    //Funcion genera un Pasaporte Aleatorio en posicion aleatoria
    protected void GeneraPas(long TiempoEspera){


        //Generamos numero de mesa y de pasaporte
        int numMesa =  (int)(Math.random()*9);
        int numPas = (int)((Math.random()*3)+1);

        //asociamos el pasaporte a la mesa solo si no hay ya pasaporte
        if(estados[numMesa]==0){

            estados[numMesa]=numPas;

            tiempoEspera = new CountDownTimer (TiempoEspera, 200) {
                @Override
                public void onTick(long millisUntilFinished) {
                // Código con cada tic del timer
                }
                @Override
                public void onFinish() {
                // Código cuando finalice el timer vuelve invisible el pasaporte y pone el estado de la mesa a 0 si no le han dado antes
                    if(estados[numMesa]!=0&&estados[numMesa]!=4){
                        estados[numMesa]=0;
                        switch(numMesa){

                            case 0:
                                ivPas1.setVisibility(View.INVISIBLE);
                                break;
                            case 1:
                                ivPas2.setVisibility(View.INVISIBLE);
                                break;
                            case 2:
                                ivPas3.setVisibility(View.INVISIBLE);
                                break;
                            case 3:
                                ivPas4.setVisibility(View.INVISIBLE);
                                break;
                            case 4:
                                ivPas5.setVisibility(View.INVISIBLE);
                                break;
                            case 5:
                                ivPas6.setVisibility(View.INVISIBLE);
                                break;
                            case 6:
                                ivPas7.setVisibility(View.INVISIBLE);
                                break;
                            case 7:
                                ivPas8.setVisibility(View.INVISIBLE);
                                break;
                            case 8:
                                ivPas9.setVisibility(View.INVISIBLE);
                                break;
                        }
                    }
                }
            }.start();

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
                    break;
            }
        }
    }

    //Funcion estampa cuando se pueda y actualiza la puntuación
    protected void Estampa(int numMesa){


        if((estados[numMesa]!=0)&&(estados[numMesa]!=4)&&estadoGlobal==1){

            i++;
            if(i>=10){
                i=0;
            }
            //guardamos con que tinta hemos pulsado para que no pueda cambiar en la animación;
            tintaAlPulsar=tinta;

            switch (numMesa){
                case 0:
                    ivPas[i]=ivPas1;
                    break;
                case 1:
                    ivPas[i]=ivPas2;
                    break;
                case 2:
                    ivPas[i]=ivPas3;
                    break;
                case 3:
                    ivPas[i]=ivPas4;
                    break;
                case 4:
                    ivPas[i]=ivPas5;
                    break;
                case 5:
                    ivPas[i]=ivPas6;
                    break;
                case 6:
                    ivPas[i]=ivPas7;
                    break;
                case 7:
                    ivPas[i]=ivPas8;
                    break;
                case 8:
                    ivPas[i]=ivPas9;
                    break;

            }

                    //en caso de pasaporte 1
                    if (estados[numMesa] == 1) {
                        //hace que no puedas darle mas de una vez
                        estados[numMesa]=4;
                        //pone la estampa
                        ivPas[i].setImageResource(R.drawable.pas1st);
                        //espera 0.2s para que se vea la estampa
                        espera1 = new CountDownTimer(100, 100) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                // Código con cada tic del timer
                            }

                            @Override
                            public void onFinish() {
                                if (tintaAlPulsar == 1) {
                                    ivPas[i].setImageResource(R.drawable.pas1v);
                                    puntuacion += 1;
                                } else if (tintaAlPulsar == 2) {
                                    ivPas[i].setImageResource(R.drawable.pas1nv);
                                    puntuacion -= 1;
                                }
                                ActualizaPuntuacion();

                                //segunda espera para que se vea el sello
                                espera2 = new CountDownTimer(500, 100) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        // Código con cada tic del timer
                                    }

                                    @Override
                                    public void onFinish() {
                                        ivPas[i].setVisibility(View.INVISIBLE);
                                        estados[numMesa] = 0;
                                    }
                                }.start();
                            }
                        }.start();
                    //si el pasaporte es el 2
                    } else if (estados[numMesa] == 2) {
                        estados[numMesa]=4;
                        //pone la estampa
                        ivPas[i].setImageResource(R.drawable.pas2st);
                        //espera 0.2s para que se vea la estampa
                        espera1 = new CountDownTimer(100, 100) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                // Código con cada tic del timer
                            }

                            @Override
                            public void onFinish() {
                                if (tintaAlPulsar == 1) {
                                    ivPas[i].setImageResource(R.drawable.pas2v);
                                    puntuacion += 1;
                                } else if (tintaAlPulsar == 2) {
                                    ivPas[i].setImageResource(R.drawable.pas2nv);
                                    puntuacion -= 1;
                                }
                                ActualizaPuntuacion();
                                //segunda espera para que se vea el sello

                                espera2= new CountDownTimer(500, 100) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        // Código con cada tic del timer
                                    }

                                    @Override
                                    public void onFinish() {
                                        ivPas[i].setVisibility(View.INVISIBLE);
                                        estados[numMesa] = 0;
                                    }
                                }.start();
                            }
                        }.start();

                        //pasaporte 3
                    } else if(estados[numMesa]==3) {
                        estados[numMesa]=4;
                        //pone la estampa
                        ivPas[i].setImageResource(R.drawable.pas3st);
                        //espera 0.2s para que se vea la estampa

                        espera1 = new CountDownTimer(100, 100) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                // Código con cada tic del timer
                            }

                            @Override
                            public void onFinish() {
                                if (tintaAlPulsar == 1) {
                                    ivPas[i].setImageResource(R.drawable.pas3v);
                                    puntuacion -= 1;
                                } else if (tintaAlPulsar == 2) {
                                    ivPas[i].setImageResource(R.drawable.pas3nv);
                                    puntuacion += 1;
                                }
                                ActualizaPuntuacion();
                                //segunda espera para que se vea el sello

                                espera2 = new CountDownTimer(500, 100) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        // Código con cada tic del timer
                                    }

                                    @Override
                                    public void onFinish() {
                                        ivPas[i].setVisibility(View.INVISIBLE);
                                        estados[numMesa] = 0;
                                    }
                                }.start();
                            }
                        }.start();

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


        //Hacemos los pasaportes y el boton volver invisibles
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

        //marco la tinta verde
        ivTintaVerde.setScaleX(1.5F);
        ivTintaVerde.setScaleY(1.5F);

        //Ponemos el estado Global en 1 (Jugando) y iniciamos el contador
        estadoGlobal=1;
        startTimer();




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
                ivTintaVerde.setScaleX(1.5F);
                ivTintaVerde.setScaleY(1.5F);
                ivTintaRoja.setScaleX(1F);
                ivTintaRoja.setScaleY(1F);

            }
        });

        ivTintaRoja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tinta=2;
                ivTintaVerde.setScaleX(1F);
                ivTintaVerde.setScaleY(1F);
                ivTintaRoja.setScaleX(1.5F);
                ivTintaRoja.setScaleY(1.5F);
            }
        });


    btVolver.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    });





    }
}