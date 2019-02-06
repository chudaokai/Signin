package com.chudk.signin.entity.RequestEntity;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;

public abstract class ARequestEntity implements IRequestEntity {
    private String queryMethod;
    private String queryUrl;
    private Map<String,String> queryString;
    private Map<String,String> queryBody;
    private HashMap<String, List<Cookie>> queryCookies = new HashMap<>();
    private String queryResponse;
    private int queryResponseState;

    public int getQueryResponseState() {
        return queryResponseState;
    }

    public void setQueryResponseState(int queryResponseState) {
        this.queryResponseState = queryResponseState;
    }

    public String getQueryResponse() {
        return queryResponse;
    }

    public void setQueryResponse(String queryResponse) {
        this.queryResponse = queryResponse;
    }

    public HashMap<String, List<Cookie>> getQueryCookies() {
        return queryCookies;
    }

    public void setQueryCookies(HashMap<String, List<Cookie>> queryCookies) {
        this.queryCookies = queryCookies;
    }

    public String getQueryMethod() {
        return queryMethod;
    }

    public void setQueryMethod(String queryMethod) {
        this.queryMethod = queryMethod;
    }

    public Map<String, String> getQueryString() {
        return queryString;
    }

    public void setQueryString(Map<String, String> queryString) {
        this.queryString = queryString;
    }

    public Map<String, String> getQueryBody() {
        return queryBody;
    }

    public void setQueryBody(Map<String, String> queryBody) {
        this.queryBody = queryBody;
    }

    public String getQueryUrl() {
        return queryUrl;
    }

    public void setQueryUrl(String queryUrl) {
        this.queryUrl = queryUrl;
    }
    /**
     * 获取完整的请求地址
     * @return
     */
    public String getRequestUrl(){
        String str = "";
        if (getQueryString() != null && !"".equals(getQueryString())) {
            StringBuilder sb = new StringBuilder();

            for (Map.Entry<String, String> enty : getQueryString().entrySet()) {
                if ("".equals(enty.getKey()))
                    sb.append(enty.getValue() + "&");
                else
                    sb.append(enty.getKey() + "=" + enty.getValue() + "&");
            }
            sb.deleteCharAt(sb.length()-1);
            str = "?"+sb.toString();
        }
        return BaseURL + getQueryUrl()+str;
    }

    public void revalueQueryString(){
    }
}
