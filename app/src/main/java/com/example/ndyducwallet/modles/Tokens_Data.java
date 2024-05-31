package com.example.ndyducwallet.modles;

public class Tokens_Data {
    private String symbol;
    private String url;

    public Tokens_Data(String symbol, String url) {
        this.symbol = symbol;
        this.url = url;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getLogoUrl() {
        return url;
    }

    public void setLogoUrl(String url) {
        this.url = url;
    }
}
