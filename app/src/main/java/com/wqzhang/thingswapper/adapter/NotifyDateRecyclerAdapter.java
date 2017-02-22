package com.wqzhang.thingswapper.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.dao.AddThingOperationXMLDataCache;
import com.wqzhang.thingswapper.event.SaveChooseOperationEvent;
import com.wqzhang.thingswapper.util.DateUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wqzhang on 16-12-30.
 */

public class NotifyDateRecyclerAdapter extends RecyclerView.Adapter {
    private ArrayList<Date> dateList = new ArrayList<>();
    private Context mContext;
    private LayoutInflater inflater;
    EventBus bus;

    private NotifyDateRecyclerAdapter() {
    }

    public NotifyDateRecyclerAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        bus = EventBus.getDefault();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_notify_date, parent, false);
        DefaultHolder defaultHolder = new DefaultHolder(view);
        return defaultHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final DefaultHolder defaultHolder = (DefaultHolder) holder;
        defaultHolder.textView = (TextView) defaultHolder.itemView.findViewById(R.id.date);
        defaultHolder.textView.setText(DateUtil.formatDateByFormat(dateList.get(position), DateUtil.TIMESTAMP_PATTERN));

        defaultHolder.delete = (ImageView) defaultHolder.itemView.findViewById(R.id.delete);
        defaultHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bus.post(new SaveChooseOperationEvent(
                        SaveChooseOperationEvent.TYPE_REMOVE_NOTIFY_DATE,
                        DateUtil.parseDate(defaultHolder.textView.getText().toString(), DateUtil.TIMESTAMP_PATTERN),
                        true));
                dateList = AddThingOperationXMLDataCache.getDates();
                notifyDataSetChanged();
            }
        });
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
        ImageView delete;

        public DefaultHolder(View itemView) {
            super(itemView);
        }
    }
}
