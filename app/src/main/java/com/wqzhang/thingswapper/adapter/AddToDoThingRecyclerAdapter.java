package com.wqzhang.thingswapper.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.listener.impl.ShowMoreSet;
import com.wqzhang.thingswapper.tools.DateUtil;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wqzhang on 16-12-30.
 */

public class AddToDoThingRecyclerAdapter extends RecyclerView.Adapter {

    private final int TYPE_EDIT = 0;
    private final int TYPE_NOTIFY_TYPE = 1;
    private final int TYPE_NOTIFY_DATE = 2;
    private final int TYPE_NOTIFY_COUNT = 3;

    private static int currentOperation = -1;
    private static final int OPERATION_CHOICE_DATE = 1;
    private static final int OPERATION_CHOICE_COUNT = 2;

    private Context mContext;
    private LayoutInflater inflater;

    private ShowMoreSet showMoreSet;

    private RecyclerView.ViewHolder preHolder;

    NotifyDateRecyclerAdapter notifyDateRecyclerAdapter;
    int[] date = new int[]{0, 1, 2, 3};

    public AddToDoThingRecyclerAdapter() {
        super();
    }

    public AddToDoThingRecyclerAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case TYPE_EDIT:
                view = inflater.inflate(R.layout.add_to_do_thing_content_item, parent, false);

                break;
            case TYPE_NOTIFY_TYPE:
                view = inflater.inflate(R.layout.add_to_do_thing_notify_type_choice_item, parent, false);

                break;
            case TYPE_NOTIFY_DATE:
                view = inflater.inflate(R.layout.add_to_do_thing_notify_date_choice_item, parent, false);
                return new DateChoiceHolder(view);
            case TYPE_NOTIFY_COUNT:
                view = inflater.inflate(R.layout.add_to_do_thing_notify_count_choice_item, parent, false);
                return new NotifyCountHolder(view);
        }
        return new DefaultHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final View itemView = holder.itemView;

        switch (getItemViewType(position)) {
            case TYPE_EDIT:

                break;
            case TYPE_NOTIFY_TYPE:


                break;
            case TYPE_NOTIFY_DATE:
                final DateChoiceHolder dateChoiceHolder = (DateChoiceHolder) holder;
                dateChoiceHolder.dateSwitch = (Switch) itemView.findViewById(R.id.reminder_date_switch);

                dateChoiceHolder.childLayout = (LinearLayout) itemView.findViewById(R.id.child_layout);
                dateChoiceHolder.recyclerView = (RecyclerView) itemView.findViewById(R.id.child_recycler_view);
                dateChoiceHolder.addDateBtn = (Button) itemView.findViewById(R.id.child_add_date_btn);
                dateChoiceHolder.addDateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dateChoiceHolder.childLayout.setVisibility(View.VISIBLE);
                        Animator startOpen = showChildView(dateChoiceHolder);
                        startOpen.start();
                        if (showMoreSet != null) {
                            showMoreSet.showMoreSetFrameLayout(ShowMoreSet.ShowType.SHOW_DATE);
                        }
                        currentOperation = OPERATION_CHOICE_DATE;
                        preHolder = holder;
                    }
                });
                dateChoiceHolder.dateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if (isChecked) {
                            //点击展开
                            dateChoiceHolder.childLayout.setVisibility(View.VISIBLE);
                            Animator startOpen = showChildView(dateChoiceHolder);
                            startOpen.start();
                            if (showMoreSet != null) {
                                showMoreSet.showMoreSetFrameLayout(ShowMoreSet.ShowType.SHOW_DATE);
                            }
                            currentOperation = OPERATION_CHOICE_DATE;
                        } else {
                            //点击折叠
//                            dateChoiceHolder.childLayout.setVisibility(View.GONE);
                            Animator startHide = hideChildView(dateChoiceHolder, dateChoiceHolder.childLayout);
                            startHide.start();
                        }
                        preHolder = holder;
                    }
                });
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                dateChoiceHolder.recyclerView.setLayoutManager(linearLayoutManager);
                notifyDateRecyclerAdapter = new NotifyDateRecyclerAdapter(mContext);
                dateChoiceHolder.recyclerView.setAdapter(notifyDateRecyclerAdapter);
                break;
            case TYPE_NOTIFY_COUNT:
                final NotifyCountHolder notifyCountHolder = (NotifyCountHolder) holder;
                notifyCountHolder.countSwitch = (Switch) itemView.findViewById(R.id.count_switch);
                notifyCountHolder.childLayout = (LinearLayout) itemView.findViewById(R.id.child_layout);
                notifyCountHolder.answerText = (TextView) itemView.findViewById(R.id.answer_text);

                notifyCountHolder.answerText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (showMoreSet != null) {
                            showMoreSet.showMoreSetFrameLayout(ShowMoreSet.ShowType.SHOW_COUNT);
                            currentOperation = OPERATION_CHOICE_COUNT;
                        }
                    }
                });
                notifyCountHolder.countSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if (isChecked) {
                            //点击展开
                            notifyCountHolder.childLayout.setVisibility(View.VISIBLE);
                            Animator startOpen = showChildView(notifyCountHolder);
                            startOpen.start();
                            if (showMoreSet != null && notifyCountHolder.answerText.getText().toString().equals("不重复")) {
                                showMoreSet.showMoreSetFrameLayout(ShowMoreSet.ShowType.SHOW_COUNT);
                            }
                        } else {
                            //点击折叠
//                            dateChoiceHolder.childLayout.setVisibility(View.GONE);
                            Animator startHide = hideChildView(notifyCountHolder, notifyCountHolder.childLayout);
                            startHide.start();
                        }
                        currentOperation = OPERATION_CHOICE_COUNT;
                        preHolder = holder;
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return date.length;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class EditHolder extends RecyclerView.ViewHolder {

        public EditHolder(View itemView) {
            super(itemView);
        }
    }

    class DefaultHolder extends RecyclerView.ViewHolder {

        public DefaultHolder(View itemView) {
            super(itemView);
        }
    }

    class DateChoiceHolder extends RecyclerView.ViewHolder {
        Switch dateSwitch;
        LinearLayout childLayout;
        RecyclerView recyclerView;
        Button addDateBtn;

        public DateChoiceHolder(View itemView) {
            super(itemView);
        }

    }

    class NotifyCountHolder extends RecyclerView.ViewHolder {
        Switch countSwitch;
        TextView answerText;
        LinearLayout childLayout;

        public NotifyCountHolder(View itemView) {
            super(itemView);
        }
    }


    public static Animator showChildView(RecyclerView.ViewHolder parentHolder) {
        View parent = (View) parentHolder.itemView.getParent();
        if (parent == null)
            throw new IllegalStateException("Cannot animate the layout of a view that has no parent");
        int start = parentHolder.itemView.getMeasuredHeight();
        parentHolder.itemView.measure(View.MeasureSpec.makeMeasureSpec(parent.getMeasuredWidth(), View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        int end = parentHolder.itemView.getMeasuredHeight();
        final Animator animator = LayoutAnimator.ofHeight(parentHolder.itemView, start, end);
        return animator;
    }

    public static Animator hideChildView(RecyclerView.ViewHolder parentHolder, View childView) {
        View parent = (View) parentHolder.itemView.getParent();
        if (parent == null)
            throw new IllegalStateException("Cannot animate the layout of a view that has no parent");
        int start = parentHolder.itemView.getMeasuredHeight();
        childView.measure(View.MeasureSpec.makeMeasureSpec(parent.getMeasuredWidth(), View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        int childHeght = childView.getHeight();
        parentHolder.itemView.measure(View.MeasureSpec.makeMeasureSpec(parent.getMeasuredWidth(), View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        int end = start - childHeght;
        final Animator animator = LayoutAnimator.ofHeight(parentHolder.itemView, start, end);
        return animator;
    }


    public void saveOperationData(boolean isDetermine, Object data) {
        switch (currentOperation) {
            case OPERATION_CHOICE_DATE:
                if (isDetermine) {

                    ArrayList<String> dateTmp = notifyDateRecyclerAdapter.getDateList();
                    dateTmp.add(DateUtil.formatTimesTampDate((Date) data));
                    notifyDateRecyclerAdapter.setDateList(dateTmp);
//                    notifyItemChanged(TYPE_NOTIFY_DATE);
                    Animator startOpen = showChildView(preHolder);
                    startOpen.start();

                } else {
                    ArrayList<String> dateTmp = notifyDateRecyclerAdapter.getDateList();
                    if (dateTmp.size() == 0) {
                        DateChoiceHolder dateChoiceHolder = ((DateChoiceHolder) preHolder);
                        Animator startHide = hideChildView(dateChoiceHolder, dateChoiceHolder.childLayout);
                        startHide.start();
                        dateChoiceHolder.dateSwitch.setChecked(false);
                    }
                }
                break;
            case OPERATION_CHOICE_COUNT:
                NotifyCountHolder notifyCountHolder = (NotifyCountHolder) preHolder;
                if (isDetermine) {
                    notifyCountHolder.answerText.setText((String) data);
                } else {
                    if (notifyCountHolder.answerText.getText().equals("不重复")) {
                        Animator startHide = hideChildView(notifyCountHolder, notifyCountHolder.childLayout);
                        startHide.start();
                        notifyCountHolder.countSwitch.setChecked(false);
                    }
                }
                break;
        }

    }

    public void setShowMoreSet(ShowMoreSet showMoreSet) {
        this.showMoreSet = showMoreSet;
    }

    //    public static Animator ofItemViewHeight(RecyclerView.ViewHolder holder) {
//        View parent = (View) holder.itemView.getParent();
//        if (parent == null)
//            throw new IllegalStateException("Cannot animate the layout of a view that has no parent");
//
////        测量扩展动画的起始高度和结束高度
//        int start = holder.itemView.getMeasuredHeight();
//        holder.itemView.measure(View.MeasureSpec.makeMeasureSpec(parent.getMeasuredWidth(), View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        int end = holder.itemView.getMeasuredHeight();
////  6
//        final Animator animator = LayoutAnimator.ofHeight(holder.itemView, start, end);
////        设定该item在动画开始结束和取消时能否被recycle
////        animator.addListener(new ViewHolderAnimatorListener(holder));
////        设定结束时这个item的宽高
////        animator.addListener(new LayoutParamsAnimatorListener(holder.itemView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//
//        return animator;
//    }

}
