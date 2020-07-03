package com.example.healthapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.healthapplication.model.HttpCallbackListener;
import com.example.healthapplication.model.User;

import de.hdodenhof.circleimageview.CircleImageView;


public class PersonalinfoFragment extends Fragment implements View.OnClickListener, HttpCallbackListener {
    private CircleImageView iconView;
    private TextView userNameTv;
    private User user = User.getInstance();

    private RelativeLayout ctRecord;
    private RelativeLayout cjRecord;
    private RelativeLayout changeInfo;
    private RelativeLayout qaRecord;


    public PersonalinfoFragment() {
    }

    public static PersonalinfoFragment newInstance() {
        PersonalinfoFragment fragment = new PersonalinfoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personalinfo, container, false);
        iconView = view.findViewById(R.id.user_icon);

        userNameTv = view.findViewById(R.id.username);

        ctRecord = view.findViewById(R.id.ct_record);
        ctRecord.setOnClickListener(this);

        cjRecord = view.findViewById(R.id.cj_record);
        cjRecord.setOnClickListener(this);

        changeInfo = view.findViewById(R.id.change_info);
        changeInfo.setOnClickListener(this);

        qaRecord = view.findViewById(R.id.qa_record);
        qaRecord.setOnClickListener(this);

        return view;

    }

    private void initInfo(){
        Glide.with(this).load(url_base + user.getAvatar_url()).into(iconView);
        userNameTv.setText(user.getName());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ct_record:
                Intent intent = new Intent(getContext(), ShowCtRecordActivity.class);
                startActivity(intent);
                break;
            case R.id.cj_record:
                Intent intent2 = new Intent(getContext(), ShowCancerReportActivity.class);
                startActivity(intent2);
                break;
            case R.id.change_info:
                Intent intent3 = new Intent(getContext(), ChangeInfoActivity.class);
                startActivity(intent3);
                break;
            case R.id.qa_record:
                Intent intent4 = new Intent(getContext(), ShowQaRecordActivity.class);
                startActivity(intent4);
                default:
                    break;

        }



    }

    @Override
    public void onStart(){
        super.onStart();
        initInfo();

    }
}
