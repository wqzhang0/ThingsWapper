package com.wqzhang.thingswapper.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.wqzhang.thingswapper.vus.Vu;

/**
 * Created by wqzhang on 17-1-5.
 */

public abstract class BaseRecyclerPartenerAdapter<V extends Vu> extends RecyclerView.Adapter {
    private V vu;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        try {
            vu  = getVuClass().newInstance();
//            vu.init(layoutInflater,parent,viewType);

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public abstract Class<V> getVuClass();
}
