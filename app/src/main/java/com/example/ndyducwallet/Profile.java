package com.example.ndyducwallet;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {
    private ImageButton home,market,wallet;
    private Button ce,cp,fa,mn,mt,sl,logout;

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

        cp = findViewById(R.id.btnwithdrawal);
        ce = findViewById(R.id.btnce);
        fa = findViewById(R.id.btnadd);
        mn = findViewById(R.id.btnmn);
        mt = findViewById(R.id.btnmt);
        sl = findViewById(R.id.btnhistory);
        logout = findViewById(R.id.btnlogout);
    }

    public void events() {
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(Profile.this);
                dialog.setContentView(R.layout.alert_logout);

                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setGravity(Gravity.BOTTOM);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                Button btnYes = dialog.findViewById(R.id.btnyes);
                Button btnNo = dialog.findViewById(R.id.btnno);
                ImageButton btnclose = dialog.findViewById(R.id.btnclose);

                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Profile.this, Login_Activity.class);
                        startActivity(intent);
                        finish();
                        dialog.dismiss();
                    }
                });

                btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btnclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });

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

        cp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, Changepass.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

    }
}
