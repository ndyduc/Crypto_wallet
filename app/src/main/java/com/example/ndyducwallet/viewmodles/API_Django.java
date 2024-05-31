package com.example.ndyducwallet.viewmodles;

import com.example.ndyducwallet.modles.Django;
import com.example.ndyducwallet.modles.Tokens_Data;
import com.example.ndyducwallet.modles.Users;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface API_Django {
    @POST("get_infor_home")
    Call<Django> getUserInfo(@Body JsonObject userId);

    @POST("register")
    Call<Django> register(@Body JsonObject user);

    @POST("confirm")
    Call<Django> confirm(@Body JsonObject user);

    @POST("resend_email")
    Call<Django> resend_email(@Body JsonObject status);

    @POST("get_email_by_phone")
    Call<Django> get_email_by_phone(@Body JsonObject user);

    @POST("forgot_password")
    Call<Django> forgot_password(@Body JsonObject status);

    @POST("get_daily_prices")
    Call<Django> get_daily_prices(@Body JsonObject token);

    @GET("get_logo_tokens")
    Call<List<Tokens_Data>>  get_logo_tokens();

    @POST("get_full_symbol")
    Call<Django> get_full_symbol(@Body JsonObject symbol);
}
