package com.example.healthapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class BMIActivity extends AppCompatActivity {
    private EditText tallEt;
    private EditText weightEt;
    private Button button;
    private TextView resultTv;
    private CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        cardView = findViewById(R.id.cardView);

        TextView backButon;
        backButon= findViewById(R.id.back);
        backButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tallEt = findViewById(R.id.tall);
        weightEt = findViewById(R.id.weight);
        button = findViewById(R.id.commit);
        resultTv = findViewById(R.id.result);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float tall = Float.parseFloat(tallEt.getText().toString()) /100;
                float weight = Float.parseFloat(weightEt.getText().toString());
                float bmi = (weight/tall)/tall;
                String state;
                if(bmi <= 18.4){
                    state = "偏瘦";
                }else if(bmi > 18.4 && bmi<23.9){
                    state = "正常";
                }else if(bmi > 24 && bmi <27.9){
                    state = "过重";
                }else
                    state = "肥胖";
                resultTv.setText("您的BMI值为"+bmi+"，身体状态为："+state);
                cardView.setVisibility(View.VISIBLE);

            }
        });


    }
}
