package com.example.healthapplication.model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.healthapplication.QandAFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpQA {
    public  static  final int ADDRECORD = 1;
    public void askForOkhttp(final List reportList, final int page){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String url = "http://47.100.32.161:8080/GET/QARecord?pageNum=" + page;
                    Request request = new Request.Builder().url(url).addHeader("Authorization", User.getInstance().getToken()).build();
                    Response response = client.newCall(request).execute();
                    String data = response.body().string();
                    Log.d("logaa",response.code()+"data:"+data);
                    JSONObject jsonObject = new JSONObject(data);

                    //Object dataObject = jsonObject.getJSONObject("data");
                    JSONArray array = jsonObject.getJSONArray( "data" );
                    String s2=array.toString();


                    Gson gson = new Gson();
                    List<AnswerReport> answerReports = gson.fromJson(s2, new TypeToken<List<AnswerReport>>(){}.getType());
                    for(AnswerReport answerReport: answerReports){
                        reportList.add(answerReport);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
