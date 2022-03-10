package com.example.whac_a_mole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Records extends AppCompatActivity {

    protected ImageView ivSalirRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();

        ivSalirRecords=findViewById(R.id.ivSalirRecords);

        ivSalirRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mi_intent = new Intent(view.getContext(), MainActivity.class);
                mi_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mi_intent);
                finish();
            }
        });

    }
}