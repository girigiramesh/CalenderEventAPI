package com.retrofit_recyclerview;

public class DataData {
    private int Year;
    private int Month;
    private int date;

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
