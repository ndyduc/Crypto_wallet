package com.example.ndyducwallet;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Code extends AppCompatActivity {
    private EditText c1,c2,c3,c4,c5,c6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmcodeemail);
        init();
        events();
    }

    public void init() {
        c1 = findViewById(R.id.editTextNumber1);
        c2 = findViewById(R.id.editTextNumber2);
        c3 = findViewById(R.id.editTextNumber3);
        c4 = findViewById(R.id.editTextNumber4);
        c5 = findViewById(R.id.editTextNumber5);
        c6 = findViewById(R.id.editTextNumber6);
    }

    public void events() {
        c1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    c2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        c2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    c3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        c3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    c4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        c4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    c5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        c5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    c6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

    }
}
