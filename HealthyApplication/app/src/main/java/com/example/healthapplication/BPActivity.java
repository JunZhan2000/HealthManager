package com.example.healthapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BPActivity extends AppCompatActivity {
    private EditText tallEt;
    private EditText weightEt;
    private Button button;
    private TextView resultTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bp);
        final CardView cardView = findViewById(R.id.cardView);


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
                int tall = Integer.parseInt(tallEt.getText().toString());
                int low = Integer.parseInt(weightEt.getText().toString());
                if(tall < 90 || low < 60)
                    resultTv.setText("您可能有低血压，若您在生活中出现因体位变化、进餐后出现头晕眼花、肢软乏力，心悸出冷汗等症状时，建议去医院新血管内科就诊");
                else if(tall > 140 || low > 90)
                    resultTv.setText("您可能患有高血压，若您在生活中出现头晕胀痛、阵发性眩晕、胸闷不适、四肢麻木等，应选择前往医院注意是否是高血压的信号。在生活中应注意改善生活方式。");
                else
                    resultTv.setText("您的身体状况很好！继续保持良好的生活习惯");
                cardView.setVisibility(View.VISIBLE);
            }
        });
    }
}
