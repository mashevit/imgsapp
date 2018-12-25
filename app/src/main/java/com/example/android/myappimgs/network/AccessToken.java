package com.example.android.myappimgs.network;

import java.util.HashMap;
import java.util.Map;

public class AccessToken {

    private String accessToken;

    private String name;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}