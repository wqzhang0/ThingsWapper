package com.wqzhang.thingswapper.adapters;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.dao.AddThingOperationXMLData;
import com.wqzhang.thingswapper.events.SaveChooseOperationEvent;
import com.wqzhang.thingswapper.events.ShowMoreSetEvent;
import com.wqzhang.thingswapper.model.HistoryData;
import com.wqzhang.thingswapper.tools.Common;
import com.wqzhang.thingswapper.tools.DateUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Date;

import static com.wqzhang.thingswapper.tools.Common.REMINDER_TYPE_ALARM;
import static com.wqzhang.thingswapper.tools.Common.REMINDER_TYPE_EMAIL;
import static com.wqzhang.thingswapper.tools.Common.REMINDER_TYPE_VERTICAL;

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
    private EventBus bus;

    private RecyclerView.ViewHolder preHolder;

    private HistoryData historyData;

    NotifyDateRecyclerAdapter notifyDateRecyclerAdapter;
    int[] date = new int[]{0, 1, 2, 3};

    private AddToDoThingRecyclerAdapter() {
        super();
    }

    public AddToDoThingRecyclerAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);
        bus = EventBus.getDefault();
        bus.register(this);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case TYPE_EDIT:
                view = inflater.inflate(R.layout.edit_nofity_content_item, parent, false);
                return new EditHolder(view);
            case TYPE_NOTIFY_TYPE:
                view = inflater.inflate(R.layout.choice_notify_type_item, parent, false);
                return new NotifyTypeHolder(view);
            case TYPE_NOTIFY_DATE:
                view = inflater.inflate(R.layout.choice_notify_date_and_time_item, parent, false);
                return new DateChoiceHolder(view);
            case TYPE_NOTIFY_COUNT:
                view = inflater.inflate(R.layout.choice_notify_counts_item, parent, false);
                return new NotifyCountHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case TYPE_EDIT:
                final EditHolder editHolder = (EditHolder) holder;
                if (historyData != null && historyData.getContent() != null) {
                    editHolder.editText.setText(historyData.getContent());
                }
                editHolder.editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                        Log.d("onEditorAction Value", editHolder.editText.getText().toString());
                        return false;
                    }
                });
                editHolder.editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean hasFocus) {
                        if (hasFocus) {
                            Log.d("editText", "获得焦点 ");
                        } else {
                            Log.d("editText", "失去焦点 ");
                        }
                    }
                });

                break;
            case TYPE_NOTIFY_TYPE:
                final NotifyTypeHolder notifyTypeHolder = (NotifyTypeHolder) holder;
                int notifyType = 0;
                if (historyData != null) {
                    notifyType = historyData.getNotifyType();

                }
                if ((notifyType & Common.REMINDER_TYPE_ALARM) > 0) {
                    notifyTypeHolder.alarmCheckbox.setChecked(true);
                }
                if ((notifyType & Common.REMINDER_TYPE_EMAIL) > 0) {
                    notifyTypeHolder.emailCheckbox.setChecked(true);
                }
                if ((notifyType & Common.REMINDER_TYPE_VERTICAL) > 0) {
                    notifyTypeHolder.verticalCheckbox.setChecked(true);
                }
                notifyTypeHolder.alarmCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if (isChecked) {
                            bus.post(new SaveChooseOperationEvent(SaveChooseOperationEvent.TYPE_SAVE_NOTIFY_TYPE, REMINDER_TYPE_ALARM));
                        } else {
                            bus.post(new SaveChooseOperationEvent(SaveChooseOperationEvent.TYPE_SAVE_NOTIFY_TYPE, -REMINDER_TYPE_ALARM));
                        }
                    }
                });

                notifyTypeHolder.emailCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if (isChecked) {
                            bus.post(new SaveChooseOperationEvent(SaveChooseOperationEvent.TYPE_SAVE_NOTIFY_TYPE, REMINDER_TYPE_EMAIL));
                        } else {
                            bus.post(new SaveChooseOperationEvent(SaveChooseOperationEvent.TYPE_SAVE_NOTIFY_TYPE, -REMINDER_TYPE_EMAIL));
                        }
                    }
                });

                notifyTypeHolder.verticalCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if (isChecked) {
                            bus.post(new SaveChooseOperationEvent(SaveChooseOperationEvent.TYPE_SAVE_NOTIFY_TYPE, REMINDER_TYPE_VERTICAL));
                        } else {
                            bus.post(new SaveChooseOperationEvent(SaveChooseOperationEvent.TYPE_SAVE_NOTIFY_TYPE, -REMINDER_TYPE_VERTICAL));
                        }
                    }
                });
                break;
            case TYPE_NOTIFY_DATE:
                final DateChoiceHolder dateChoiceHolder = (DateChoiceHolder) holder;

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                dateChoiceHolder.recyclerView.setLayoutManager(linearLayoutManager);
                notifyDateRecyclerAdapter = new NotifyDateRecyclerAdapter(mContext);

                dateChoiceHolder.recyclerView.setAdapter(notifyDateRecyclerAdapter);
                if (historyData != null) {
                    if (historyData.getDates().size() != 0) {
                        notifyDateRecyclerAdapter.setDateList(historyData.getDates());
                        dateChoiceHolder.childLayout.setVisibility(View.VISIBLE);
                        dateChoiceHolder.dateSwitch.setChecked(true);
                    }
                }

                dateChoiceHolder.addDateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dateChoiceHolder.childLayout.setVisibility(View.VISIBLE);
                        Animator startOpen = showChildView(dateChoiceHolder);
                        startOpen.start();
                        bus.post(new ShowMoreSetEvent(ShowMoreSetEvent.SHOW_DATE));
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
                            bus.post(new ShowMoreSetEvent(ShowMoreSetEvent.SHOW_DATE));
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

                break;
            case TYPE_NOTIFY_COUNT:
                final NotifyCountHolder notifyCountHolder = (NotifyCountHolder) holder;

                //检验是否有上一次没操作完的数据  并展示
                if (historyData != null && !historyData.getNotifyCounts().equals("不重复")) {
                    String notifyCounts = historyData.getNotifyCounts();
                    notifyCountHolder.countSwitch.setChecked(true);
                    notifyCountHolder.answerText.setText(notifyCounts);
                    notifyCountHolder.childLayout.setVisibility(View.VISIBLE);
                }

                //点击 展示 重复提醒设置 视图
                notifyCountHolder.answerText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bus.post(new ShowMoreSetEvent(ShowMoreSetEvent.SHOW_COUNT));
                        currentOperation = OPERATION_CHOICE_COUNT;
                    }
                });
                //点击 switch 展开 重复提醒设置 视图 和子视图
                notifyCountHolder.countSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if (isChecked) {
                            //点击展开
                            notifyCountHolder.childLayout.setVisibility(View.VISIBLE);
                            Animator startOpen = showChildView(notifyCountHolder);
                            startOpen.start();
                            if (notifyCountHolder.answerText.getText().toString().equals("不重复")) {
                                bus.post(new ShowMoreSetEvent(ShowMoreSetEvent.SHOW_COUNT));
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

    /**
     * 设置历史数据
     *
     * @param historyData
     */
    public void setDataAndFlushView(HistoryData historyData) {
        this.historyData = historyData;
        this.notifyDataSetChanged();
    }

    //提醒内容展示Holder
    class EditHolder extends RecyclerView.ViewHolder {
        EditText editText;

        public EditHolder(View itemView) {
            super(itemView);
            editText = (EditText) itemView.findViewById(R.id.remind_content);
        }
    }

    //提醒类型展示Holder
    class NotifyTypeHolder extends RecyclerView.ViewHolder {
        CheckBox alarmCheckbox;
        CheckBox emailCheckbox;
        CheckBox verticalCheckbox;

        public NotifyTypeHolder(View itemView) {
            super(itemView);
            alarmCheckbox = (CheckBox) itemView.findViewById(R.id.alarm_checkbox);
            emailCheckbox = (CheckBox) itemView.findViewById(R.id.email_checkbox);
            verticalCheckbox = (CheckBox) itemView.findViewById(R.id.vertical_checkbox);
        }
    }

    //提醒时间展示holder
    class DateChoiceHolder extends RecyclerView.ViewHolder {
        Switch dateSwitch;
        LinearLayout childLayout;
        RecyclerView recyclerView;
        Button addDateBtn;

        public DateChoiceHolder(View itemView) {
            super(itemView);
            dateSwitch = (Switch) itemView.findViewById(R.id.reminder_time_switch);
            childLayout = (LinearLayout) itemView.findViewById(R.id.child_layout);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.child_recycler_view);
            addDateBtn = (Button) itemView.findViewById(R.id.choose_notify_date_and_time);
        }

    }

    //提醒次数holder 类型
    class NotifyCountHolder extends RecyclerView.ViewHolder {
        Switch countSwitch;
        TextView answerText;
        LinearLayout childLayout;

        public NotifyCountHolder(View itemView) {
            super(itemView);
            countSwitch = (Switch) itemView.findViewById(R.id.count_switch);
            childLayout = (LinearLayout) itemView.findViewById(R.id.child_layout);
            answerText = (TextView) itemView.findViewById(R.id.answer_text);
        }
    }


    /**
     * 方法用于 switch 被选择时的动画效果
     *
     * @param parentHolder
     * @return
     */
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

    /**
     * 根据操作的类型.保存各种数据
     *
     * @param saveChooseOperationEvent
     */
    @Subscribe
    public void save(SaveChooseOperationEvent saveChooseOperationEvent) {
        switch (currentOperation) {
            case OPERATION_CHOICE_DATE:
                if (saveChooseOperationEvent.isDetermine()) {

                    ArrayList<Date> dateTmp = notifyDateRecyclerAdapter.getDateList();
                    dateTmp.add(saveChooseOperationEvent.getDate());
                    notifyDateRecyclerAdapter.setDateList(dateTmp);
                    Animator startOpen = showChildView(preHolder);
                    startOpen.start();

                } else {
                    ArrayList<Date> dateTmp = notifyDateRecyclerAdapter.getDateList();
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
                if (saveChooseOperationEvent.isDetermine()) {
                    notifyCountHolder.answerText.setText(saveChooseOperationEvent.getNotifityCounts());
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
