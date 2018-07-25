package com.andrei.lapitchat;

public class Messages {

    private String message, type;
    private boolean seen;
    private long time;

    public Messages() {
    }

    public Messages(String message, String type, boolean seen, long time) {
        this.message = message;
        this.type = type;
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

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Messages{" +
                "message='" + message + '\'' +
                ", type='" + type + '\'' +
                ", seen=" + seen +
                ", time=" + time +
                '}';
    }
}
