package com.example.web.utils;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpUtil {

    //get方法
    public static String get(String url) {
        //1.获得一个httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //2.生成一个get请求
        HttpGet httpget = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            //3.执行get请求并返回结果
            response = httpclient.execute(httpget);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        String result = null;
        try {
            //4.处理结果，这里将结果返回为字符串
            assert response != null;
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    //生成get方法的url
    public static String generateGetUrl(String baseUrl, String parameterName, String parameterValue){
        if(parameterName == null && parameterValue == null){
            return baseUrl;
        }
        if(parameterName == null || parameterValue == null){
            throw new IllegalArgumentException("参数错误");
        }
        StringBuilder stringBuilder = new StringBuilder(baseUrl+"?");
        stringBuilder.append(parameterName+"=");
        stringBuilder.append(parameterValue);

        return stringBuilder.toString();
    }

    //生成get方法的url
    public static String generateGetUrl(String baseUrl, String[] parametersName, String[] parametersValue){
        if(parametersName == null && parametersValue == null){
            return baseUrl;
        }
        if((parametersName == null || parametersValue == null)
                || (parametersName.length != parametersValue.length)){
            throw new IllegalArgumentException("参数错误");
        }
        StringBuilder stringBuilder = new StringBuilder(baseUrl+"?");
        for(int i = 0; i < parametersName.length; i++){
            stringBuilder.append(parametersName[i]+"=");
            stringBuilder.append(parametersValue[i]);
            if(i != parametersName.length-1){
                stringBuilder.append("&");
            }
        }

        return stringBuilder.toString();
    }
}

