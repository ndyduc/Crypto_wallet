package com.example.ndyducwallet.viewmodles;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ndyducwallet.modles.Users;

public class Login_VM extends ViewModel {
    private final MutableLiveData<Integer> userId = new MutableLiveData<>();

    public void setUserId(int id) {
        userId.setValue(id);
        Log.d("Login_VM_JSON", "userId : " + userId.getValue());
    }

    public LiveData<Integer> getUserId() {
        return userId;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d("ViewModel_JSON", "ViewModel cleared");
    }
}
