package com.chudk.signin.entity.RequestEntity;

import java.util.HashMap;
import java.util.Map;

public class LoginRequestEntity extends ARequestEntity {
    private String loginid;
    private String password;
    public LoginRequestEntity(String loginid,String password) {
        this.loginid = loginid;
        this.password = password;
        this.setQueryMethod("POST");
        this.setQueryUrl("/client.do");

        Map<String,String> queryString = new HashMap<String, String>();
        queryString.put("method","login");
        queryString.put("udid","F5995898-549D-491B-A595-3E842EF3F698");
        queryString.put("token","64a3f4174cf517c1c9c9d7fd942cdc0acb0e86b67d14217342523dacee018d74");
        queryString.put("language","zh-Hans");
        queryString.put("country","CN");
        queryString.put("isneedmoulds","1");
        queryString.put("clienttype","iPhone");
        queryString.put("clientver","6.5.64");
        queryString.put("clientos","iOS");
        queryString.put("clientosver","12.0.1");
        queryString.put("authcode","");
        queryString.put("dynapass","");
        queryString.put("tokenpass","");
        queryString.put("clientChannelId","");
        queryString.put("clientuserid","121c83f760000877532");
        this.setQueryString(queryString);

        Map<String,String> body = new HashMap<String, String>();
        body.put("loginid",loginid);
        body.put("password",password);
        body.put("isFromSunEmobile","1");
        this.setQueryBody(body);
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void changeLoginUser(){
        Map<String,String> body = this.getQueryBody();
        body.put("loginid",this.loginid);
        body.put("password",this.password);
    }
}
