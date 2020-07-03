package com.example.healthapplication.model;

import android.widget.EditText;

import java.io.Serializable;

public class Question implements Serializable {
    private String title;
    private String text;
    private String deapartment;

    public Question(String title, String text, String deapartment) {
        this.title = title;
        this.text = text;
        this.deapartment = deapartment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDeapartment() {
        return deapartment;
    }

    public void setDeapartment(String deapartment) {
        this.deapartment = deapartment;
    }
}
