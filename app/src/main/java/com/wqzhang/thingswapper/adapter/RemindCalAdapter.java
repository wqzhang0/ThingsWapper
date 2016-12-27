package com.wqzhang.thingswapper.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.wqzhang.thingswapper.R;

import java.util.ArrayList;

/**
 * Created by wqzhang on 16-12-27.
 */

public class RemindCalAdapter implements ListAdapter {

    private Context mContext;
    private ArrayList<String> choicesData = new ArrayList<>();
    private LayoutInflater inflater;

    private ChoicesHolder choicesHolder;


    public void setChoicesData(ArrayList<String> data) {
        this.choicesData = data;
    }

    public RemindCalAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null || choicesHolder == null) {
            choicesHolder = new ChoicesHolder();
            view = inflater.inflate(R.layout.reminder_cal_list_item, null);
            choicesHolder.textView = (TextView) view.findViewById(R.id.content);
            view.setTag(choicesHolder);
        } else {
            choicesHolder = (ChoicesHolder) view.getTag();
        }
        choicesHolder.textView.setText(choicesData.get(i));

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
