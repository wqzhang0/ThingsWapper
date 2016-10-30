package com.wqzhang.thingswapper.UI;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private String[] data = new String[20];

    public RecyclerAdapterTest(Context context) {
        inflater = LayoutInflater.from(context);
        for (int i = 0; i < 20; i++) {
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
        return data.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item);
        }
    }
}
