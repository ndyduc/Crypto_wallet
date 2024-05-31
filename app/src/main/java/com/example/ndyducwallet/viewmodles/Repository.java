package com.example.ndyducwallet.viewmodles;

public class Repository {
    private static Repository instance;
    private int userId;
    private String userPhone, userEmail, userStatus, userName, currenttoken, chart, url_logo;

    private Repository() {
        userId = -1 ;
        userPhone = "";
        userEmail = "";
        userStatus = "";
        userName = "";
        currenttoken = "";
        chart = "";
        url_logo = "";
    }

    public void resetToDefault() {
        userId = -1;
        userPhone = "";
        userEmail = "";
        userStatus = "";
        userName = "";
        currenttoken = "";
        chart = "";
        url_logo = "";
    }

    public static synchronized Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    public String getUrl_logo(){return url_logo;}

    public void setUrl_logot(String url_logo){this.url_logo = url_logo;}

    public String getChart(){return chart;}

    public void setChart(String ischange){this.chart = chart;}

    public String getCurrenttoken(){return currenttoken;}

    public void setCurrenttoken(String currenttoken){this.currenttoken = currenttoken;}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName(){return userName;}

    public void setUserName(String userName){this.userName = userName;}

    public String getUserPhone(){return userPhone;}

    public void setUserPhone(String userPhone){this.userPhone = userPhone;}

    public String getUserEmail(){return userEmail;}

    public void setUserEmail(String userEmail){this.userEmail = userEmail;}

    public String getUserStatus(){return userStatus;}

    public void setUserStatus(String userStatus){this.userStatus = userStatus;}
}
