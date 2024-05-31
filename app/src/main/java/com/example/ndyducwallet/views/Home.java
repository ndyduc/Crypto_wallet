package com.example.ndyducwallet.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ndyducwallet.NewsHelper;
import com.example.ndyducwallet.R;
import com.example.ndyducwallet.modles.Django;
import com.example.ndyducwallet.modles.Tokens_Data;
import com.example.ndyducwallet.modles.Users;
import com.example.ndyducwallet.viewmodles.API_Django;
import com.example.ndyducwallet.viewmodles.Login_VM;
import com.example.ndyducwallet.viewmodles.Token_Adapter;
import com.example.ndyducwallet.viewmodles.Repository;
import com.example.ndyducwallet.viewmodles.Retrofit_client;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Home extends AppCompatActivity {
    private ImageButton profile,market,wallet;
    private Button transfer,exchange,addnet,withdrawal,history;
    private Login_VM userid;
    private TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        init();
        events();
        NewsHelper.getNews(this,findViewById(R.id.mum));
        String userPhone = Repository.getInstance().getUserPhone();
        Log.d("JSON", "User ID: " + userPhone);

        new Thread(new Runnable() {
            @Override
            public void run() {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("phone", userPhone);
                getinfor(jsonObject);
            }
        }).start();

    }

    public void init() {
        transfer = findViewById(R.id.btntransfer);
        exchange = findViewById(R.id.btnexchange);
        addnet = findViewById(R.id.btnaddnetwork);
        withdrawal = findViewById(R.id.btnwithdrawal);
        history = findViewById(R.id.btnhistory);

        username = findViewById(R.id.txtusername);

        profile = findViewById(R.id.btnprofile);
        market = findViewById(R.id.btnmarket);
        wallet = findViewById(R.id.btnwallet);
    }

    public void events() {
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Profile.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Transfer.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Market.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });
    }

    private void getinfor(JsonObject userId) {
        API_Django apiService = Retrofit_client.getClient().create(API_Django.class);
        Call<Django> call = apiService.getUserInfo(userId);
        call.enqueue(new Callback<Django>() {
            @Override
            public void onResponse(Call<Django> call,Response<Django> response) {
                if (response.isSuccessful()) {
                    Django djangoResponse = response.body();
                    Users user = djangoResponse.getUser();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (user != null) {
                                username.setText(user.getName());
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Django> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


}
