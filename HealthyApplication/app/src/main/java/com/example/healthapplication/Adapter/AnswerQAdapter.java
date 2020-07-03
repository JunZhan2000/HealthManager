package com.example.healthapplication.Adapter;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.healthapplication.R;
import com.example.healthapplication.model.AnswerReport;
import com.example.healthapplication.model.HttpCallbackListener;
import com.example.healthapplication.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AnswerQAdapter extends RecyclerView.Adapter<AnswerQAdapter.MyViewHolder> implements HttpCallbackListener {

    private int p;
    private Context context;
    private List<AnswerReport> answerList = new ArrayList<>(1);
    private static final int TYPE_TOP = 0x0000;
    private static final int TYPE_NORMAL= 0x0001;
    public  static  final int DELETE = 2;

    int[] icon = new int[]{R.drawable.qa2,R.drawable.qa3,R.drawable.qa1,R.drawable.qa4,R.drawable.qa5};


    public AnswerQAdapter(Context context, List<AnswerReport> datas) {
        this.context = context;
        this.answerList = datas;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_qa,parent,false);

        final MyViewHolder holder = new MyViewHolder(itemview);

        holder.todoView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                int position = holder.getAdapterPosition();
                AnswerReport bean_click = answerList.get(position);

                if(AlterClick != null) {
                    AlterClick.OnAlterClick(bean_click,position);}
            }
        });

        holder.deletBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p = holder.getAdapterPosition();
                showPopupMenu(holder.deletBt);


            }
        });


        return  holder;
    }

    private Handler handler = new Handler(){
        public void handleMessage(Message message){
            switch (message.what){
                case DELETE:
                    removeData(p);

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
                                    String url = url_base+"/DELETE/QARecord?id=" + answerList.get(p).getId();

                                    Request request = new Request.Builder().url(url)
                                            .addHeader("Authorization", User.getInstance().getToken())
                                            .delete()
                                            .build();
                                    Response response = client.newCall(request).execute();
                                    String data = response.body().string();
                                    Message message = new Message();
                                    message.what = DELETE;
                                    handler.sendMessage(message);

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
        Random r = new Random();
        int x = r.nextInt(1000);


        Glide.with(context).load(icon[x%5]).into(myViewHolder.img);
        myViewHolder.timeTv.setText(answerList.get(i).getTime());
        myViewHolder.answerTv.setText("提问："+answerList.get(i).getQ_title() + "\n" + "回答"+answerList.get(i).getAnswer());

    }

    @Override
    public int getItemCount() {
        return answerList.size();
    }

    public AnswerReport getItem(int position){

        return answerList.get(position);
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


    public void addData(AnswerReport bean1) {


        answerList.add(0, bean1);
        //刷新适配器
        notifyItemInserted(0);

    }




    public void removeData(int position) {

        answerList.remove(position);

        notifyItemRemoved(position);

    }

    public static interface AlterClickListener {

        public void OnAlterClick(AnswerReport bean_init, int position);

    }

    AlterClickListener AlterClick;

    public void setAlterClickListener(AlterClickListener AlterClick) {

        this.AlterClick = AlterClick;
    }





}

