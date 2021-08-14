package com.example.ping;

import java.util.HashMap;

public class UserClass {
    String userName;

    public UserClass() {
    }

    public UserClass(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public HashMap getHashMap(){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("name",userName);
        return hashMap;
    }
}
