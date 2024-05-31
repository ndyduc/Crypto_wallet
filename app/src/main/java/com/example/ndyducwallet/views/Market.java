package com.example.ndyducwallet.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ndyducwallet.R;
import com.example.ndyducwallet.modles.Django;
import com.example.ndyducwallet.modles.Token;
import com.example.ndyducwallet.modles.Tokens_Data;
import com.example.ndyducwallet.modles.Users;
import com.example.ndyducwallet.viewmodles.API_Django;
import com.example.ndyducwallet.viewmodles.Market_ViewModel;
import com.example.ndyducwallet.viewmodles.Repository;
import com.example.ndyducwallet.viewmodles.Retrofit_client;
import com.example.ndyducwallet.viewmodles.TokenChangeListener;
import com.example.ndyducwallet.viewmodles.Token_Adapter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Market extends AppCompatActivity {
    TextView open;
    TextView close;
    TextView high;
    TextView low;
    TextView cap;
    TextView vol;
    TextView ask;
    TextView bid;
    static TextView name;
    TextView price;
    TextView percent;
    private ImageButton infor, home, wallet, profile, search;
    private EditText txtsearch;
    static ImageView logo;
    LinearLayout exchange, coinop;
    private boolean CoinopVisible = false, isChangelogo = false;
    private Market_ViewModel marketViewModel;
    String currenttoken, url , endpoint;
    private RecyclerView recyclerView;
    private ArrayList<Tokens_Data> tokenlist;
    private Token_Adapter adapter;
    private static WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.market);
        init();

        txtsearch.setText("");
        tokenlist = new ArrayList<>();
        load_list_tokens(tokenlist);

        currenttoken = Repository.getInstance().getCurrenttoken();
        url = Repository.getInstance().getUrl_logo();
        if(url.isEmpty()){
            Change_Logo("https://s2.coinmarketcap.com/static/img/coins/64x64/1.png");
        } else Change_Logo(url);
        if (TextUtils.isEmpty(currenttoken)) {currenttoken = "BTCUSD";}

        events();
        load_price();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                webview.getSettings().setJavaScriptEnabled(true);
                webview.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        scrollToPosition(view, 0, 360);
                    }
                });
                loadWebView(name.getText().toString());
            }
        }, 3000);
    }

    public static void updateCurrentToken(Context context, String token) {
        Change_Symbol(token, new TokenChangeListener() {
            @Override
            public void onTokenChanged(String token) {
                Repository repository = Repository.getInstance();
                if (repository != null) {
                    repository.setCurrenttoken(token);
                }
                if (context instanceof Activity) {
                    Activity activity = (Activity) context;
                    activity.recreate();
                }
            }
        });
    }


    private static void loadWebView(String endpoint) {
        Log.d("JSON_abc", endpoint);
        webview.loadUrl("https://www.kraken.com/prices/"+endpoint);
    }

    private void scrollToPosition(WebView webview, int x, int y) {
        String script = String.format("window.scrollTo(%d,%d);", x, y);
        Market.this.webview.loadUrl("javascript:(function(){" + script + "})()");
    }

    public void init(){
        infor = findViewById(R.id.btninfor);
        home = findViewById(R.id.btnhome);
        wallet = findViewById(R.id.btnwallet);
        profile = findViewById(R.id.btnprofile);
        webview = findViewById(R.id.timeframe);

        open = findViewById(R.id.txtopen);
        close = findViewById(R.id.txtclose);
        high = findViewById(R.id.txthigh);
        low = findViewById(R.id.txtlow);
        cap = findViewById(R.id.txtcap);
        vol = findViewById(R.id.txtvol);
        ask = findViewById(R.id.txtask);
        bid = findViewById(R.id.txtbid);
        logo = findViewById(R.id.logo);
        name = findViewById(R.id.txtname);
        price = findViewById(R.id.txtprice);
        percent = findViewById(R.id.txtpersent);

        exchange = findViewById(R.id.btnexchange);
        coinop = findViewById(R.id.coinop);
        search = findViewById(R.id.btnsearchmarket);
        txtsearch = findViewById(R.id.txtsearch);
    }

    public void events(){
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marketViewModel.stopDataLoading();
                Intent intent = new Intent(Market.this, Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marketViewModel.stopDataLoading();
                Intent intent = new Intent(Market.this, Profile.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });

        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marketViewModel.stopDataLoading();
                Intent intent = new Intent(Market.this, Wallet.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });

        exchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CoinopVisible = !CoinopVisible;

                if (CoinopVisible) {
                    coinop.setVisibility(View.VISIBLE);
                } else {
                    coinop.setVisibility(View.GONE);
                }
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = txtsearch.getText().toString().trim();
                if (!searchText.isEmpty()) {
                    List<Tokens_Data> reload = new ArrayList<>(tokenlist);
                    List<Tokens_Data> filteredList = new ArrayList<>();
                    for (Tokens_Data token : reload) {
                        if (token.getSymbol().toLowerCase().contains(searchText.toLowerCase())) {
                            filteredList.add(token);
                        }
                    }
                    adapter.updateList(filteredList);
                } else {
                    tokenlist = new ArrayList<>();
                    load_list_tokens(tokenlist);
                }
            }
        });


    }

    private void load_list_tokens(List<Tokens_Data> list) {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Token_Adapter(this, tokenlist);
        recyclerView.setAdapter(adapter);

        API_Django apiService = Retrofit_client.getClient().create(API_Django.class);
        Call<List<Tokens_Data>> call = apiService.get_logo_tokens();
        call.enqueue(new Callback<List<Tokens_Data>>() {
            @Override
            public void onResponse(Call<List<Tokens_Data>> call, Response<List<Tokens_Data>> response) {
                if (response.isSuccessful()) {
                    List<Tokens_Data> userList = response.body();
                    if (userList != null) {
                        tokenlist.addAll(userList);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Tokens_Data>> call, Throwable t) {
                t.printStackTrace();
                Signup_activity.error(Market.this,"Connection Error","Get data","Try again");
            }
        });
    }

    private static void Change_Symbol(String token, TokenChangeListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("symbol", token);
                API_Django apiService = Retrofit_client.getClient().create(API_Django.class);
                Call<Django> call = apiService.get_full_symbol(jsonObject);
                call.enqueue(new Callback<Django>() {
                    @Override
                    public void onResponse(Call<Django> call,Response<Django> response) {
                        if (response.isSuccessful()) {
                            Django djangoResponse = response.body();
                            String symbol = djangoResponse.getSymbol();
                            if (symbol != null) {
                                listener.onTokenChanged(symbol);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Django> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        }).start();
    }

    public static void Change_Logo(String url){
        Picasso.get().load(url).into(logo);
    }

    public void load_price() {
        marketViewModel = new ViewModelProvider(this).get(Market_ViewModel.class);
        marketViewModel.setTokenDefault(currenttoken);
        marketViewModel.startDataLoading();
        marketViewModel.getTokenLiveData().observe(this, new Observer<Token>() {
            @Override
            public void onChanged(Token token) {
                updateUI(token);
            }
        });
    }

    private void updateUI(Token token) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (token != null) {
                    open.setText(String.valueOf(token.getOpen()));
                    close.setText(String.valueOf(token.getClose()));
                    high.setText(String.valueOf(token.getHigh()));
                    low.setText(String.valueOf(token.getLow()));
                    ask.setText(String.valueOf(token.getAsk()));
                    bid.setText(String.valueOf(token.getBid()));
                    cap.setText(String.format("%.4f", token.getAveragePrice()));
                    vol.setText(String.format("%.4f", token.getVolume()));
                    name.setText(token.getLogoName());

                    double now = (token.getAsk() + token.getBid()) / 2;
                    String formattedNow = String.format("%.4f", now);
                    price.setText(formattedNow);

                    double a;
                    DecimalFormat decimalFormat = new DecimalFormat("#.####");

                    if (token.getAsk() >= token.getOpen()) {
                        a = now - token.getOpen();
                        double b = a * 100 / token.getOpen();

                        String formattedA = decimalFormat.format(a);
                        String formattedB = decimalFormat.format(b);
                        percent.setText(String.format("+%s(+%s%%)", formattedA, formattedB));
                    } else {
                        a = token.getOpen() - now;
                        double b = a * 100 / token.getOpen();

                        String formattedA = decimalFormat.format(a);
                        String formattedB = decimalFormat.format(b);
                        percent.setText(String.format("-%s(-%s%%)", formattedA, formattedB));
                        percent.setTextColor(Color.parseColor("#FF5733"));
                    }
                }
            }
        });
    }

}

