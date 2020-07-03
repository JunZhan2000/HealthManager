package com.example.healthapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.healthapplication.model.User;
import com.example.healthapplication.util.FileUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Register2Activity extends AppCompatActivity implements View.OnClickListener{
    private CircleImageView icon;
    private Button changeIconBt;
    private EditText userNameET;
    private EditText ageEt;
    private int gender = 0;     //0为男 1为女
    private TextView genderManTv;
    private TextView genderFemaleTv;
    private Button submitBt;

    private User user = User.getInstance();


    private boolean isChange = false;
    private String iconPath;

    private final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_register_step_two);

        icon = findViewById(R.id.register_icon);
        changeIconBt = findViewById(R.id.change_icon);
        userNameET = findViewById(R.id.et_register_username);
        ageEt = findViewById(R.id.et_register_age);
        genderManTv = findViewById(R.id.tv_register_man);
        genderFemaleTv = findViewById(R.id.tv_register_female);
        submitBt = findViewById(R.id.bt_register_submit);

        icon.setOnClickListener(this);
        Glide.with(this).load("http://47.100.32.161:8080/upload/默认头像.jpg").into(icon);
        changeIconBt.setOnClickListener(this);
        genderFemaleTv.setOnClickListener(this);
        genderManTv.setOnClickListener(this);
        submitBt.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_icon:
            case R.id.change_icon:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    //未授权，申请授权(从相册选择图片需要读取存储卡的权限)
                    ActivityCompat.requestPermissions(Register2Activity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, RC_CHOOSE_PHOTO);
                } else {
                    //已授权，获取照片
                    choosePhoto();
                }
                break;
            case R.id.tv_register_female:
                gender = 1;
                genderFemaleTv.setBackgroundResource(R.color.account_lock_bg);
                genderManTv.setBackgroundResource(0);
                break;
            case R.id.tv_register_man:
                gender = 0;
                genderFemaleTv.setBackgroundResource(0);
                genderManTv.setBackgroundResource(R.color.account_lock_bg);
                break;
            case R.id.bt_register_submit:
                if(isChange){
                    //修改头像
                    final File file = new File(iconPath);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            OkHttpClient mOkHttpClient = new OkHttpClient();
                            MultipartBody.Builder builder = new MultipartBody.Builder();
                            builder.setType(MultipartBody.FORM)
                                    .addFormDataPart("avatar", "img" + "_" + System.currentTimeMillis() + ".jpg",
                                            RequestBody.create(MEDIA_TYPE_PNG, file));



                            RequestBody requestBody = builder.build();
                            Request.Builder reqBuilder = new Request.Builder();

                            Request request = reqBuilder
                                    .url("http://47.100.32.161:8080/POST/avatar")
                                    .addHeader("Authorization", User.getInstance().getToken())
                                    .post(requestBody)
                                    .build();

                            try{
                                Response response = mOkHttpClient.newCall(request).execute();
                                Log.d("logaa", "响应码 " + response.code());
                                String data = response.body().string();
                                JSONObject jsonObject = new JSONObject(data);
                                String url = jsonObject.getString("data");
                                user.setAvatar_url(url);
                            } catch (Exception e) {

                                e.printStackTrace();

                            }

                        }
                    }).start();

                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient client = new OkHttpClient();
                        String json = "{\"name\":" + "\""  + userNameET.getText().toString()
                                + "\"" + ",\"age\":" +"\""+ageEt.getText().toString()
                                +"\"," + "\"gender\":" +"\""+ String.valueOf(gender)+"\""+
                                "}";
                        Log.d("register",json);
                        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
                        Request request = new Request.Builder().url("http://47.100.32.161:8080/UPDATE/user")
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

                user.setName(userNameET.getText().toString());
                user.setAge(Integer.parseInt(ageEt.getText().toString()));
                user.setGender(gender);

                Intent intent = new Intent(Register2Activity.this, MainActivity.class);
                startActivity(intent);
                break;


                default:
                    break;

        }

    }

    public static final int RC_CHOOSE_PHOTO = 2;

    private void choosePhoto() {
        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intentToPickPic, RC_CHOOSE_PHOTO);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RC_CHOOSE_PHOTO:   //相册选择照片权限申请返回
                choosePhoto();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RC_CHOOSE_PHOTO:
                isChange = true;
                Uri uri = data.getData();
                iconPath  = FileUtil.getFilePathByUri(this, uri);
                Glide.with(this).load(iconPath).into(icon);

                break;
        }




    }



}
