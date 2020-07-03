package com.example.healthapplication.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {


    private OnitemTouchCallBackListener onitemTouchCallBackListener;

    public  void setOnitemTouchCallBackListener(OnitemTouchCallBackListener onitemTouchCallBackListener){

        this.onitemTouchCallBackListener = onitemTouchCallBackListener;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    //滑动、拖拽的方向
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int dragFlag = 0;
        int swiperFlag = 0;
        dragFlag = ItemTouchHelper.UP| ItemTouchHelper.DOWN;
        swiperFlag = ItemTouchHelper.LEFT;

        return makeMovementFlags(dragFlag,swiperFlag);
    }

    //被拖拽时回调
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder startViewHolder, @NonNull RecyclerView.ViewHolder endViewHolder) {

        if(onitemTouchCallBackListener != null){
            return  onitemTouchCallBackListener.onMove(startViewHolder.getAdapterPosition(),endViewHolder.getAdapterPosition());
        }

        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if(onitemTouchCallBackListener != null){
            onitemTouchCallBackListener.onSwiped(viewHolder.getAdapterPosition());
        }

    }

    public  interface  OnitemTouchCallBackListener{

        void onSwiped(int position);

        boolean onMove(int startPosition, int endPosition);


    }
}
