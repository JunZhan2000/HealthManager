package com.example.healthapplication.model;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.healthapplication.LogActivity;
import com.example.healthapplication.MainActivity;
import com.example.healthapplication.Register2Activity;
import com.example.healthapplication.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUser implements HttpCallbackListener {
    private static HttpUser httpUser;
    private int code = 0;
    private HttpUser(){}

    public static HttpUser getInstance(){
        if(httpUser == null)
            return new HttpUser();
        else
            return httpUser;
    }
    //登录
    public void loginRequest(final Context context, final String name, final String pwd) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                String json = "{\"phone\":" + "\""  + name + "\"" + ",\"password\":" +"\""+pwd+"\"}";
                RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
                Request request = new Request.Builder().url(url_base+"/login").post(requestBody).build();
                try {
                    Response response = client.newCall(request).execute();
                    String data = response.body().string();
                    JSONObject jsonObject = new JSONObject(data);

                    Object dataObject = jsonObject.getJSONObject("data");
                    Log.d("logaa",((JSONObject) dataObject).toString());
                    String s2=dataObject.toString();
                    JSONObject userDataJson = new JSONObject(s2);



                    String code = jsonObject.getString("code");
                    if(Integer.parseInt(code) == 200){
                        String token = userDataJson.getString("Authorization");
                        Object userInfoObject = ((JSONObject) dataObject).getJSONObject("userInfo");
                        String infoString =userInfoObject.toString();
                        JSONObject infoJson = new JSONObject(infoString);

                        String userName = infoJson.getString("name");
                        int gender = Integer.parseInt(infoJson.getString("gender"));
                        String phone = infoJson.getString("phone");
                        String avatar = infoJson.getString("avatar_url");
                        int age = Integer.parseInt(infoJson.getString("age"));
                        User user = User.getInstance(age, gender, avatar, userName, phone, token);

                        Intent intent = new Intent(context, MainActivity.class);

                        context.startActivity(intent);
                    }else {
                        Looper.prepare();
                        Toast.makeText(context, userDataJson.getString("message"), Toast.LENGTH_SHORT).show();
                        Looper.loop();
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

