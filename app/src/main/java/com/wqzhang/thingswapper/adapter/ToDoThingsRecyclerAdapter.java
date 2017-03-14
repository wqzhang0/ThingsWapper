package com.wqzhang.thingswapper.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wqzhang.thingswapper.MainApplication;
import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.dao.BusinessProcessImpl;
import com.wqzhang.thingswapper.dao.greendao.ToDoThing;
import com.wqzhang.thingswapper.model.ShowThingsDTO;
import com.wqzhang.thingswapper.util.Common;
import com.wqzhang.thingswapper.ui.SlideContentView;
import com.wqzhang.thingswapper.ui.TodoThingsRecyclerListView;
import com.wqzhang.thingswapper.util.DateUtil;

import java.util.ArrayList;

/**
 * Created by wqzhang on 16-10-21.
 * 自定义 recycleAdapter 类
 */

public class ToDoThingsRecyclerAdapter extends RecyclerView.Adapter {

    private final String TAG = "RecyclerAdapter";
    public static final int TYPE_EMPTY = 0;
    public static final int TYPE_NORMAL_FINSH = 1;
    public static final int TYPE_NORMAL_TO_BE_DONE = 2;

    private LayoutInflater inflater = null;
    private Context mContext = null;
    private ArrayList<ShowThingsDTO> showThingsDTOs;


    public ToDoThingsRecyclerAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
    }


    public void setData(ArrayList<ShowThingsDTO> toDoThings) {
        this.showThingsDTOs = toDoThings;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_EMPTY) {
            View emptyView = inflater.inflate(R.layout.list_item_empty_fullscreen_view, parent, false);
            EmptyViewHolder emptyViewHolder = new EmptyViewHolder(emptyView);
            return emptyViewHolder;
        } else if (viewType == TYPE_NORMAL_FINSH) {
            View view = inflater.inflate(R.layout.list_item_show_reminder, parent, false);
            SlideContentView slideContentView = new SlideContentView(mContext, view, R.layout.list_item_show_finsh_reminder_slide_view_merge);

            SlideViewHolder slideViewHolder = new SlideViewHolder(slideContentView);
            return slideViewHolder;
        } else if (viewType == TYPE_NORMAL_TO_BE_DONE) {
            View view = inflater.inflate(R.layout.list_item_show_reminder, parent, false);

            SlideContentView slideContentView = new SlideContentView(mContext, view, R.layout.list_item_show_tobedone_reminder_slide_view_merge);
            SlideViewHolder slideViewHolder = new SlideViewHolder(slideContentView);
            return slideViewHolder;
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_EMPTY) {
            //如果为空 则可双向拖动
            TodoThingsRecyclerListView.setScrolledState(TodoThingsRecyclerListView.PULL_DOUBLE);

            return;
        } else if (getItemViewType(position) == TYPE_NORMAL_FINSH) {
            SlideViewHolder slideViewHolder = (SlideViewHolder) holder;
            final SlideContentView slideContentView = slideViewHolder.slide_content_view;
            final TextView textView = (TextView) slideContentView.findViewById(R.id.tittle);

            ToDoThing toDoThing = showThingsDTOs.get(position).getToDoThing();
            textView.setText(toDoThing.getReminderContext());
            textView.setTag(R.id.toDoThingId, toDoThing.getId());
//            textView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Log.d(TAG, "textView is clicked");
//                }
//            });
            slideContentView.findViewById(R.id.bottom_right).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Long toDoThingId = (Long) textView.getTag(R.id.toDoThingId);
                    BusinessProcessImpl.getInstance().updateThingState(toDoThingId, Common.STATUS_FINSH);
                    removeItem(toDoThingId);
                    slideContentView.shrink();

                }
            });

            slideContentView.findViewById(R.id.bottom_left).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Long toDoThingId = (Long) textView.getTag(R.id.toDoThingId);
                    BusinessProcessImpl.getInstance().removeToDoTingById(toDoThingId);
                    removeItem(toDoThingId);
                    slideContentView.shrink();

                }
            });

        } else if (getItemViewType(position) == TYPE_NORMAL_TO_BE_DONE) {
            SlideViewHolder slideViewHolder = (SlideViewHolder) holder;
            final SlideContentView slideContentView = slideViewHolder.slide_content_view;
            final TextView textView = (TextView) slideContentView.findViewById(R.id.tittle);

            ToDoThing toDoThing = showThingsDTOs.get(position).getToDoThing();
            textView.setText(toDoThing.getReminderContext());
            textView.setTag(R.id.toDoThingId, toDoThing.getId());
            slideContentView.findViewById(R.id.bottom_right).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Long toDoThingId = (Long) textView.getTag(R.id.toDoThingId);
                    BusinessProcessImpl.getInstance().updateThingState(toDoThingId, Common.STATUS_FINSH);
                    removeItem(toDoThingId);
                    slideContentView.shrink();

                    MainApplication.startScanService();
                }
            });

            slideContentView.findViewById(R.id.bottom_left).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Long toDoThingId = (Long) textView.getTag(R.id.toDoThingId);
                    BusinessProcessImpl.getInstance().updateThingState(toDoThingId, Common.STATUS_FINSH);
                    removeItem(toDoThingId);
                    slideContentView.shrink();
                    MainApplication.startScanService();
                }
            });
            LinearLayout notifyLayout = (LinearLayout) slideContentView.findViewById(R.id.notify_layout);

            ToDoThing tmpToDoThing = showThingsDTOs.get(position).getToDoThing();
            if (tmpToDoThing.getReminderType() > 0) {
                notifyLayout.setVisibility(View.VISIBLE);
                TextView notifyTime = (TextView) slideContentView.findViewById(R.id.notify_time);
                if (!showThingsDTOs.get(position).isAlreayNotify()) {
                    String answer = DateUtil.formatDateByFormat(showThingsDTOs.get(position).getRecentReminderDate(), DateUtil.TIMESTAMP_PATTERN);
                    notifyTime.setText((answer));
                }
                //如果存在nextReminder
//                if(TextUtils.isEmpty(tmpToDoThing.get))
//                notifyTime.setText();

            } else {
                notifyLayout.setVisibility(View.GONE);
            }
        }
    }

    private void removeItem(Long toDoThingId) {
        for (int i = 0; i < showThingsDTOs.size(); i++) {
            if (showThingsDTOs.get(i).getToDoThing().getId() == toDoThingId) {
                notifyItemRemoved(i);
                showThingsDTOs.remove(i);
            }
        }
    }


    @Override
    public int getItemCount() {
        return (showThingsDTOs == null || showThingsDTOs.size() == 0) ? 1 : showThingsDTOs.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (showThingsDTOs == null || showThingsDTOs.size() == 0) {
            return TYPE_EMPTY;
        } else {
            if (showThingsDTOs.get(position).getToDoThing().getStatus() == Common.STATUS_FINSH) {
                return TYPE_NORMAL_FINSH;
            } else {
                return TYPE_NORMAL_TO_BE_DONE;
            }
        }
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
