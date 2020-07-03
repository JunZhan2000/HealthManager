package com.example.healthapplication;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.healthapplication.Adapter.AnswerAdapter;
import com.example.healthapplication.model.AnswerReport;
import com.example.healthapplication.model.HttpCallbackListener;
import com.example.healthapplication.model.User;
import com.example.healthapplication.MyView.LoadMoreListView;
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

public class ShowQaRecordActivity extends AppCompatActivity implements HttpCallbackListener {

    private LoadMoreListView answerLv;
    private AnswerAdapter adapter;
    private List<AnswerReport> reportList = new ArrayList<>();
    private int page = 0;
    public  static  final int ADDRECORD = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_qa_record);

        answerLv = findViewById(R.id.lvTrace);

        TextView backButon;
        backButon= findViewById(R.id.back);
        backButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        askForOkhttp();

        adapter = new AnswerAdapter(this, reportList);
        answerLv.setAdapter(adapter);

        answerLv.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onloadMore() {
                loadMore();
            }
        });
    }




    private void askForOkhttp(){
        //{{base_url}}/GET/QARecord?pageNum=1&pageSize=10
        page = page+1;

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    String url = url_base+"/GET/QARecord?pageNum=" + page;
                    Request request = new Request.Builder().url(url).addHeader("Authorization", User.getInstance().getToken()).build();
                    Response response = client.newCall(request).execute();
                    String data = response.body().string();
                    Log.d("logaa",response.code()+"data:"+data);
                    JSONObject jsonObject = new JSONObject(data);

                    //Object dataObject = jsonObject.getJSONObject("data");
                    JSONArray array = jsonObject.getJSONArray( "data" );
                    String s2=array.toString();
                    Log.d("logaa",s2);

                    Gson gson = new Gson();
                    List<AnswerReport> answerReports = gson.fromJson(s2, new TypeToken<List<AnswerReport>>(){}.getType());

                    if(answerReports.size() >0){
                        Message message = new Message();
                        message.what = ADDRECORD;
                        handler.sendMessage(message);
                    }

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

    private Handler handler = new Handler(){
        public void handleMessage(Message message){
            switch (message.what){
                case ADDRECORD:
                    adapter.notifyDataSetChanged();
                    answerLv.setLoadCompleted();
                    break;
                default:
                    break;
            }
        }
    };

    private void loadMore() {
        askForOkhttp();
    }
}
