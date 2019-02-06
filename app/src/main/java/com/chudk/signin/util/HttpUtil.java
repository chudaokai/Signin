package com.chudk.signin.util;


import com.chudk.signin.entity.RequestEntity.ARequestEntity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtil {

    public static void doRequest(ARequestEntity requestEntity){
        if(requestEntity == null)
            return ;
        if(requestEntity.getQueryMethod() == null ||"".equals(requestEntity.getQueryMethod()))
            return ;
        if("GET".equals(requestEntity.getQueryMethod())){
            doGet(requestEntity);
        }else
            doPost(requestEntity);
    }

    public static void doGet(ARequestEntity requestEnty){

        try{
            OkHttpClient httpClient = new OkHttpClient.Builder().cookieJar(new CookieJar() {
                @Override
                public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                    requestEnty.getQueryCookies().put(url.host(),cookies);
                }

                @Override
                public List<Cookie> loadForRequest(HttpUrl url) {
                    List<Cookie> list = requestEnty.getQueryCookies().get(url.host());
                    return list ==null ?new ArrayList<Cookie>():list;
                }
            }).build();
            Request request = new Request.Builder().url(requestEnty.getRequestUrl())
                    .get().build();
            Call call = httpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    requestEnty.setQueryResponseState(9);
                    requestEnty.setQueryResponse("{}");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    requestEnty.setQueryResponseState(1);
                    requestEnty.setQueryResponse(response.body().string());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void doPost(ARequestEntity requestEnty){

        try{
            OkHttpClient httpClient = new OkHttpClient.Builder().cookieJar(new CookieJar() {
                @Override
                public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                    requestEnty.getQueryCookies().put(url.host(),cookies);
                }

                @Override
                public List<Cookie> loadForRequest(HttpUrl url) {
                    List<Cookie> list = requestEnty.getQueryCookies().get(url.host());
                    return list ==null ?new ArrayList<Cookie>():list;
                }
            }).build();
            FormBody.Builder builder = new FormBody.Builder();
            if (requestEnty.getQueryBody() != null && !requestEnty.getQueryBody().isEmpty()) {
                for (Map.Entry<String, String> ent :
                        requestEnty.getQueryBody().entrySet()) {
                    builder.add(ent.getKey(),ent.getValue());
                }
            }
            FormBody body = builder.build();

            Request request = new Request.Builder().url(requestEnty.getRequestUrl())
                    .post(body).build();
            Call call = httpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    requestEnty.setQueryResponseState(9);
                    requestEnty.setQueryResponse("{}");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    requestEnty.setQueryResponseState(1);
                    requestEnty.setQueryResponse(response.body().string());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
