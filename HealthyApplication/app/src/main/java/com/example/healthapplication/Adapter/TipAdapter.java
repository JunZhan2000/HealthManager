package com.example.healthapplication.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.healthapplication.R;
import com.example.healthapplication.model.Tip;

import java.util.ArrayList;

public  class TipAdapter extends RecyclerView.Adapter<TipAdapter.MyViewHolder> {



    private final Context context;

    private static ArrayList<Tip> datas;

    public TipAdapter(Context context, ArrayList<Tip> datas) {

        this.context = context;

        this.datas = datas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recyclerview,parent,false);

        final MyViewHolder holder = new MyViewHolder(itemview);

        holder.todoView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                int position = holder.getAdapterPosition();
                Tip bean_click = datas.get(position);

                if(AlterClick != null) {
                AlterClick.OnAlterClick(bean_click,position);}
            }
        });


        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        //根据位置得到对应的数据

        String thedata = datas.get(i).getContent();

        myViewHolder.todo.setText(thedata);
        myViewHolder.time.setText(datas.get(i).getTime());
        myViewHolder.day.setText(printCycle(datas.get(i).getBooleans()));

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public Tip getItem(int position){

        return datas.get(position);
    }


    class MyViewHolder extends RecyclerView.ViewHolder {


        private TextView todo;
        private TextView day;
        private TextView time;

        View todoView;


        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            todo = itemView.findViewById(R.id.tip_name);
            day = itemView.findViewById(R.id.tip_day);
            time = itemView.findViewById(R.id.tip_time);

            todoView = itemView;

        }

    }


    public void addData(Tip bean1) {


        datas.add(0, bean1);
        //刷新适配器
        notifyItemInserted(0);

    }

    public  void swop(int start ,int end){

        Tip beanTemp = datas.get(start);

        datas.set(start,datas.get(end));
        datas.set(end,beanTemp);

    }


    public void removeData(int position) {

        datas.remove(position);

        notifyItemRemoved(position);

    }

    public static interface AlterClickListener {

        public void OnAlterClick(Tip bean_init, int position);

    }

    AlterClickListener AlterClick;

    public void setAlterClickListener(AlterClickListener AlterClick) {

        this.AlterClick = AlterClick;
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

