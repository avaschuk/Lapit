package com.andrei.lapitchat;

public class Conv {
    public String seen;
    public long timestamp;

    public Conv(String seen, long timestamp) {
        this.seen = seen;
        this.timestamp = timestamp;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
