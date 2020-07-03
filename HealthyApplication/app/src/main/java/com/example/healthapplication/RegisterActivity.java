package com.example.healthapplication;


import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthapplication.model.HttpCallbackListener;
import com.example.healthapplication.model.HttpUser;
import com.example.healthapplication.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, HttpCallbackListener {

    private EditText phoneEt;
    private EditText passwordEt;
    private EditText authoEt;
    private int code;
    private HttpUser httpUser = HttpUser.getInstance();

    private TextView sendMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_register_step_one);

        initview();
    }

    private void initview(){
        findViewById(R.id.ib_navigation_back).setOnClickListener(this);
        findViewById(R.id.bt_register_submit).setOnClickListener(this);
        sendMessage = findViewById(R.id.tv_register_sms_call);
        sendMessage.setOnClickListener(this);

        phoneEt = findViewById(R.id.et_register_phone);
        passwordEt = findViewById(R.id.et_register_pwd);
        authoEt = findViewById(R.id.et_register_auth_code);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_navigation_back:
                finish();
                break;
            case R.id.tv_register_sms_call:
                sendMessage.setText("已发送");
//                try {
//                    httpUser.sendSms(phoneEt.getText().toString());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            OkHttpClient client = new OkHttpClient();
                            String url = url_base + "/sendSms?phone=" + phoneEt.getText().toString();
                            Request request = new Request.Builder().url(url).build();
                            Response response = client.newCall(request).execute();
                            String data = response.body().string();
                            JSONObject jsonObject = new JSONObject(data);
                            code = Integer.parseInt(jsonObject.getString("data"));
                            Log.d("reg",data);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

           break;
            case R.id.bt_register_submit:
                //httpUser.regist(this,Integer.parseInt(authoEt.getText().toString()),phoneEt.getText().toString(),passwordEt.getText().toString());

                if(code == Integer.parseInt(authoEt.getText().toString())) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            OkHttpClient client = new OkHttpClient();
                            String json = "{\"phone\":" + "\"" + phoneEt.getText().toString() + "\"" + ",\"password\":" + "\"" + passwordEt.getText().toString() + "\"}";
                            RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
                            Request request = new Request.Builder().url(url_base+"/signIn").post(requestBody).build();
                            try {
                                Response response = client.newCall(request).execute();
                                String data = response.body().string();
                                JSONObject jsonObject = new JSONObject(data);

                                Object dataObject = jsonObject.getJSONObject("data");
                                Log.d("logaa",((JSONObject) dataObject).toString());
                                String s2=dataObject.toString();
                                JSONObject userDataJson = new JSONObject(s2);

                                String token = userDataJson.getString("Authorization");
                                Object userInfoObject = ((JSONObject) dataObject).getJSONObject("userInfo");
                                String infoString =userInfoObject.toString();
                                JSONObject infoJson = new JSONObject(infoString);

                                String userName = infoJson.getString("name");

                                String phone = infoJson.getString("phone");
                                String avatar = infoJson.getString("avatar_url");

                                User user = User.getInstance(avatar, userName, phone, token);

                                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);

                                startActivity(intent);


                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }).start();
                }
                else{
                    Toast.makeText(RegisterActivity.this, "验证码输入错误", Toast.LENGTH_SHORT).show();
                }
                
                break;

        }
    }

}