package com.example.ndyducwallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {
    private ImageButton home,market,wallet;
    private Button ce,cp,fa,mn,mt,sl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        init();
        events();
    }


    public void init() {
        home = findViewById(R.id.btnhome);
        market = findViewById(R.id.btnmarket);
        wallet = findViewById(R.id.btnwallet);

        cp = findViewById(R.id.btncp);
        ce = findViewById(R.id.btnce);
        fa = findViewById(R.id.btnfa);
        mn = findViewById(R.id.btnmn);
        mt = findViewById(R.id.btnmt);
        sl = findViewById(R.id.btnsl);
    }

    public void events() {
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        ce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, newemail.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }
}
