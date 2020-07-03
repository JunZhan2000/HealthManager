package com.example.healthapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.healthapplication.model.HttpCallbackListener;
import com.example.healthapplication.model.User;
import com.example.healthapplication.util.FileUtil;

import org.json.JSONObject;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChangeInfoActivity extends AppCompatActivity implements View.OnClickListener, HttpCallbackListener {

    private RelativeLayout changeIcon;
    private RelativeLayout changeName;
    private RelativeLayout changeAge;
    private RelativeLayout changeGender;
    private RelativeLayout changePassword;


    private TextView name;
    private TextView age;
    private TextView gender;
    private TextView password;

    private ImageView icon;
    private String iconPath;
    private User user = User.getInstance();
    private final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private final static int COMPLETE = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);


        initView();
        initInfo();
    }

    private void initView() {
        changeIcon = findViewById(R.id.info_change_icon);
        changeIcon.setOnClickListener(this);

        changeName = findViewById(R.id.change_name);
        changeName.setOnClickListener(this);

        changeAge = findViewById(R.id.change_age);
        changeAge.setOnClickListener(this);

        changeGender = findViewById(R.id.change_gender);
        changeGender.setOnClickListener(this);

        changePassword = findViewById(R.id.change_password);
        changePassword.setOnClickListener(this);

        name = findViewById(R.id.name);
        icon = findViewById(R.id.icon);
        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);

        TextView backButon;
        backButon= findViewById(R.id.back);
        backButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void initInfo(){
        Glide.with(this).load(url_base+user.getAvatar_url()).into(icon);
        name.setText(user.getName());
        age.setText(""+user.getAge());
        gender.setText(user.getGender());
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(ChangeInfoActivity.this, ChangeDetailActivity.class);
        switch (v.getId()) {
            case R.id.info_change_icon:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    //未授权，申请授权(从相册选择图片需要读取存储卡的权限)
                    ActivityCompat.requestPermissions(ChangeInfoActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, RC_CHOOSE_PHOTO);
                } else {
                    //已授权，获取照片
                    choosePhoto();
                }
                break;
            case R.id.change_name:
                intent.putExtra("type",0);
                startActivity(intent);
                break;
            case R.id.change_age:
                intent.putExtra("type",1);
                startActivity(intent);
                break;
            case R.id.change_gender:
                intent.putExtra("type",2);
                startActivity(intent);
                break;
            case R.id.change_password:
                intent.putExtra("type",3);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onStart(){
        super.onStart();
        initInfo();

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
                Uri uri = data.getData();
                iconPath = FileUtil.getFilePathByUri(this, uri);
                File file = new File(iconPath);
                Glide.with(this).load(file).into(icon);
                sendIconRequest();

                break;
        }
    }

    private void sendIconRequest(){

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
                        .url(url_base+"/POST/avatar")
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

                    Message message = new Message();
                    message.what = COMPLETE;
                    handler.sendMessage(message);
                } catch (Exception e) {

                    e.printStackTrace();

                }

            }
        }).start();


    }

    private Handler handler = new Handler(){
        public void handleMessage(Message message){
            switch (message.what){
                case COMPLETE:
                    Glide.with(ChangeInfoActivity.this).load(url_base + user.getAvatar_url()).into(icon);

                    break;
                default:
                    break;
            }
        }
    };
}
