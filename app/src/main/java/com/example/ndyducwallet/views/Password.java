package com.example.ndyducwallet.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ndyducwallet.R;
import com.example.ndyducwallet.modles.Django;
import com.example.ndyducwallet.viewmodles.API_Django;
import com.example.ndyducwallet.viewmodles.Repository;
import com.example.ndyducwallet.viewmodles.Retrofit_client;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Password extends AppCompatActivity {
    ImageButton btnback,btninfor;
    ImageView eye1,eye2,eye3;
    Button confirm;
    EditText opass,npass1,npass2;
    TextView txtname;
    LinearLayout layoldpass;
    private boolean switchState1, switchState2, switchState3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepass);
        init();
        String statusStatus = Repository.getInstance().getUserStatus();
        if(statusStatus.equals("forgot password")) {
            layoldpass.setVisibility(View.GONE);
            btninfor.setVisibility(View.GONE);
        } else txtname.setText(Repository.getInstance().getUserName());
        events();
    }

    public void init() {
        btnback = findViewById(R.id.btnback);
        btninfor = findViewById(R.id.btninfor);
        eye1 = findViewById(R.id.eye);
        eye2 = findViewById(R.id.eyeswitch2);
        eye3 = findViewById(R.id.eyeswitch3);
        opass = findViewById(R.id.txtopass);
        npass1 = findViewById(R.id.txtnpass1);
        npass2 = findViewById(R.id.txtnpass2);
        confirm = findViewById(R.id.btnconfirm);
        layoldpass = findViewById(R.id.layoldpass);
        txtname = findViewById(R.id.txtname);
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

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldpass = opass.getText().toString();
                String newpass1 = npass1.getText().toString();
                String newpass2 = npass2.getText().toString();

                if ( newpass1.isEmpty() || newpass2.isEmpty()) {
                    Signup_activity.error(Password.this, "Fill the form !","Change password");
                    return;
                }

                if (!newpass1.equals(newpass2)) {
                    Signup_activity.error(Password.this, "Password not match !","Change password");
                    return;
                }
                String userStatus = Repository.getInstance().getUserStatus();
                String userPhone = Repository.getInstance().getUserPhone();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("phone", userPhone);
                        jsonObject.addProperty("oldpass", oldpass);
                        jsonObject.addProperty("newpass", newpass1);

                        if(userStatus.equals("forgot password")) forgot_password(jsonObject);
                        else change_password(jsonObject);

                    }
                }).start();
        }
    });

}

    private void forgot_password(JsonObject status) {
        API_Django apiService = Retrofit_client.getClient().create(API_Django.class);
        Call<Django> call = apiService.forgot_password(status);
        call.enqueue(new Callback<Django>() {
            @Override
            public void onResponse(Call<Django> call, Response<Django> response) {
                if (response.isSuccessful()) {
                    Django djangoResponse = response.body();
                    String status = djangoResponse.getStatus();
                    Log.d("JSON", status);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (status.equals("Change password successfully !")) {
                                Signup_activity.error(Password.this,status,"Change password","Go to home");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(Password.this, Home.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        startActivity(intent);
                                    }
                                }, 15000);
                                finish();
                            }
                            else {
                                Signup_activity.error(Password.this, status,"Change password");
                            }
                        }
                    });
                } else {
                    Signup_activity.error(Password.this, "Can't connect to server");
                }
            }

            @Override
            public void onFailure(Call<Django> call, Throwable t) {
                t.printStackTrace();
                Signup_activity.error(Password.this, "Server don't response !","Change password");
            }
        });
    }

    private void change_password(JsonObject status){
        API_Django apiService = Retrofit_client.getClient().create(API_Django.class);
        Call<Django> call = apiService.forgot_password(status);
        call.enqueue(new Callback<Django>() {
            @Override
            public void onResponse(Call<Django> call, Response<Django> response) {
                if (response.isSuccessful()) {
                    Django djangoResponse = response.body();
                    String status = djangoResponse.getStatus();
                    Log.d("JSON", status);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (status.equals("Change password successfully !")) {
                                Signup_activity.error(Password.this,status,"Change password","Excilent !");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                    }
                                }, 15000);
                            }
                            else {
                                Signup_activity.error(Password.this, status,"Change password");
                            }
                        }
                    });
                } else {
                    Signup_activity.error(Password.this, "Can't connect to server");
                }
            }

            @Override
            public void onFailure(Call<Django> call, Throwable t) {
                t.printStackTrace();
                Signup_activity.error(Password.this, "Server don't response !","Change password");
            }
        });
    }
}