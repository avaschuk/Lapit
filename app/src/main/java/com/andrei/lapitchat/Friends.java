package com.andrei.lapitchat;

public class Friends {

    private String date;

    private String userName;

    private String status;

    private String thumb_image;

    private boolean isOnline;

    public Friends() {
    }

    public Friends(String date, String userName, String status, String thumb_image, boolean isOnline) {
        this.date = date;
        this.userName = userName;
        this.status = status;
        this.thumb_image = thumb_image;
        this.isOnline = isOnline;
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

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    @Override
    public String toString() {
        return "Friends{" +
                "date='" + date + '\'' +
                ", userName='" + userName + '\'' +
                ", status='" + status + '\'' +
                ", thumb_image='" + thumb_image + '\'' +
                ", isOnline=" + isOnline +
                '}';
    }
}
