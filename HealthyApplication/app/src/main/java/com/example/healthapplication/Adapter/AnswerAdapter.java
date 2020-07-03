package com.example.healthapplication.Adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.healthapplication.R;
import com.example.healthapplication.model.AnswerReport;
import com.example.healthapplication.model.HttpCallbackListener;
import com.example.healthapplication.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



public class AnswerAdapter extends BaseAdapter implements HttpCallbackListener {


    private int p;
    private Context context;
    private List<AnswerReport> answerList = new ArrayList<>(1);
    private static final int TYPE_TOP = 0x0000;
    private static final int TYPE_NORMAL= 0x0001;
    public  static  final int DELETE = 2;

    public AnswerAdapter(Context context, List<AnswerReport> traceList) {
        this.context = context;
        this.answerList = traceList;
    }

    @Override
    public int getCount() {
        return answerList.size();
    }

    @Override
    public AnswerReport getItem(int position) {
        return answerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final AnswerReport answerReport = getItem(position);
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_trace, parent, false);
            holder.tvAcceptTime = (TextView) convertView.findViewById(R.id.tvAcceptTime);
            holder.tvAcceptStation = (TextView) convertView.findViewById(R.id.tvAcceptStation);
            holder.tvTopLine = (TextView) convertView.findViewById(R.id.tvTopLine);
            holder.tvDot = (TextView) convertView.findViewById(R.id.tvDot);
            holder.tvAnswer = convertView.findViewById(R.id.item_answer);
            holder.deletMenu = convertView.findViewById(R.id.delete);

            convertView.setTag(holder);
        }

        if (getItemViewType(position) == TYPE_TOP) {
            // 第一行头的竖线不显示
            holder.tvTopLine.setVisibility(View.INVISIBLE);
            // 字体颜色加深
            //holder.tvAcceptTime.setTextColor(0xff555555);
            //holder.tvAcceptStation.setTextColor(0xff555555);
            holder.tvDot.setBackgroundResource(R.drawable.timelline_dot_first);
        } else if (getItemViewType(position) == TYPE_NORMAL) {
            holder.tvTopLine.setVisibility(View.VISIBLE);
            //holder.tvAcceptTime.setTextColor(0xff999999);
            //holder.tvAcceptStation.setTextColor(0xff999999);
            holder.tvDot.setBackgroundResource(R.drawable.timelline_dot_normal);
        }

        holder.tvAcceptTime.setText(answerReport.getTime());
        holder.tvAcceptStation.setText(answerReport.getQ_title());
        holder.tvAnswer.setText(answerReport.getAnswer());


        holder.deletMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p = position;
                showPopupMenu(holder.deletMenu);

            }
        });

        return convertView;
    }

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
    private Handler handler = new Handler(){
        public void handleMessage(Message message){
            switch (message.what){
                case DELETE:
                    answerList.remove(p);
                    notifyDataSetChanged();

                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public int getItemViewType(int id) {
        if (id == 0) {
            return TYPE_TOP;
        }
        return TYPE_NORMAL;
    }

    static class ViewHolder {
        public TextView tvAcceptTime, tvAcceptStation ,tvAnswer;
        public TextView tvTopLine, tvDot;
        private ImageView deletMenu;
    }
}









































































