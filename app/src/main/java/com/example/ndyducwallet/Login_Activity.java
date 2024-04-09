package com.example.ndyducwallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class Login_Activity extends AppCompatActivity {
    private Button btnlogin,btnsignup;
    private ImageButton btnback;
    private Switch eye;
    private EditText loginpass,txtphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newaccount); // Thay đổi layout thành newaccount.xml
        init();
        events();
    }

    public void init() {
        btnlogin = findViewById(R.id.btnconfirm);
        loginpass = findViewById(R.id.txtopw);
        btnsignup = findViewById(R.id.btnsignup);
        btnback = findViewById(R.id.btnback);
        loginpass = findViewById(R.id.txtpassword);
        txtphone = findViewById(R.id.txtphone);
        eye = findViewById(R.id.eyeswitch1);
    }

    public void events() {
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Activity.this, Signup_activity.class);
                startActivity(intent);
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Activity.this, Home.class);
                startActivity(intent);
            }
        });

        eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}