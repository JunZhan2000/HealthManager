package com.example.healthapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.healthapplication.model.HttpCallbackListener;
import com.example.healthapplication.model.User;

import org.json.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AnalyzeDetailActivity extends AppCompatActivity implements HttpCallbackListener {
    private ImageView imageView;
    private TextView textView;
    private TextView answertv;
    private ProgressDialog progressDialog;

    private int type;
    private String answer;
    private String url;

    private final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static final int COMPLETE = 1000;
    private String[] title = new String[]{"淋巴切片诊断结果", "CT诊断结果"};
    private String[] urlPiece = new String[]{"/POST/CJRecord", "/POST/CTRecord"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_analyze_detail);


        imageView = findViewById(R.id.analyze_img);
        textView = findViewById(R.id.choice_tv);
        answertv = findViewById(R.id.analyze_answer);

        TextView backButon;
        backButon= findViewById(R.id.back);
        backButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Intent intent = getIntent();
        type = intent.getIntExtra("choice", 1);
        textView.setText(title[type]);

        int time = intent.getIntExtra("time",1);

        //新分析的
        if(time == 1){
            String image_path = intent.getStringExtra("path");
            Glide.with(this).load(image_path).into(imageView);

            sendHttp(image_path);

            progressDialog = new ProgressDialog(AnalyzeDetailActivity.this);
            progressDialog.setTitle("图片正在分析");
            progressDialog.setMessage("加载中……");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        //展示历史记录
        else if(time == 2){
            String url = intent.getStringExtra("img");
            String answer = intent.getStringExtra("answer");
            Glide.with(this).load(url_base + url);
            answertv.setText("检测结果："+ answer);

        }








    }


    private void sendHttp(String image_path){

        if (!TextUtils.isEmpty(image_path )) {


            final File file = new File(image_path );
            new Thread(new Runnable() {
                @Override
                public void run() {

                    OkHttpClient mOkHttpClient = new OkHttpClient();
                    MultipartBody.Builder builder = new MultipartBody.Builder();
                    builder.setType(MultipartBody.FORM)
                            .addFormDataPart("picture", "img" + "_" + System.currentTimeMillis() + ".jpg",
                                    RequestBody.create(MEDIA_TYPE_PNG, file));



                    RequestBody requestBody = builder.build();
                    Request.Builder reqBuilder = new Request.Builder();

                    Request request = reqBuilder
                            .url(url_base + urlPiece[type])
                            .addHeader("Authorization", User.getInstance().getToken())
                            .post(requestBody)
                            .build();

                    try{

                        Response response = mOkHttpClient.newCall(request).execute();
                        Log.d("logaa", "响应码 " + response.code());
                        String resultValue = response.body().string();
                        JSONObject jsonObject = new JSONObject(resultValue);

                        Object dataObject = jsonObject.getJSONObject("data");
                        String s2=dataObject.toString();
                        JSONObject userDataJson = new JSONObject(s2);

                        url = userDataJson.getString("picture_url");
                        answer =userDataJson.getString("answer");

                        //Thread.sleep(5000);

                        Message message = new Message();
                        message.what = COMPLETE;
                        handler.sendMessage(message);


                    } catch (Exception e) {

                        e.printStackTrace();

                    }

                }
            }).start();


        }
    }

    public Handler handler = new Handler(){
        public void handleMessage(Message message){
            switch (message.what){
                case COMPLETE:
                    progressDialog.dismiss();
                    answertv.setText("检测结果："+ answer);


                    break;
                default:
                    break;
            }
        }
    };

}
