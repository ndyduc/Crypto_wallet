package com.example.ndyducwallet.modles;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Token {
    @JsonProperty("ask")
    private double ask;

    @JsonProperty("bid")
    private double bid;

    @JsonProperty("close")
    private double close;

    @JsonProperty("volume")
    private double volume;

    @JsonProperty("average_price")
    private double average_price;

    @JsonProperty("number_of_trades")
    private double number_of_trades;

    @JsonProperty("low")
    private double low;

    @JsonProperty("high")
    private double high;

    @JsonProperty("open")
    private double open;

    @JsonProperty("logo")
    private String logo;

    @JsonProperty("logo_name")
    private String logo_name;

    // Getter and Setter methods
    public double getAsk() {
        return this.ask;
    }

    public void setAsk(double ask) {
        this.ask = ask;
    }

    public double getBid() {
        return this.bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    public double getClose() {
        return this.close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getVolume() {
        return this.volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getAveragePrice() {
        return this.average_price;
    }

    public void setAveragePrice(double averagePrice) {
        this.average_price = averagePrice;
    }

    public double getNumberOfTrades() {
        return this.number_of_trades;
    }

    public void setNumberOfTrades(double number_of_trades) {
        this.number_of_trades = number_of_trades;
    }

    public double getLow() {
        return this.low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getHigh() {
        return this.high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getOpen() {
        return this.open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public String getLogo() {
        return this.logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLogoName() {
        return this.logo_name;
    }

    public void setLogoName(String logoName) {
        this.logo_name = logoName;
    }
}
