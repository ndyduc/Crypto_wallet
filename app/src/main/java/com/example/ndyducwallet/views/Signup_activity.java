
package com.example.ndyducwallet.views;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ndyducwallet.R;
import com.example.ndyducwallet.Validator.Email;
import com.example.ndyducwallet.modles.Django;
import com.example.ndyducwallet.modles.Users;
import com.example.ndyducwallet.viewmodles.API_Django;
import com.example.ndyducwallet.viewmodles.Repository;
import com.example.ndyducwallet.viewmodles.Retrofit_client;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup_activity extends AppCompatActivity {

    private ImageButton btnback, infor;
    private ImageView eye1, eye2;
    private Button register;
    private boolean switchState, switchState1;
    private EditText txtpass, txtrepass, txtusername, txtphone, txtemail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_c);
        init();
        events();
    }

    public void init() {
        btnback = findViewById(R.id.btnback);
        eye1 = findViewById(R.id.eye);
        eye2 = findViewById(R.id.eyeswitch2);
        register = findViewById(R.id.btnconfirm);
        infor = findViewById(R.id.btninfor);

        txtusername = findViewById(R.id.txtusername);
        txtphone = findViewById(R.id.txtphone);
        txtemail = findViewById(R.id.txtemail);
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
                String username = txtusername.getText().toString();
                String phone = txtphone.getText().toString();
                String email = txtemail.getText().toString();
                String pass = txtpass.getText().toString();
                String repass = txtrepass.getText().toString();

                if (username.isEmpty() || phone.isEmpty() || email.isEmpty() || pass.isEmpty() || repass.isEmpty()) {
                    error(Signup_activity.this, "Fill the form !");
                    return;
                }

                if (!Email.validateEmail(email)) {
                    error(Signup_activity.this,"Email is not valid !");
                    return;
                }

                if (pass.equals(repass)) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            JsonObject jsonObject = new JsonObject();
                            jsonObject.addProperty("phone", phone);
                            jsonObject.addProperty("name", username);
                            jsonObject.addProperty("email", email);
                            jsonObject.addProperty("pass", pass);
                            Register(jsonObject);
                        }
                    }).start();
                } else {
                    error(Signup_activity.this,"Your password doesn't match !");
                }
            }
        });

    }

    private void Register(JsonObject user) {
        API_Django apiService = Retrofit_client.getClient().create(API_Django.class);
        Call<Django> call = apiService.register(user);
        call.enqueue(new Callback<Django>() {
            @Override
            public void onResponse(@NonNull Call<Django> call, @NonNull Response<Django> response) {
                if (response.isSuccessful()) {
                    Django djangoResponse = response.body();
                    assert djangoResponse != null;
                    String error = djangoResponse.getError();
                    if (error != null) {
                        error(Signup_activity.this, "Account allready exist !");
                        return;
                    }
                    Users user = djangoResponse.getUser();
                    Log.d("JSON", user.getPhone());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (user.getPhone() != null) {
                                Repository.getInstance().setUserPhone(user.getPhone());
                                Repository.getInstance().setUserEmail(user.getEmail());
                                Repository.getInstance().setUserStatus("signup");
                                Intent intent = new Intent(Signup_activity.this, Code.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                            }
                        }
                    });
                } else {
                    error(Signup_activity.this,"Can't connect to server");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Django> call, @NonNull Throwable t) {
                t.printStackTrace();
                error(Signup_activity.this,"Server don't response !");
            }
        });
    }

    public static void error(Context context, String message) {
        error(context, message, "Error", "OK");
    }

    public static void error(Context context, String message, String title) {
        error(context, message, title, "OK");
    }

    public static void error(Context context, String message, String Title, String button){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.alert_login);

        Window window = dialog.getWindow();
        assert window != null;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btnNo = dialog.findViewById(R.id.btnno);
        TextView txttitle = dialog.findViewById(R.id.txttitle);
        ImageButton btnclose = dialog.findViewById(R.id.btnclose);
        TextView txtinform = dialog.findViewById(R.id.txtinform);

        txtinform.setText(message);
        txttitle.setText(Title);
        btnNo.setText(button);

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