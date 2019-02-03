package com.chudk.signin.entity.RequestEntity;

import java.util.HashMap;
import java.util.Map;

public class GetUserInfoRequest extends ARequestEntity {
    private final String method="getuser";
    private String sessionkey;

    public String getSessionkey() {
        return sessionkey;
    }

    public void setSessionkey(String sessionkey) {
        this.sessionkey = sessionkey;
    }

    public GetUserInfoRequest(String key){
        this.setSessionkey(key);
        this.setQueryUrl("/client.do");
        this.setQueryMethod("GET");

        Map<String,String> queryString = new HashMap<String,String>();
        queryString.put("method",method);
        queryString.put("sessionkey",key);
        this.setQueryString(queryString);
    }
}
