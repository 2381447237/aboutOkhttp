package com.okhttp.zhengbin.okhttpdemo.model;

import java.io.Serializable;

/**
 * Created by ZHengBin on 2017/9/21.
 */

public class User implements Serializable{

    private String userName;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
