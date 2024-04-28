package com.example.ndyducwallet;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

public class Transfer extends AppCompatActivity {
    private ImageButton btnmess, btnwli, btnback,btninfor;
    private ImageView inet, itok;
    private EditText txtmess;
    private LinearLayout btnnwo, btntko;
    private TextView btnwl;
    private NestedScrollView networkop, tokenop, wlop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer);
        init();
        events();
    }

    public void init(){
        btnback = findViewById(R.id.btnback);
        btninfor = findViewById(R.id.btninfor);

        networkop = findViewById(R.id.networkop);
        btnnwo = findViewById(R.id.btnnwo);
        inet = findViewById(R.id.inet);

        tokenop = findViewById(R.id.tokenop);
        btntko = findViewById(R.id.btntko);
        itok = findViewById(R.id.itok);

        wlop = findViewById(R.id.wlop);
        btnwl = findViewById(R.id.btnwl);
        btnwli = findViewById(R.id.btnwli);

        btnmess = findViewById(R.id.btnmess);
        txtmess = findViewById(R.id.txtmess);
    }

    public void events(){
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnnwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (networkop.getVisibility() == View.VISIBLE) {
                    networkop.setVisibility(View.GONE);
                    inet.setImageResource(R.drawable.down_circle);
                } else {
                    networkop.setVisibility(View.VISIBLE);
                    inet.setImageResource(R.drawable.up_circle);
                }
            }
        });

        btntko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tokenop.getVisibility() == View.VISIBLE) {
                    tokenop.setVisibility(View.GONE);
                    itok.setImageResource(R.drawable.down_circle);
                } else {
                    tokenop.setVisibility(View.VISIBLE);
                    itok.setImageResource(R.drawable.up_circle);
                }
            }
        });

        btnwl.setOnClickListener(ownwallet);
        btnwli.setOnClickListener(ownwallet);

        btnmess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(txtmess, InputMethodManager.SHOW_IMPLICIT);
            }
        });
    }


    private View.OnClickListener ownwallet = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (wlop.getVisibility() == View.VISIBLE) {
                wlop.setVisibility(View.GONE);
                btnwli.setImageResource(R.drawable.u_angle_down);
            } else {
                wlop.setVisibility(View.VISIBLE);
                btnwli.setImageResource(R.drawable.u_angle_up);
            }
        }
    };
}
