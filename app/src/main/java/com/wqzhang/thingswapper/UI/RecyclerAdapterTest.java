package com.wqzhang.thingswapper.UI;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wqzhang.thingswapper.R;

/**
 * Created by wqzhang on 16-10-21.
 * 自定义 recycleAdapter 类
 * 添加点击事件的 回调  函数
 */

public class RecyclerAdapterTest extends RecyclerView.Adapter {

    private final String TAG = "RecyclerAdapterTest";

    private LayoutInflater inflater = null;
    private String[] data = new String[10];

    public RecyclerAdapterTest(Context context) {
        inflater = LayoutInflater.from(context);
        for (int i = 0; i < 10; i++) {
            data[i] = "item" + (i + 1);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder");


        View view = inflater.inflate(R.layout.to_do_list_item_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder");
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.textView.setText(data[position]);
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount");
        return data == null ? 0 : data.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public LinearLayout bottom_right_layout;
        public LinearLayout bottom_left_layout;
        public ImageButton bottom_left_trashImgBtn;
        public ImageButton bottom_right_finshImgBtn;

        private final int bottomLeftWidth;
        private final int bottomRightWidth;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item);
            bottom_left_layout = (LinearLayout) itemView.findViewById(R.id.bottom_left);
            bottom_right_layout = (LinearLayout) itemView.findViewById(R.id.bottom_right);
            bottom_right_finshImgBtn = (ImageButton) itemView.findViewById(R.id.finsh);
            bottom_left_trashImgBtn = (ImageButton) itemView.findViewById(R.id.trash);


            bottom_left_layout.setVisibility(View.VISIBLE);

            //获得宽度需要在UI绘制后
            bottomLeftWidth = bottom_left_layout.getWidth();
            bottomRightWidth = bottom_right_layout.getWidth();
            textView.setTranslationX(100);

        }

//        private void initAnmation() {
//
//            float first_press_down = 0;
//            boolean mobileSignal = false;
//            textView.setOnTouchListener(
//                    new View.OnTouchListener() {
//                        int relativeMoveDistance = 0;
//
//                        @Override
//                        public boolean onTouch(View v, MotionEvent event) {
//                            switch (event.getAction()) {
//                                case MotionEvent.ACTION_DOWN:
//                                    first_press_down = event.getX();
//                                    break;
//                                case MotionEvent.ACTION_MOVE:
//                                    relativeMoveDistance = first_press_down - event.getX();
//                                    if (Math.abs(relativeMoveDistance) > 20) {
//                                        mobileSignal = true;
//                                    }
//                                    if (Math.abs(relativeMoveDistance) < (bottomLeftWidth > bottomRightWidth ?
//                                            bottomLeftWidth : bottomRightWidth)) {
//                                        //可以移动
//                                    }
//                                    break;
//                                case MotionEvent.ACTION_UP:
//                                    break;
//                                default:
//                                    break;
//                            }
//                            return false;
//                        }
//                    }
//
//            );
//
//        }
    }
}
