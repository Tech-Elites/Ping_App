package com.example.ping;

public class CompanionSwitchClass {
    private String name;
    private boolean status;

    public CompanionSwitchClass(String name, boolean status) {
        this.name = name;
        this.status = status;
    }

    public CompanionSwitchClass() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
