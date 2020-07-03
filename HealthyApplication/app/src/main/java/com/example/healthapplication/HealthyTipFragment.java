package com.example.healthapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.healthapplication.Adapter.TipAdapter;
import com.example.healthapplication.model.Tip;
import com.example.healthapplication.util.ItemTouchHelperCallback;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


public class HealthyTipFragment extends Fragment {

    private TipAdapter adapter;
    private RecyclerView recyclerview;
    private ArrayList<Tip> datas;
    private Tip tip;
    private int p;



    public HealthyTipFragment() {
    }

    public static HealthyTipFragment newInstance() {
        HealthyTipFragment fragment = new HealthyTipFragment();
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_healthy_tip, container, false);
        Toolbar toolbar = view.findViewById(R.id.tip_toolbar);
        toolbar.setTitle("健康便签");



        recyclerview = view.findViewById(R.id.recyclerview);
        datas = new ArrayList<Tip>();

        Connector.getDatabase();


        datas = (ArrayList<Tip>) DataSupport.findAll(Tip.class);

        adapter = new TipAdapter(getContext(), datas);

        recyclerview.setAdapter(adapter);

        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        FloatingActionButton button = view.findViewById(R.id.add_tip);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditTipActivity.class);
                startActivityForResult(intent, 1);
            }


        });

        TipAdapter.AlterClickListener onItemActionClick = new TipAdapter.AlterClickListener() {

            @Override
            public void OnAlterClick(Tip bean_init, int position) {
                tip = bean_init;
                Intent intent = new Intent(getContext(),EditTipActivity.class);
                intent.putExtra("init", tip);
                startActivityForResult(intent,2);
                p = position;
            }

        };

        adapter.setAlterClickListener(onItemActionClick);

       initTouch();


        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        switch (requestCode) {

            case 1:
                if (resultCode == RESULT_OK) {
                    Tip tip = (Tip) data.getSerializableExtra("tip_data");
                    tip.save();
                    adapter.addData(tip);

                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {

                    tip = (Tip) data.getSerializableExtra("tip_data");
                    Tip tipDataBase = DataSupport.where("content = ?", datas.get(p).getContent()).find(Tip.class).get(0);

                    tipDataBase.setTime(tip.getCalendarTime());
                    tipDataBase.setContent(tip.getContent());
                    tipDataBase.setBooleans(tip.getBooleans());
                    tipDataBase.save();

                    datas.set(p,tip);
                    adapter.notifyItemChanged(p);

                }

            default:
        }

    }

    private void initTouch() {
        ItemTouchHelperCallback itemTouchHelperCallback = new ItemTouchHelperCallback();
        itemTouchHelperCallback.setOnitemTouchCallBackListener(onitemTouchCallBackListener);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerview);
    }



    private ItemTouchHelperCallback.OnitemTouchCallBackListener onitemTouchCallBackListener = new ItemTouchHelperCallback.OnitemTouchCallBackListener(){


        @Override
        public void onSwiped(int position) {

            if(datas != null){

                Tip beanGet = adapter.getItem(position);

                DataSupport.deleteAll(Tip.class,"content = ?", beanGet.getContent());
                adapter.removeData(position);
            }

        }


        @Override
        public boolean onMove(int startPosition, int endPosition) {

            adapter.notifyItemMoved(startPosition,endPosition);
            adapter.swop(startPosition,endPosition);



            return true;
        }


    };

}
