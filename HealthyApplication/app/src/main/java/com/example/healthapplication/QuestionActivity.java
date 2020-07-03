package com.example.healthapplication;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.healthapplication.model.HttpCallbackListener;
import com.example.healthapplication.model.Question;
import com.example.healthapplication.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class QuestionActivity extends AppCompatActivity implements HttpCallbackListener {
    private TextView nextStep;

    private EditText edTttle;
    private EditText edText;
    private EditText edDeapartment;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        nextStep = findViewById(R.id.next_step);


        edTttle = findViewById(R.id.question_title);
        edText = findViewById(R.id.question_text);
        edDeapartment = findViewById(R.id.question_department);


        button = findViewById(R.id.start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Question q = new Question(edTttle.getText().toString(),edText.getText().toString(),edDeapartment.getText().toString());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient client = new OkHttpClient();
                        String json = "{\"q_department\":" + "\""  + q.getDeapartment() + "\"" + ",\"q_title\":" +"\""+q.getTitle()+"\",\"q_text\":\"" +q.getText() +"\"}";
                        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
                        Request request = new Request.Builder().url(url_base+"/POST/QARecord").addHeader("Authorization",User.getInstance().getToken()).post(requestBody).build();
                        try {
                            Response response = client.newCall(request).execute();
                            String data = response.body().string();
                            JSONObject jsonObject = new JSONObject(data);

                            Object dataObject = jsonObject.getJSONObject("data");
                            Log.d("logaa",((JSONObject) dataObject).toString());
                            String s2=dataObject.toString();
                            JSONObject userDataJson = new JSONObject(s2);
                            String answer = userDataJson.getString("answer");
                            Log.d("logaa",data);

                            Intent intent = new Intent(QuestionActivity.this, AnswerActivity.class);
                            intent.putExtra("answer", answer);
                            intent.putExtra("Question",q);

                            startActivity(intent);


                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }
        });

        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

    }
}
