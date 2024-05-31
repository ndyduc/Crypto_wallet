package com.example.ndyducwallet.viewmodles;

import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.ndyducwallet.modles.Django;
import com.example.ndyducwallet.modles.Token;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Market_ViewModel extends ViewModel {
    private MutableLiveData<Token> tokenLiveData;
    private String tokendefault;
    private Handler handler;

    public void setTokenDefault(String tokendefault) {
        this.tokendefault = tokendefault;
        if (tokenLiveData != null) {
            loadData(create_JsonObject_body(tokendefault));
        }
    }

    public LiveData<Token> getTokenLiveData() {
        if (tokenLiveData == null) {
            tokenLiveData = new MutableLiveData<>();
            JsonObject jsonObject = create_JsonObject_body(tokendefault);
            loadData(jsonObject);
        }
        return tokenLiveData;
    }

    public void startDataLoading() {
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadData(create_JsonObject_body(tokendefault));
                handler.postDelayed(this, 5000);
            }
        }, 5000);
    }

    public void stopDataLoading() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    private void loadData(JsonObject token) {
        API_Django apiService = Retrofit_client.getClient().create(API_Django.class);
        Call<Django> call = apiService.get_daily_prices(token);
        call.enqueue(new Callback<Django>() {
            @Override
            public void onResponse(Call<Django> call, Response<Django> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Django djangoResponse = response.body();
                    Token token = djangoResponse.getToken();
                    tokenLiveData.setValue(token);
                }
            }
            @Override
            public void onFailure(Call<Django> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private JsonObject create_JsonObject_body(String tokendefault) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token", tokendefault);
        return jsonObject;
    }
}
