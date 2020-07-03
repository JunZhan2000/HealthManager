package com.example.healthapplication.model;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Tip extends DataSupport implements Serializable {
    private String content;
    int hour;
    int minute;
    boolean mon = false;
    boolean tue = false;
    boolean wed= false;
    boolean thurs = false;
    boolean fri= false;
    boolean sat= false;
    boolean sun=false;

    public Tip (){}

    public Tip(String content) {
        this.content = content;
        this.hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        this.minute = Calendar.getInstance().get(Calendar.MINUTE);

    }

    public Tip(String content, Calendar time, boolean[] booleans) {
        this.content = content;
        this.hour = time.get(Calendar.HOUR_OF_DAY);
        this.minute = time.get(Calendar.MINUTE);
        mon = booleans[0];
        tue = booleans[1];
        wed= booleans[2];
        thurs = booleans[3];
        fri= booleans[4];
        sat= booleans[5];
        sun= booleans[6];
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        if(minute < 10)
            return hour +":0"+minute;
        else return hour +":" +minute;
    }

    public void setTime(Calendar time) {
        this.hour = time.get(Calendar.HOUR_OF_DAY);
        this.minute = time.get(Calendar.MINUTE);
    }

    public Calendar getCalendarTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE, minute);
        return calendar;
    }

    public boolean[] getBooleans() {
        boolean b[] = new boolean[]{mon, tue,wed,thurs,fri ,sat ,sun};
        return b;
    }

    public void setBooleans(boolean[] booleans) {
        mon = booleans[0];
        tue = booleans[1];
        wed= booleans[2];
        thurs = booleans[3];
        fri= booleans[4];
        sat= booleans[5];
        sun= booleans[6];
    }
}
