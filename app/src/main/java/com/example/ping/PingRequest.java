package com.example.ping;

import java.util.HashMap;

public class PingRequest {
    private String desc,address;
    private String lat,lng;
    private String name,userid;



    public String getName() {
        return name;
    }

    public String getUserid() {
        return userid;
    }


    public PingRequest(String desc, String address, String lat, String lng,String name,String userid) {
        this.desc = desc;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.name=name;
        this.userid=userid;

    }
    PingRequest()
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
    public HashMap<String,String> returnPingRequest()
    {
        HashMap<String,String> temp=new HashMap<>();
        temp.put("desc",desc);
        temp.put("lat",lat);
        temp.put("lng",lng);
        temp.put("address",address);
        temp.put("name",name);
        temp.put("userid",userid);
        return temp;
    }
}
