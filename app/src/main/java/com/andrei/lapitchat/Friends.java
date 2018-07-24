package com.andrei.lapitchat;

public class Friends {

    private String date;

    public Friends() {
    }

    public Friends(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Friends{" +
                "date='" + date + '\'' +
                '}';
    }
}
