package com.wqzhang.thingswapper.adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.events.SaveChooseOperationEvent;
import com.wqzhang.thingswapper.events.ShowMoreSetEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by wqzhang on 16-12-27.
 */

public class RemindCountAdapter implements ListAdapter {

    private Context mContext;
    private ArrayList<String> choicesData = new ArrayList<>();
    private LayoutInflater inflater;

    private ChoicesHolder choicesHolder;

    private EventBus bus;


    public void setChoicesData(ArrayList<String> data) {
        this.choicesData = data;
    }

    public RemindCountAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);
        bus = EventBus.getDefault();
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int i) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return choicesData == null ? 0 : choicesData.size();
    }

    @Override
    public Object getItem(int i) {
        return choicesData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null || choicesHolder == null) {
            choicesHolder = new ChoicesHolder();
            view = inflater.inflate(R.layout.list_item_reminder_cal, null);
            choicesHolder.textView = (TextView) view.findViewById(R.id.remind_content);
            view.setTag(choicesHolder);
        } else {
            choicesHolder = (ChoicesHolder) view.getTag();
        }
        choicesHolder.textView.setText(choicesData.get(i));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bus.post(new SaveChooseOperationEvent(SaveChooseOperationEvent.TYPE_SAVE_NOTYFLY_COUNTS, choicesData.get(i), true));
                bus.post(new ShowMoreSetEvent(ShowMoreSetEvent.HIDE));
            }
        });

        return view;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    class ChoicesHolder {
        TextView textView;
    }
}
