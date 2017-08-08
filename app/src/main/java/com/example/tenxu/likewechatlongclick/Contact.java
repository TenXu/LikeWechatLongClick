package com.example.tenxu.likewechatlongclick;

import java.io.Serializable;

/**
 * Created by TenXu on 2017/8/1.
 */

public class Contact implements Serializable {
    private static final long serialVersionUID = 5016380118470444725L;
    private String header;
    private String name;
    private long lastTime;
    private String lastMsg;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }
}
