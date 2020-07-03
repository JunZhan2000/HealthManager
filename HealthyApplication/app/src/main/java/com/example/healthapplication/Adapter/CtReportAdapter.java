package com.example.healthapplication.Adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.healthapplication.R;
import com.example.healthapplication.model.HttpCallbackListener;
import com.example.healthapplication.model.JudgeRecord;
import com.example.healthapplication.model.User;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CtReportAdapter extends RecyclerView.Adapter<CtReportAdapter.MyViewHolder> implements HttpCallbackListener {


    private final Context context;
    private int position;
    public  static  final int DELETE = 2;
    private String type;

    private static ArrayList<JudgeRecord> datas;

    public CtReportAdapter(Context context, ArrayList<JudgeRecord> datas, String type) {

        this.context = context;

        this.datas = datas;

        this.type = type;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ct,parent,false);

        final MyViewHolder holder = new MyViewHolder(itemview);

        holder.todoView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                int position = holder.getAdapterPosition();
                JudgeRecord bean_click = datas.get(position);
                Log.d("logaa","position1"+position);
                Log.d("logaa","id"+datas.get(position).getId() + datas.get(position).getTime());

                if(AlterClick != null) {
                    AlterClick.OnAlterClick(bean_click,position);}
            }
        });

        holder.deletBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = holder.getAdapterPosition();
                showPopupMenu(holder.deletBt);


            }
        });


        return  holder;
    }

    private Handler handler = new Handler(){
        public void handleMessage(Message message){
            switch (message.what){
                case DELETE:
                    removeData(position);

                    break;
                default:
                    break;
            }
        }
    };

    private void showPopupMenu(View view) {
        // 这里的view代表popupMenu需要依附的view
        PopupMenu popupMenu = new PopupMenu(context, view);
        // 获取布局文件
        popupMenu.getMenuInflater().inflate(R.menu.delete_menu, popupMenu.getMenu());
        popupMenu.show();
        // 通过上面这几行代码，就可以把控件显示出来了
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // 控件每一个item的点击事件
                switch (item.getItemId()){
                    case R.id.del:

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    OkHttpClient client = new OkHttpClient();
                                    Log.d("logaa","position"+position);
                                    Log.d("logaa","id"+ datas.get(position).getId());
                                    String url = url_base+"/DELETE/"+type+"?id=" + datas.get(position).getId();
                                    Log.d("logaa",url);
                                    Log.d("logaa","0号id"+datas.get(0).getId() + datas.get(0).getTime());


                                    Request request = new Request.Builder().url(url)
                                            .addHeader("Authorization", User.getInstance().getToken())
                                            .delete()
                                            .build();
                                    Response response = client.newCall(request).execute();
                                    String data = response.body().string();
                                    Message message = new Message();
                                    message.what = DELETE;
                                    handler.sendMessage(message);

                                    Log.d("logaa",data);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                        break;
                        default:
                            break;

                }
                return true;
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                // 控件消失时的事件
            }
        });

    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        //根据位置得到对应的数据

        Glide.with(context).load(url_base+ datas.get(i).getPicture_url()).into(myViewHolder.img);
        myViewHolder.timeTv.setText(datas.get(i).getTime());
        myViewHolder.answerTv.setText("分析结果为："+datas.get(i).getAnswer());

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public JudgeRecord getItem(int position){

        return datas.get(position);
    }


    class MyViewHolder extends RecyclerView.ViewHolder {


        private ImageView img;
        private TextView timeTv;
        private TextView answerTv;
        private ImageView deletBt;

        View todoView;


        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            img = itemView.findViewById(R.id.img);
            timeTv = itemView.findViewById(R.id.time);
            answerTv = itemView.findViewById(R.id.answer);
            todoView = itemView;
            deletBt = itemView.findViewById(R.id.delet);

        }

    }


    public void addData(JudgeRecord bean1) {


        datas.add(0, bean1);
        //刷新适配器
        notifyItemInserted(0);

    }




    public void removeData(int position) {

        datas.remove(position);

        notifyItemRemoved(position);

    }

    public static interface AlterClickListener {

        public void OnAlterClick(JudgeRecord bean_init, int position);

    }

    AlterClickListener AlterClick;

    public void setAlterClickListener(AlterClickListener AlterClick) {

        this.AlterClick = AlterClick;
    }





}

