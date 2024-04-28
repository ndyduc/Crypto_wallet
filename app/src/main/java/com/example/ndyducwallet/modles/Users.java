package com.example.ndyducwallet.modles;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Users{
    @JsonProperty("id")
    public int getId() {
        return this.id; }
    public void setId(int id) {
        this.id = id; }
    int id;
    @JsonProperty("phone")
    public String getPhone() {
        return this.phone; }
    public void setPhone(String phone) {
        this.phone = phone; }
    String phone;
    @JsonProperty("name")
    public String getName() {
        return this.name; }
    public void setName(String name) {
        this.name = name; }
    String name;
    @JsonProperty("email")
    public String getEmail() {
        return this.email; }
    public void setEmail(String email) {
        this.email = email; }
    String email;
    @JsonProperty("password")
    public String getPassword() {
        return this.password; }
    public void setPassword(String password) {
        this.password = password; }
    String password;
    @JsonProperty("limit")
    public Object getLimit() {
        return this.limit; }
    public void setLimit(Object limit) {
        this.limit = limit; }
    Object limit;
    @JsonProperty("fa")
    public Object getFa() {
        return this.fa; }
    public void setFa(Object fa) {
        this.fa = fa; }
    Object fa;
}
