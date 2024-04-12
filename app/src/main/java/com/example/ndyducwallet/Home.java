package com.example.ndyducwallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {
    private ImageButton profile,market,wallet;
    private Button transfer,exchange,addnet,withdrawal,history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        init();
        events();
    }


    public void init() {
        transfer = findViewById(R.id.btntransfer);
        exchange = findViewById(R.id.btnexchange);
        addnet = findViewById(R.id.btnaddnetwork);
        withdrawal = findViewById(R.id.btnwithdrawal);
        history = findViewById(R.id.btnhistory);

        profile = findViewById(R.id.btnprofile);
        market = findViewById(R.id.btnmarket);
        wallet = findViewById(R.id.btnwallet);
    }

    public void events() {
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Profile.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Transfer.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Market.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }
}
