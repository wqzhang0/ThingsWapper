package com.wqzhang.thingswapper.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.tools.DateUtil;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wqzhang on 16-12-30.
 */

public class NotifyDateRecyclerAdapter extends RecyclerView.Adapter {
    private ArrayList<Date> dateList = new ArrayList<>();
    private Context mContext;
    private LayoutInflater inflater;

    private NotifyDateRecyclerAdapter() {
    }

    public NotifyDateRecyclerAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
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
        defaultHolder.textView.setText(DateUtil.formatDateByFormat(dateList.get(position),DateUtil.TIMESTAMP_PATTERN));
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }

    public void setDateList(ArrayList<Date> dates) {
        this.dateList = dates;
        this.notifyDataSetChanged();
    }

    public ArrayList<Date> getDateList() {
        return dateList;
    }

    class DefaultHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public DefaultHolder(View itemView) {
            super(itemView);
        }
    }
}
