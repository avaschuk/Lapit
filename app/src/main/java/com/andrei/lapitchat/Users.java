package com.andrei.lapitchat;

public class Users {

    private String name;

    private String status;

    private String image;

    public Users() {
    }

    public Users(String name, String status, String image) {
        this.name = name;
        this.status = status;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Users{" +
                "name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
