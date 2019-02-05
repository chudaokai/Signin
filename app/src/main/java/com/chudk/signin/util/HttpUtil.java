package com.chudk.signin.util;


import com.chudk.signin.entity.RequestEntity.ARequestEntity;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtil {

    public static String doRequest(ARequestEntity requestEntity){
        if(requestEntity == null)
            return "{}";
        if(requestEntity.getQueryMethod() == null ||"".equals(requestEntity.getQueryMethod()))
            return "{}";
        if("GET".equals(requestEntity.getQueryMethod())){
            return doGet(requestEntity);
        }else
            return doPost(requestEntity);
    }

    public static String doGet(ARequestEntity requestEnty){
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String responseJSON = "";
        try{
            httpClient = HttpClients.custom().setDefaultCookieStore(requestEnty.getQueryCookies()).build();
            HttpGet httpGet = new HttpGet(requestEnty.getRequestUrl());
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            responseJSON = EntityUtils.toString(entity);
        }
        catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return responseJSON;
    }

    public static String doPost(ARequestEntity requestEntity){
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String responseJSON = "";
        try {
            httpClient = HttpClients.custom().setDefaultCookieStore(requestEntity.getQueryCookies()).build();
            HttpPost httpPost = new HttpPost(requestEntity.getRequestUrl());
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            if (requestEntity.getQueryBody() != null && !requestEntity.getQueryBody().isEmpty()) {
                List<NameValuePair> list = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> ent :
                        requestEntity.getQueryBody().entrySet()) {
                    list.add(new BasicNameValuePair(ent.getKey(), ent.getValue()));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(list,"utf-8"));
            }
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            responseJSON = EntityUtils.toString(entity);
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            // 关闭资源
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return responseJSON;
    }
}
