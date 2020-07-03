package com.example.healthapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.healthapplication.Adapter.CtReportAdapter;
import com.example.healthapplication.model.HttpCallbackListener;
import com.example.healthapplication.model.JudgeRecord;
import com.example.healthapplication.model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ShowCtRecordActivity extends AppCompatActivity implements HttpCallbackListener {
    private int page = 0;
    public  static  final int ADDRECORD = 1;
    private ArrayList<JudgeRecord> recordList = new ArrayList();

    private RecyclerView recyclerView;
    private CtReportAdapter adapter;

    int state = 0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ct_record);
        recyclerView = findViewById(R.id.ct_recyclerview);

        TextView backButon;
        backButon= findViewById(R.id.back);
        backButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView title;
        title = findViewById(R.id.title);
        title.setText("CT诊断记录");

        askForOkhttp();
        adapter = new CtReportAdapter(this,recordList,"CTRecord");

        recyclerView.setAdapter(adapter);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int position = linearLayoutManager.findLastVisibleItemPosition();
                if(position == (recordList.size()-1)){
                    askForOkhttp();
                }
            }
        });

        CtReportAdapter.AlterClickListener onItemActionClick = new CtReportAdapter.AlterClickListener() {

            @Override
            public void OnAlterClick(JudgeRecord bean_init, int position) {
                JudgeRecord judgeRecord = bean_init;
                Intent intent = new Intent(ShowCtRecordActivity.this,AnalyzeDetailActivity.class);


                intent.putExtra("choice",1);
                intent.putExtra("time",2);
                intent.putExtra("img", judgeRecord.getPicture_url());
                intent.putExtra("answer", judgeRecord.getAnswer());
                startActivity(intent);

            }

        };

        adapter.setAlterClickListener(onItemActionClick);


    }

    private void askForOkhttp(){
        if(state == 1)
            return;
        page = page+1;

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String url = url_base+"/GET/CTRecord?pageNum=" + page;
                    Request request = new Request.Builder().url(url).addHeader("Authorization", User.getInstance().getToken()).build();
                    Response response = client.newCall(request).execute();
                    String data = response.body().string();

                    JSONObject jsonObject = new JSONObject(data);
                    JSONArray array = jsonObject.getJSONArray( "data" );
                    String s2=array.toString();
                    Log.d("logaa",s2);

                    Gson gson = new Gson();
                    List<JudgeRecord> answerReports = gson.fromJson(s2, new TypeToken<List<JudgeRecord>>(){}.getType());

                    if(answerReports.size() >0){
                        Message message = new Message();
                        message.what = ADDRECORD;
                        handler.sendMessage(message);
                    }else{
                        state = 1;
                    }

                    for(JudgeRecord judgeRecord : answerReports){
                        recordList.add(judgeRecord);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private Handler handler = new Handler(){
        public void handleMessage(Message message){
            switch (message.what){
                case ADDRECORD:
                    adapter.notifyDataSetChanged();

                    break;
                default:
                    break;
            }
        }
    };


}
