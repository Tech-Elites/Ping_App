package com.example.ping;

public class UserNameIDClass {
    private String name,uid;

    public UserNameIDClass(String name, String uid) {
        this.name = name;
        this.uid = uid;
    }

    public UserNameIDClass() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
