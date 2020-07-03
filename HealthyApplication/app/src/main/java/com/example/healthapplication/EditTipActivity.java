package com.example.healthapplication;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.healthapplication.model.Tip;
import com.example.healthapplication.util.AlarmManagerUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditTipActivity extends AppCompatActivity {


    private EditText ed_tipTitle;
    private String title;
    private Tip tip;
    private AlertDialog alertDialog3; //多选框
    private RelativeLayout button3;
    final List cycledata = new ArrayList();
    private Calendar c=Calendar.getInstance();

    private boolean change = false;

    private boolean[] cycle;

    private TextView textView1;
    private TextView textView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        TextView backButon;
        backButon= findViewById(R.id.back);
        backButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cycle = new boolean[]{false,false,false,false,false,false,false};
        textView1 = findViewById(R.id.text1);
        textView2 = findViewById(R.id.text2);

        ed_tipTitle = findViewById(R.id.thingsToDo);

        button3 = findViewById(R.id.cycle);
        TextView save = findViewById(R.id.save);
        Intent intent = getIntent();
        tip = (Tip) intent.getSerializableExtra("init");

        if(tip != null){
            cycle = tip.getBooleans();
            ed_tipTitle.setText(tip.getContent());
            textView1.setText(tip.getTime());
            textView2.setText(printCycle(cycle));
        }

        else {
            tip = new Tip();
        }




        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMutilAlertDialog();
            }
        });




        save.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v){
                tip.setContent(ed_tipTitle.getText().toString());
                tip.setTime(c);
                tip.setBooleans(cycle);

                for (int i = 0; i < 7; i++) {
                    if(tip.getBooleans()[i] == true) {

                        AlarmManagerUtil.setAlarm(EditTipActivity.this, 2, tip.getCalendarTime().get(Calendar.HOUR_OF_DAY)
                                , tip.getCalendarTime().get(Calendar.MINUTE), i, i, tip.getContent(), 2);
                    }

                }


                Intent intent = new Intent ();
                intent.putExtra("tip_data",tip);
                setResult(RESULT_OK,intent);

                finish();

            }
        });

        RelativeLayout alarm = findViewById(R.id.alarm);

        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //获取系统当前时间

                Calendar calendar=Calendar.getInstance();
                int hourNow=calendar.get(Calendar.HOUR_OF_DAY);
                int minuteNow=calendar.get(Calendar.MINUTE);

                //弹出时间对话框

                TimePickerDialog timePickerDialog=new TimePickerDialog(EditTipActivity.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        //c为改了之后的时间
                        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        c.set(Calendar.MINUTE,minute);
                        tip.setTime(c);
                        textView1.setText(tip.getTime());
                    }

                },hourNow,minuteNow,true);

                timePickerDialog.show();

            }


        });



    }



    public void showMutilAlertDialog(){
        final String[] items = {"周一", "周二", "周三", "周四","周五","周六","周日"};

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("重复");
        /**
         *第一个参数:弹出框的消息集合，一般为字符串集合
         * 第二个参数：默认被选中的，布尔类数组
         * 第三个参数：勾选事件监听
         */
        alertBuilder.setMultiChoiceItems(items, cycle, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                if (isChecked){
                    Toast.makeText(EditTipActivity.this, "选择" + items[i], Toast.LENGTH_SHORT).show();
                    cycle[i] = true;
                }else {
                    Toast.makeText(EditTipActivity.this, "取消选择" + items[i], Toast.LENGTH_SHORT).show();
                    cycle[i] = false;
                }
            }
        });
        alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                textView2.setText(printCycle(cycle));
                alertDialog3.dismiss();
            }
        });

        alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog3.dismiss();
            }
        });


        alertDialog3 = alertBuilder.create();
        alertDialog3.show();
    }


    private String printCycle(boolean[] booleans){
        StringBuffer stringBuffer = new StringBuffer();
        for(int i = 0; i < 7; i++){
            if(booleans[i] == true) {
                stringBuffer.append("周");
                stringBuffer.append("" + (i+ 1));
                stringBuffer.append(" ");
            }
        }
        return stringBuffer.toString();

    }
}
