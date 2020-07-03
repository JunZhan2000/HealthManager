package com.example.healthapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.util.Log;

import com.example.healthapplication.util.AlarmManagerUtil;

public class AlarmReceiver extends BroadcastReceiver {

    private MediaPlayer mediaPlayer;

    private Vibrator vibrator;



    @Override

    public void onReceive(Context context, Intent intent) {

        // TODO Auto-generated method stub
        Log.d("alarm","ClorkActivity");

        String msg = intent.getStringExtra("msg");

        long intervalMillis = intent.getLongExtra("intervalMillis", 0);

        if (intervalMillis != 0) {

            AlarmManagerUtil.setAlarmTime(context, System.currentTimeMillis() + intervalMillis,

                    intent);

        }

        int flag = intent.getIntExtra("soundOrVibrator", 0);

        Intent clockIntent = new Intent(context, ClockAlarmActivity.class);

        clockIntent.putExtra("msg", msg);

        clockIntent.putExtra("flag", flag);

        clockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(clockIntent);

    }





}
