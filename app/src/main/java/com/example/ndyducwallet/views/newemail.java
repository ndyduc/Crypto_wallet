package com.example.ndyducwallet.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ndyducwallet.R;

public class newemail extends AppCompatActivity {
    private ImageButton btnback,btninfor;
    private Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newemail);
        init();
        events();
    }

    public void init(){
        btnback = findViewById(R.id.btnback);
        btninfor = findViewById(R.id.btninfor);
        confirm = findViewById(R.id.btnconfirm);
    }

    public void events(){
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(newemail.this, Code.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }
}
