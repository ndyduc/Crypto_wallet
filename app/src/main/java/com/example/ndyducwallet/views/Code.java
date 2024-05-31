package com.example.ndyducwallet.views;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ndyducwallet.R;
import com.example.ndyducwallet.modles.Django;
import com.example.ndyducwallet.modles.Users;
import com.example.ndyducwallet.viewmodles.API_Django;
import com.example.ndyducwallet.viewmodles.Repository;
import com.example.ndyducwallet.viewmodles.Retrofit_client;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Code extends AppCompatActivity {
    private EditText c1, c2, c3, c4, c5, c6;
    private ImageButton btnback;
    private Button btnconfirm;
    private TextView btnresend, txtemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmcodeemail);
        String userEmail = Repository.getInstance().getUserEmail();
        init();
        txtemail.setText(userEmail);

        events();
    }

    public void init() {
        c1 = findViewById(R.id.editTextNumber1);
        c2 = findViewById(R.id.editTextNumber2);
        c3 = findViewById(R.id.editTextNumber3);
        c4 = findViewById(R.id.editTextNumber4);
        c5 = findViewById(R.id.editTextNumber5);
        c6 = findViewById(R.id.editTextNumber6);

        txtemail = findViewById(R.id.txtemail);
        txtemail = findViewById(R.id.txtemail);
        btnresend = findViewById(R.id.btnresend);

        btnback = findViewById(R.id.btnback);
        btnconfirm = findViewById(R.id.btnconfirm);
    }

    public void events() {
        c1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    c2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        c2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    c3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        c3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    c4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        c4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    c5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        c5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    c6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnresend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String userPhone = Repository.getInstance().getUserPhone();
                        String userEmail = Repository.getInstance().getUserEmail();
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("email", userEmail);
                        jsonObject.addProperty("phone", userPhone);
                        Log.d("JSON Response", jsonObject.toString());
                        resend_email(jsonObject);

                    }
                }).start();
            }
        });

        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value1 = c1.getText().toString();
                String value2 = c2.getText().toString();
                String value3 = c3.getText().toString();
                String value4 = c4.getText().toString();
                String value5 = c5.getText().toString();
                String value6 = c6.getText().toString();

                String userPhone = Repository.getInstance().getUserPhone();
                String code = value1 + value2 + value3 + value4 + value5 + value6;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("phone", userPhone);
                        jsonObject.addProperty("fa", code);

                        Confirm(jsonObject);
                    }
                }).start();
            }
        });

    }

    private void Confirm(JsonObject user) {
        API_Django apiService = Retrofit_client.getClient().create(API_Django.class);
        Call<Django> call = apiService.confirm(user);
        call.enqueue(new Callback<Django>() {
            @Override
            public void onResponse(@NonNull Call<Django> call, @NonNull Response<Django> response) {
                if (response.isSuccessful()) {
                    Django djangoResponse = response.body();
                    assert djangoResponse != null;
                    Users user = djangoResponse.getUser();
                    String status = djangoResponse.getStatus();
                    if(status.equals("Invalid code")) Signup_activity.error(Code.this, status, "Verify code");
                    Log.d("JSON", djangoResponse.toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (user.getPhone() != null) {
                                Repository.getInstance().setUserId(user.getId());
                                Repository.getInstance().setUserPhone(user.getPhone());
                                Repository.getInstance().setUserEmail(user.getEmail());
                                Repository.getInstance().setUserName(user.getName());
                                String userStatus = Repository.getInstance().getUserStatus();
                                switch (userStatus) {
                                    case "forgot password":
                                        Log.d("JSON confirmcode", status+userStatus);
                                        movetoPass();
                                        break;
                                    case "signup":
                                        movetoHome();
                                        break;
                                    case "transfer":
                                        movetoBill();
                                        break;
                                    case "change":
                                        movetoProfile();
                                        break;
                                    default:
                                        Signup_activity.error(Code.this, "Repository Status is none !");
                                        break;
                                }
                                }
                            else {
                                Signup_activity.error(Code.this, "Can't get User !");
                            }
                        }
                    });
                } else {
                    Signup_activity.error(Code.this, "Can't connect to server","Verify code");
                }
            }

            @Override
            public void onFailure(Call<Django> call, Throwable t) {
                t.printStackTrace();
                Signup_activity.error(Code.this, "Server don't response !");
            }
        });
    }

    public void resend_email(JsonObject user) {
        API_Django apiService = Retrofit_client.getClient().create(API_Django.class);
        Call<Django> call = apiService.resend_email(user);
        call.enqueue(new Callback<Django>() {
            @Override
            public void onResponse(Call<Django> call, Response<Django> response) {
                if (response.isSuccessful()) {
                    Django djangoResponse = response.body();
                    String status = djangoResponse.getStatus();
                    Log.d("JSON resend", status);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if ("Resend successfuly !".equals(status)) {
                                Signup_activity.error(Code.this, status,"Resend code");
                            } else {
                                Signup_activity.error(Code.this, status,"Resend code");
                            }
                        }
                    });
                } else {
                    Signup_activity.error(Code.this, "Can't connect to server","Verify code");
                }
            }

            @Override
            public void onFailure(Call<Django> call, Throwable t) {
                t.printStackTrace();
                Signup_activity.error(Code.this, "Server don't response !","Verify code");
            }
        });
    }

    private void movetoPass(){
        Intent intent = new Intent(Code.this, Password.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    private void movetoHome() {
        Intent intent = new Intent(Code.this, Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    private void movetoProfile(){
        Intent intent = new Intent(Code.this, Profile.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    private void movetoBill(){
        Intent intent = new Intent(Code.this, Bill_Activity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
}
