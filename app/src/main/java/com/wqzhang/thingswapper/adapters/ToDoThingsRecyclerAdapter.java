package com.wqzhang.thingswapper.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.dao.greendao.ToDoThing;
import com.wqzhang.thingswapper.ui.SlideContentView;
import com.wqzhang.thingswapper.ui.TodoThingsRecyclerListView;

import java.util.ArrayList;

/**
 * Created by wqzhang on 16-10-21.
 * 自定义 recycleAdapter 类
 */

public class ToDoThingsRecyclerAdapter extends RecyclerView.Adapter {

    private final String TAG = "RecyclerAdapter";
    public static final int TYPE_EMPTY = 0;
    public static final int TYPE_NORMAL = 1;

    private LayoutInflater inflater = null;
    private Context mContext = null;
    private ArrayList<ToDoThing> toDoThings;


    public ToDoThingsRecyclerAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
    }


    public void setData(ArrayList<ToDoThing> toDoThings) {
        this.toDoThings = toDoThings;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_EMPTY) {
            View emptyView = inflater.inflate(R.layout.empty_fullscreen_view, parent, false);
            EmptyViewHolder emptyViewHolder = new EmptyViewHolder(emptyView);
            return emptyViewHolder;
        } else {
            parent = new SlideContentView(mContext);
            View view = inflater.inflate(R.layout.show_reminder_item, parent, false);
            SlideContentView slideContentView = new SlideContentView(mContext);
            slideContentView.setContentView(view);

            SlideViewHolder slideViewHolder = new SlideViewHolder(slideContentView);
            return slideViewHolder;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_EMPTY) {
            //默认可以上拉
            TodoThingsRecyclerListView.setScrolledState(TodoThingsRecyclerListView.PULL_DOUBLE);

            return;
        } else {
            SlideViewHolder slideViewHolder = (SlideViewHolder) holder;
            SlideContentView slideContentView = slideViewHolder.slide_content_view;
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
        return (toDoThings == null || toDoThings.size() == 0) ? 1 : toDoThings.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (toDoThings == null || toDoThings.size() == 0) ? TYPE_EMPTY : TYPE_NORMAL;
    }


    public static class SlideViewHolder extends RecyclerView.ViewHolder {
        public SlideContentView slide_content_view;


        public SlideViewHolder(View itemView) {
            super(itemView);
            slide_content_view = (SlideContentView) itemView;
        }
    }

    public class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
