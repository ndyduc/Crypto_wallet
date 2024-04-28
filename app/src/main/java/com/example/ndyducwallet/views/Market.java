package com.example.ndyducwallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Market extends AppCompatActivity {
    TextView open, close, high, low, cap, vol, btnlog;
    private ImageButton infor, home, wallet, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.market);
        init();
        events();
    }

    public void init(){
        infor = findViewById(R.id.btninfor);
        home = findViewById(R.id.btnhome);
        wallet = findViewById(R.id.btnwallet);
        profile = findViewById(R.id.btnprofile);

        open = findViewById(R.id.txtopen);
        close = findViewById(R.id.txtclose);
        high = findViewById(R.id.txthigh);
        low = findViewById(R.id.txtlow);
        cap = findViewById(R.id.txtcap);
        vol = findViewById(R.id.txtvol);

        btnlog = findViewById(R.id.btnlog);
    }

    public void events(){
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Market.this, Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
    }

    public String callRestfulApi() {
        HttpURLConnection urlConnection = null;
        String stResponse = "";
        try {
            URL url = new URL("https://api.kraken.com/0/public/Ticker?pair=ETHUSD");
            urlConnection = (HttpURLConnection) url.openConnection();

            int code = urlConnection.getResponseCode();
            if (code != 200) {
                throw new IOException("Invalid response from server: " + code);
            }

            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                stResponse += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return stResponse;
    }


}
