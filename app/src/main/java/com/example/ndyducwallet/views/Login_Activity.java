package com.example.ndyducwallet;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Login_Activity extends AppCompatActivity {
    private Button btnlogin,btnsignup;
    private ImageButton btnback;
    private ImageView eye;
    private EditText txtpass,txtphone;
    private boolean switchState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newaccount); // Thay đổi layout thành newaccount.xml
        init();
        events();
    }

    public void init() {
        btnlogin = findViewById(R.id.btnconfirm);
        btnsignup = findViewById(R.id.btnsignup);
        btnback = findViewById(R.id.btnback);
        txtphone = findViewById(R.id.txtphone);
        txtpass = findViewById(R.id.txtnpass1);
        eye = findViewById(R.id.eyeswitch);
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
                switchState = !switchState;
                if (switchState) {
                    eye.setImageResource(R.drawable.eye_slash_fill);
                    txtpass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    eye.setImageResource(R.drawable.eye_fill);
                    txtpass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }

}