package com.example.ndyducwallet.views;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ndyducwallet.R;
import com.example.ndyducwallet.modles.Django;
import com.example.ndyducwallet.modles.Users;
import com.example.ndyducwallet.viewmodles.API_Django;
import com.example.ndyducwallet.viewmodles.Login_VM;
import com.example.ndyducwallet.viewmodles.Repository;
import com.example.ndyducwallet.viewmodles.Retrofit_client;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class Login_Activity extends AppCompatActivity {
    private Button btnlogin,btnsignup;
    private ImageView eye;
    private EditText txtpass,txtphone;
    private boolean switchState = false;
    private Login_VM loginVM;
    private TextView btnforgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newaccount);
        init();
        events();
        loginVM = new ViewModelProvider(this).get(Login_VM.class);

    }

    public void init() {
        btnlogin = findViewById(R.id.btnconfirm);
        btnsignup = findViewById(R.id.btnsignup);
        txtphone = findViewById(R.id.txtphone);
        txtpass = findViewById(R.id.txtnpass1);
        eye = findViewById(R.id.eyeswitch);
        btnforgot = findViewById(R.id.btnforgot);
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
                String phoneValue = txtphone.getText().toString();
                String passValue = txtpass.getText().toString();
                if (!TextUtils.isEmpty(phoneValue) && !TextUtils.isEmpty(passValue)) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            OkHttpClient client = new OkHttpClient();
                            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
                            Gson son = new Gson();
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("phone", phoneValue);
                            jsonObject.addProperty("password", passValue);
                            String json = son.toJson(jsonObject);
                            RequestBody body = RequestBody.create(json, mediaType);

                            Request request = new Request.Builder()
                                    .url("http://10.0.2.2:8000/check_user")
                                    .post(body)
                                    .build();

                            client.newCall(request).enqueue(new Callback() {
                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    try (ResponseBody responseBody = response.body()) {
                                        if (!response.isSuccessful()) {
                                            throw new IOException("Unexpected code " + response);
                                        }
                                        String jsonResponse = responseBody.string();

                                        Log.d("JSON Response", jsonResponse);
                                        Gson gson = new Gson();
                                        Django djangoResponse = gson.fromJson(jsonResponse, Django.class);
                                        Users user = djangoResponse.getUser();
                                        String error = djangoResponse.getError();
                                        if (error != null) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    error("User not found !");
                                                }
                                            });
                                        } else {
                                            if ( user.getId() > 0) {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
//                                                    loginVM.setUserId(user.getId());
                                                        Repository.getInstance().setUserId(user.getId());
                                                        Repository.getInstance().setUserEmail(user.getEmail());
                                                        Repository.getInstance().setUserPhone(user.getPhone());
                                                        Repository.getInstance().setUserName(user.getName());
                                                        Intent intent = new Intent(Login_Activity.this, Home.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                });
                                            } else {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        error("Can't find your account !");
                                                    }
                                                });
                                            }
                                        }


                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                    e.printStackTrace();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            error("Fail to connect to server !");
                                        }
                                    });
                                }
                            });
                        }
                    }).start();
                } else {
                    error("Enter Phone number & Password");
                }
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

        btnforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userPhone = txtphone.getText().toString();
                if (userPhone.isEmpty()) {
                    error("Please enter phone number ");
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("phone", userPhone);
                            getemailbyphone(jsonObject);

                        }
                    }).start();
                }
            }
        });

    }

    public void getemailbyphone(JsonObject user) {
        API_Django apiService = Retrofit_client.getClient().create(API_Django.class);
        retrofit2.Call<Django> call = apiService.get_email_by_phone(user);
        call.enqueue(new retrofit2.Callback<Django>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<Django> call, @NonNull retrofit2.Response<Django> response) {
                if (response.isSuccessful()) {
                    Django djangoResponse = response.body();
                    Users user = djangoResponse.getUser();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (user.getPhone() != null) {
                                Log.d("JSON", user.getEmail());
                                Repository.getInstance().setUserEmail(user.getEmail());
                                Repository.getInstance().setUserPhone(user.getPhone());
                                Repository.getInstance().setUserStatus("forgot password");
                                Intent intent = new Intent(Login_Activity.this, Code.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                            }else error("Account not found !");
                        }
                    });
                } else {
                    error("Can't connect to server");
                }
            }

            @Override
            public void onFailure(retrofit2.Call<Django> call, Throwable t) {
                t.printStackTrace();
                error("Server don't response !");
            }
        });
    }

    private void error(String message){
        Dialog dialog = new Dialog(Login_Activity.this);
        dialog.setContentView(R.layout.alert_login);

        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btnNo = dialog.findViewById(R.id.btnno);
        ImageButton btnclose = dialog.findViewById(R.id.btnclose);
        TextView txtinform = dialog.findViewById(R.id.txtinform);

        txtinform.setText(message);

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
}