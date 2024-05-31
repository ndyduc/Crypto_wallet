package com.example.ndyducwallet.modles;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Django {
    @JsonProperty("user")
    public Users getUser() {
        return this.user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @JsonProperty("token")
    public Token getToken() { return this.token;}

    public  void setToken(Token token){this.token = token;}

    @JsonProperty("error")
    public String getError() {
        return this.error;
    }

    public void setError(String error){
        this.error = error;
    }

    @JsonProperty("status")
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("symbol")
    public String getSymbol(){return this.symbol;}

    private String symbol;
    private Tokens_Data logos;
    private Token token;
    private Users user;
    private String error;
    private String status;
}
