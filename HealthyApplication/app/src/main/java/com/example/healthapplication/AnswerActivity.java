package com.example.healthapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.healthapplication.model.Question;

public class AnswerActivity extends AppCompatActivity {

    private TextView backButton;
    private String answer;
    private Question question;

    private TextView questionTitle;
    private TextView questionText;
    private TextView answerTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        questionTitle = findViewById(R.id.answer_title);
        questionText = findViewById(R.id.answer_detail);
        answerTv = findViewById(R.id.answer_answer);

        Intent intent = getIntent();
        answer = intent.getStringExtra("answer");
        question = (Question) intent.getSerializableExtra("Question");

        answerTv.setText(answer);
        questionTitle.setText(question.getTitle());
        questionText.setText("科室："+question.getDeapartment()+"\n详情：" + question.getText());

        backButton = findViewById(R.id.answer_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnswerActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
