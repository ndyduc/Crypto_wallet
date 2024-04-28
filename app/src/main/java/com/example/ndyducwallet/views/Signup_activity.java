
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

public class Signup_activity extends AppCompatActivity {

    private ImageButton btnback, infor;
    private ImageView eye1, eye2;
    private Button register;
    private boolean switchState, switchState1;
    private EditText txtpass, txtrepass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_c);
        init();
        events();
    }

    public void init() {
        btnback = findViewById(R.id.btnback);
        eye1 = findViewById(R.id.eyeswitch1);
        eye2 = findViewById(R.id.eyeswitch2);
        register = findViewById(R.id.btnconfirm);
        infor = findViewById(R.id.btninfor);
        txtpass = findViewById(R.id.txtnpass1);
        txtrepass = findViewById(R.id.txtrepass);
    }

    public void events() {
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        infor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signup_activity.this , Information.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        eye1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchState = !switchState;
                if (switchState) {
                    eye1.setImageResource(R.drawable.eye_slash_fill);
                    txtpass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    eye1.setImageResource(R.drawable.eye_fill);
                    txtpass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        eye2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchState1 = !switchState1;
                if (switchState1) {
                    eye2.setImageResource(R.drawable.eye_slash_fill);
                    txtrepass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    eye2.setImageResource(R.drawable.eye_fill);
                    txtrepass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}