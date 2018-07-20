package com.retrofit_recyclerview;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataData {
    private int Year;
    private int Month;
    private int date;

    @SerializedName("grant_type")
    @Expose
    private String grantType;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String passowrd;
    @SerializedName("domain-name")
    @Expose
    private String domainName;

    public DataData(String grantType, String username, String passowrd, String domainName) {
        this.grantType = grantType;
        this.username = username;
        this.passowrd = passowrd;
        this.domainName = domainName;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassowrd() {
        return passowrd;
    }

    public void setPassowrd(String passowrd) {
        this.passowrd = passowrd;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public int getYear() {
        return Year;
    }

    public int getMonth() {
        return Month;
    }

    public int getDate() {
        return date;
    }

    public DataData(int year, int month, int date) {
        Year = year;
        Month = month;
        this.date = date;
    }
}
