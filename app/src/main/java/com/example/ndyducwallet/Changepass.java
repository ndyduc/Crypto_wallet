package com.example.ndyducwallet;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Changepass extends AppCompatActivity {
    ImageButton btnback,btninfor;
    ImageView eye1,eye2,eye3;
    Button confirm;
    EditText opass,npass1,npass2;
    private boolean switchState1, switchState2, switchState3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepass);
        init();
        events();
    }

    public void init() {
        btnback = findViewById(R.id.btnback);
        btninfor = findViewById(R.id.btninfor);
        eye1 = findViewById(R.id.eyeswitch1);
        eye2 = findViewById(R.id.eyeswitch2);
        eye3 = findViewById(R.id.eyeswitch3);
        opass = findViewById(R.id.txtopass);
        npass1 = findViewById(R.id.txtnpass1);
        npass2 = findViewById(R.id.txtnpass2);
        confirm = findViewById(R.id.btnconfirm);
    }

    public void events() {
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        eye1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchState1 = !switchState1;
                if (switchState1) {
                    eye1.setImageResource(R.drawable.eye_slash_fill);
                    opass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    eye1.setImageResource(R.drawable.eye_fill);
                    opass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        eye2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchState2 = !switchState2;
                if (switchState2) {
                    eye2.setImageResource(R.drawable.eye_slash_fill);
                    npass1.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    eye2.setImageResource(R.drawable.eye_fill);
                    npass1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        eye3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchState3 = !switchState3;
                if (switchState3) {
                    eye3.setImageResource(R.drawable.eye_slash_fill);
                    npass2.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    eye3.setImageResource(R.drawable.eye_fill);
                    npass2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }
}
