package com.wqzhang.thingswapper.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wqzhang.thingswapper.R;

import java.util.ArrayList;

/**
 * Created by wqzhang on 16-12-30.
 */

public class NotifyDateRecyclerAdapter extends RecyclerView.Adapter {
    private ArrayList<String> dateList = new ArrayList<>();
    private Context mContext;
    private LayoutInflater inflater;

    private NotifyDateRecyclerAdapter() {
    }

    public NotifyDateRecyclerAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
//        dateList.add("2016年12月30日15:11");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.notify_date_item, parent, false);
        DefaultHolder defaultHolder = new DefaultHolder(view);
        return defaultHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DefaultHolder defaultHolder = (DefaultHolder) holder;
        defaultHolder.textView = (TextView) defaultHolder.itemView.findViewById(R.id.date);
        defaultHolder.textView.setText(dateList.get(position));
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }

    public void setDateList(ArrayList<String> dateList) {
        this.dateList = dateList;
        notifyDataSetChanged();
    }

    public ArrayList<String> getDateList() {
        return dateList;
    }

    class DefaultHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public DefaultHolder(View itemView) {
            super(itemView);
        }
    }
}
