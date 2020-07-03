package com.example.healthapplication.model;

public class User {
    private int age;
    private int gender;
    private String avatar_url;
    private String name;
    private String phone;
    private String token;

    private User(int age, int gender, String avatar_url, String name, String phone, String token) {
        this.age = age;
        this.gender = gender;
        this.avatar_url = avatar_url;
        this.name = name;
        this.phone = phone;
        this.token = token;
    }
    private User(){}

    private User( String avatar_url, String name, String phone, String token) {
        this.age = 0;
        this.gender = 0;
        this.avatar_url = avatar_url;
        this.name = name;
        this.phone = phone;
        this.token = token;
    }

    private static User instance;


    public static User getInstance(int age, int gender, String avatar_url, String name, String phone, String token) {
        if (instance == null) {
            instance = new User(age, gender, avatar_url, name, phone, token);
        }
        return instance;
    }

    public static User getInstance( String avatar_url, String name, String phone, String token) {
        if (instance == null) {
            instance = new User( avatar_url, name, phone, token);
        }
        return instance;
    }

    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        if(gender == 1){
            return "男";
        }else return "女";

    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
