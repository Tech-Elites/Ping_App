package com.example.ping;

import java.util.HashMap;

public class Ping {
    private String desc,address;
    private String lat,lng;
    private String visible;

    public String getVisible() {
        return visible;
    }

    public Ping(String visible, String desc, String address, String lat, String lng) {
        this.desc = desc;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.visible = visible;
    }
    Ping()
    {}

    public String getDesc() {
        return desc;
    }

    public String getAddress() {
        return address;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }
    public HashMap<String,String> returnPing()
    {
        HashMap<String,String> temp=new HashMap<>();
        temp.put("desc",desc);
        temp.put("visible",visible);
        temp.put("lat",lat);
        temp.put("lng",lng);
        temp.put("address",address);
        return temp;
    }

}
