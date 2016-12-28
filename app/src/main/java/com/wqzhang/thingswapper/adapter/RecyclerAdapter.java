package com.wqzhang.thingswapper.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.dao.greendao.ToDoThing;
import com.wqzhang.thingswapper.ui.RecyclerListView;
import com.wqzhang.thingswapper.ui.SlideContentView;

import java.util.ArrayList;

/**
 * Created by wqzhang on 16-10-21.
 * 自定义 recycleAdapter 类
 */

public class RecyclerAdapter extends RecyclerView.Adapter {

    private final String TAG = "RecyclerAdapterTest";
    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的

    private LayoutInflater inflater = null;
    private Context mContext = null;
    private ArrayList<ToDoThing> toDoThings;

    private View mHeaderView;
    private View mFooterView;

    public RecyclerAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<ToDoThing> toDoThings) {
        this.toDoThings = toDoThings;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Log.d(TAG, "onCreateVi
// ewHolder");

        if (viewType == TYPE_HEADER) {
            return new HeaderFooterHolder(mHeaderView);
        } else if (viewType == TYPE_FOOTER) {

            RecyclerListView.isBootom = true;
            return new HeaderFooterHolder(mFooterView);
        } else {
            parent = new SlideContentView(mContext);
            View view = inflater.inflate(R.layout.list_view_item, parent, false);
            SlideContentView slideContentView = new SlideContentView(mContext);
            slideContentView.setContentView(view);

            ViewHolder viewHolder = new ViewHolder(slideContentView);
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
//        Log.d(TAG, "onBindViewHolder");

        if (getItemViewType(position) == TYPE_HEADER) {

        } else if (getItemViewType(position) == TYPE_FOOTER) {

        } else {
            ViewHolder viewHolder = (ViewHolder) holder;
            SlideContentView slideContentView = viewHolder.slide_content_view;
            TextView textView = (TextView) slideContentView.findViewById(R.id.tittle);
            textView.setText(toDoThings.get(position).getReminderContext());
//            textView.setText(toDoThings.get(position - 1).getReminderContext());
            slideContentView.getmFinshImgBtn().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                Toast.makeText(mContext, data[position] + " Click", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
//        Log.d(TAG, "getItemCount");
        return toDoThings == null ? 0 : toDoThings.size();
    }

    @Override
    public int getItemViewType(int position) {

//        if (position == 0 && mHeaderView != null) {
//            return TYPE_HEADER;
//        }
//        if (position == getItemCount() - 1 && mFooterView != null) {
//            return TYPE_FOOTER;
//        }

        return TYPE_NORMAL;
    }

    public void setHeaderView(View headerView) {
        this.mHeaderView = headerView;
    }

    public void setFooterView(View footerView) {
        this.mFooterView = footerView;
    }

    public void hiddenFooterView() {
        this.mFooterView.setVisibility(View.GONE);
    }

    public void showFooterView() {
        this.mFooterView.setVisibility(View.VISIBLE);
    }

    public void hiddenHeaderView() {
        this.mHeaderView.setVisibility(View.GONE);
    }

    public void showHeaderView() {
        this.mHeaderView.setVisibility(View.VISIBLE);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public SlideContentView slide_content_view;


        public ViewHolder(View itemView) {
            super(itemView);
            slide_content_view = (SlideContentView) itemView;
        }
    }

    public static class HeaderFooterHolder extends RecyclerView.ViewHolder {
        public HeaderFooterHolder(View itemView) {
            super(itemView);
        }
    }


}
