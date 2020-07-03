package com.example.healthapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.healthapplication.model.HttpCallbackListener;
import com.example.healthapplication.model.User;

import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChangeDetailActivity extends AppCompatActivity implements HttpCallbackListener {
    private TextView titleTv;
    private EditText edit;
    private int type;
    private TextView saveBt;
    private User user = User.getInstance();

    private String[] title = new String[]{"name","age","gender","password"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_detail);
        titleTv = findViewById(R.id.title);
        edit = findViewById(R.id.edit);

        Intent intent = getIntent();
        type = intent.getIntExtra("type",1);

        switch (type){
            case 0:
                titleTv.setText("修改名字");
                edit.setText(user.getName());
                break;
            case 1:
                titleTv.setText("修改年龄");
                edit.setText(""+user.getAge());
                break;
            case 2:
                titleTv.setText("修改性别");
                edit.setText(user.getGender());
                break;
            case 3:
                titleTv.setText("修改密码");
                break;
                default:
                    break;
        }

        saveBt = findViewById(R.id.save);
        saveBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String x = edit.getText().toString();
                if(type == 0)
                    user.setName(x);
                else if(type == 1)
                    user.setAge(Integer.parseInt(x));
                else if(type == 2){
                    if(x.equals("男")){
                        x = "1";
                        user.setGender(1);
                    }else {
                        x = "2";
                        user.setGender(2);
                    }

                }
                postChangeInfo(x);


                finish();

            }
        });

    }

    private void postChangeInfo(final String x){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                String json = "{\""+ title[type] +"\":" + "\""+ x +"\"}";
                Log.d("register",json);
                RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
                Request request = new Request.Builder().url(url_base+"/UPDATE/user")
                        .addHeader("Authorization", User.getInstance().getToken())
                        .post(requestBody).build();
                try {
                    Response response = client.newCall(request).execute();
                    String data = response.body().string();Log.d("register",data);


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
