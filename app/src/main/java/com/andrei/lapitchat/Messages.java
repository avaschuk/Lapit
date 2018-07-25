package com.andrei.lapitchat;

public class Messages {

    private String message, type, from, seen;
    private long time;

    public Messages() {
    }

    public Messages(String message, String type, String from, String seen, long time) {
        this.message = message;
        this.type = type;
        this.from = from;
        this.seen = seen;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public String toString() {
        return "Messages{" +
                "message='" + message + '\'' +
                ", type='" + type + '\'' +
                ", from='" + from + '\'' +
                ", seen=" + seen +
                ", time=" + time +
                '}';
    }
}
