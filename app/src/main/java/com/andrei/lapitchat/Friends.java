package com.andrei.lapitchat;

public class Friends {

    private String date;

    private String userName;

    private String status;

    private String thumb_image;

    private String online;

    public Friends() {
    }

    public Friends(String date, String userName, String thumb_image, String online) {
        this.date = date;
        this.userName = userName;
        this.thumb_image = thumb_image;
        this.online = online;
    }

    public Friends(String date, String userName, String status, String thumb_image, String online) {
        this.date = date;
        this.userName = userName;
        this.status = status;
        this.thumb_image = thumb_image;
        this.online = online;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getThumb_image() {
        return thumb_image;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    @Override
    public String toString() {
        return "Friends{" +
                "date='" + date + '\'' +
                ", userName='" + userName + '\'' +
                ", status='" + status + '\'' +
                ", thumb_image='" + thumb_image + '\'' +
                ", online='" + online + '\'' +
                '}';
    }
}
