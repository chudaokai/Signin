package com.chudk.signin.entity.RequestEntity;

import java.util.HashMap;
import java.util.Map;

public class CheckInRequest extends ARequestEntity {
    private String method="checkin";
    private String type;
    private String latlng;
    private String addr;
    private String sessionkey;
    private String wifiMac;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLatlng() {
        return latlng;
    }

    public void setLatlng(String latlng) {
        this.latlng = latlng;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getSessionkey() {
        return sessionkey;
    }

    public void setSessionkey(String sessionkey) {
        this.sessionkey = sessionkey;
    }

    public String getWifiMac() {
        return wifiMac;
    }

    public void setWifiMac(String wifiMac) {
        this.wifiMac = wifiMac;
    }

    public CheckInRequest(){
        this.setQueryMethod("GET");
        this.setQueryUrl("/client.do");
    }

    @Override
    public void revalueQueryString() {
        Map<String,String> map = new HashMap<String,String>();
        map.put("method",method);
        map.put("type",getType());
        map.put("sessionkey",getSessionkey());
        map.put("latlng",getLatlng());
        map.put("addr",getAddr());
        map.put("wifiMac",getWifiMac());
        setQueryString(map);
    }
}
