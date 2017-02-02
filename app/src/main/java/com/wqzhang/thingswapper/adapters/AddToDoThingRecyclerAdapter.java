package com.wqzhang.thingswapper.adapters;

import android.animation.Animator;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.dao.AddThingOperationXMLData;
import com.wqzhang.thingswapper.dao.AddThingOperationXMLDataCache;
import com.wqzhang.thingswapper.events.ChangeAddThingSubmitStateEvent;
import com.wqzhang.thingswapper.events.DataCacheChange;
import com.wqzhang.thingswapper.events.SaveChooseOperationEvent;
import com.wqzhang.thingswapper.events.ShowMoreSetEvent;
import com.wqzhang.thingswapper.tools.Common;
import com.wqzhang.thingswapper.tools.ShowOrHideView;

import org.greenrobot.eventbus.EventBus;

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

    private RecyclerView.ViewHolder preHolder;

    private DateChoiceHolder dateChoiceHolder;
    private NotifyCountHolder notifyCountHolder;

    NotifyDateRecyclerAdapter notifyDateRecyclerAdapter;
    int[] ViewType = new int[]{0, 1, 2, 3};

    EventBus bus;

    private AddToDoThingRecyclerAdapter() {
        super();
    }

    public AddToDoThingRecyclerAdapter(Context context) {
        this.mContext = context;
        inflater = LayoutInflater.from(context);
        bus = EventBus.getDefault();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case TYPE_EDIT:
                view = inflater.inflate(R.layout.list_item_edit_nofity_content, parent, false);
                return new EditHolder(view);
            case TYPE_NOTIFY_TYPE:
                view = inflater.inflate(R.layout.list_item_choice_notify_type, parent, false);
                return new NotifyTypeHolder(view);
            case TYPE_NOTIFY_DATE:
                view = inflater.inflate(R.layout.list_item_choice_notify_date_and_time, parent, false);
                dateChoiceHolder = new DateChoiceHolder(view);
                return dateChoiceHolder;
            case TYPE_NOTIFY_COUNT:
                view = inflater.inflate(R.layout.list_item_choice_notify_counts, parent, false);
                notifyCountHolder = new NotifyCountHolder(view);
                return notifyCountHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case TYPE_EDIT:
                final EditHolder editHolder = (EditHolder) holder;
                String _content = AddThingOperationXMLDataCache.getContent();
                if (_content != null) {
                    if (_content.equals("")) {
                        bus.post(new ChangeAddThingSubmitStateEvent(ChangeAddThingSubmitStateEvent.TYPE_NOT_CLICK));
                    } else {
                        editHolder.editText.setText(_content);
                        bus.post(new ChangeAddThingSubmitStateEvent(ChangeAddThingSubmitStateEvent.TYPE_CAN_CLICK));
                    }
                }

                editHolder.editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        Log.d("beforeTextChanged Value", charSequence.toString());
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        Log.d("onTextChanged Value", charSequence.toString());
                        if (charSequence.length() == 0) {
                            bus.post(new ChangeAddThingSubmitStateEvent(ChangeAddThingSubmitStateEvent.TYPE_NOT_CLICK));
                        } else {
                            bus.post(new ChangeAddThingSubmitStateEvent(ChangeAddThingSubmitStateEvent.TYPE_CAN_CLICK));
                        }
                        bus.post(new SaveChooseOperationEvent(SaveChooseOperationEvent.TYPE_SAVE_CONTEXT, charSequence.toString()));

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                break;
            case TYPE_NOTIFY_TYPE:
                final NotifyTypeHolder notifyTypeHolder = (NotifyTypeHolder) holder;
                int notifyType = AddThingOperationXMLDataCache.getNotifyType();
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

                //检查历史是否有数据,并判断上次是否设置了允许提醒
                ArrayList<Date> dates = AddThingOperationXMLDataCache.getDates();
                if (dates.size() != 0) {
                    notifyDateRecyclerAdapter.setDateList(dates);
                    if (AddThingOperationXMLData.getInstall().isReminder()) {
                        dateChoiceHolder.childLayout.setVisibility(View.VISIBLE);
                        dateChoiceHolder.dateSwitch.setChecked(true);
                    }
                }

                dateChoiceHolder.addDateText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dateChoiceHolder.childLayout.setVisibility(View.VISIBLE);
                        Animator startOpen = ShowOrHideView.showChildView(dateChoiceHolder);
                        startOpen.start();
                        bus.post(new ShowMoreSetEvent(ShowMoreSetEvent.SHOW_DATE));
                        currentOperation = OPERATION_CHOICE_DATE;
                        preHolder = holder;
                    }
                });
                dateChoiceHolder.dateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        currentOperation = OPERATION_CHOICE_DATE;

                        if (isChecked) {
                            //点击展开
                            dateChoiceHolder.childLayout.setVisibility(View.VISIBLE);
                            Animator startOpen = ShowOrHideView.showChildView(dateChoiceHolder);
                            startOpen.start();
                            if (AddThingOperationXMLDataCache.getDates().size() == 0) {
                                bus.post(new ShowMoreSetEvent(ShowMoreSetEvent.SHOW_DATE));
                            }
                            bus.post(new SaveChooseOperationEvent(SaveChooseOperationEvent.TYPE_IS_REMINDER, true));

                            //如果设置的提醒次数大于1   则设置为不重复
                            hideCountView();


                        } else {
                            //点击折叠
                            Animator startHide = ShowOrHideView.hideChildView(dateChoiceHolder, dateChoiceHolder.childLayout);

                            bus.post(new SaveChooseOperationEvent(SaveChooseOperationEvent.TYPE_IS_REMINDER, false));

                            startHide.start();

                            //当data.size>1  时    提醒次数 item  被隐藏
                            //这里做判断  如果> 1 则代表item被隐藏, 此处显示 item
                            //如果提醒时间 条数> 1  不允许重复
                            initAndShowCountView();
                        }
                        preHolder = holder;
                    }
                });

                break;
            case TYPE_NOTIFY_COUNT:
                final NotifyCountHolder notifyCountHolder = (NotifyCountHolder) holder;

                //检验是否有上一次没操作完的数据  并展示
                String notifyCounts = AddThingOperationXMLDataCache.getNotifyCounts();
                if (!notifyCounts.equals("不重复")) {
                    notifyCountHolder.answerText.setText(notifyCounts);

                    if (AddThingOperationXMLData.getInstall().isRepeat()) {
                        notifyCountHolder.countSwitch.setChecked(true);
                        notifyCountHolder.childLayout.setVisibility(View.VISIBLE);
                    }
                }

                //如果提醒时间 条数> 1  不允许重复
                if (AddThingOperationXMLDataCache.getDates().size() > 1 && AddThingOperationXMLData.getInstall().isReminder()) {
                    hideCountView();
                }


                //点击 展示 重复提醒设置 视图
                notifyCountHolder.answerText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bus.post(new ShowMoreSetEvent(ShowMoreSetEvent.SHOW_COUNT));
                        currentOperation = OPERATION_CHOICE_COUNT;
                        preHolder = holder;
                    }
                });
                //点击 switch 展开 重复提醒设置 视图 和子视图
                notifyCountHolder.countSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if (isChecked) {
                            //点击展开
                            notifyCountHolder.childLayout.setVisibility(View.VISIBLE);
                            Animator startOpen = ShowOrHideView.showChildView(notifyCountHolder);
                            startOpen.start();
                            if (notifyCountHolder.answerText.getText().toString().equals("不重复")) {
                                bus.post(new ShowMoreSetEvent(ShowMoreSetEvent.SHOW_COUNT));
                            }
                            //将xml里存储的 是否重复提醒字段(IS_REPEAT) 值更改为true
                            bus.post(new SaveChooseOperationEvent(SaveChooseOperationEvent.TYPE_IS_REPEAT, true));

                            hideCountView();
                        } else {
                            //点击折叠
                            Animator startHide = ShowOrHideView.hideChildView(notifyCountHolder, notifyCountHolder.childLayout);
                            startHide.start();

                            //将xml里存储的 是否重复提醒字段(IS_REPEAT) 值更改为false
                            bus.post(new SaveChooseOperationEvent(SaveChooseOperationEvent.TYPE_IS_REPEAT, false));

                            initAndShowCountView();
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
        return ViewType.length;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    /**
     * 判断 是否提醒时间 的数量大于2  如果大于2  则隐藏重复选择条目
     */
    public void hideCountView() {
        //如果设置的提醒次数大于1   则设置为不重复
        if (AddThingOperationXMLDataCache.getDates().size() > 1 && AddThingOperationXMLData.getInstall().isReminder()) {
            notifyCountHolder.countSwitch.setChecked(false);
            notifyCountHolder.itemView.setVisibility(View.INVISIBLE);
            //将xml里存储的 是否重复提醒字段(IS_REPEAT) 值更改为false
            bus.post(new SaveChooseOperationEvent(SaveChooseOperationEvent.TYPE_IS_REPEAT, false));
        }
    }

    public void showCountView() {
        if (!(AddThingOperationXMLDataCache.getDates().size() > 1 && AddThingOperationXMLData.getInstall().isReminder())) {
            notifyCountHolder.countSwitch.setChecked(false);
            notifyCountHolder.itemView.setVisibility(View.VISIBLE);
            //将xml里存储的 是否重复提醒字段(IS_REPEAT) 值更改为false
            bus.post(new SaveChooseOperationEvent(SaveChooseOperationEvent.TYPE_IS_REPEAT, false));
        }
    }

    /**
     * 当折叠时.初始化并且显示  提醒重复 条目
     */
    public void initAndShowCountView() {
        //如果设置的提醒次数大于1   则设置为不重复
        if (AddThingOperationXMLDataCache.getDates().size() > 1) {
            notifyCountHolder.countSwitch.setChecked(false);
            notifyCountHolder.itemView.setVisibility(View.VISIBLE);

            Animator startHideCountView = ShowOrHideView.hideChildView(notifyCountHolder, notifyCountHolder.childLayout);
            startHideCountView.start();
        }
    }

    /**
     * 根据操作的类型.保存各种数据
     *
     * @param dataCacheChange
     */
    public void save(DataCacheChange dataCacheChange) {
        //如果是删除事件  此处做处理
        if (dataCacheChange.getType() == DataCacheChange.TYPE_REMOVE_NOTIFY_DATE_CHANGE) {
            if (dataCacheChange.isDetermine()) {
                ArrayList<Date> dateTmp = AddThingOperationXMLDataCache.getDates();
                notifyDateRecyclerAdapter.setDateList(AddThingOperationXMLDataCache.getDates());
                if (dateTmp.size() == 0) {
                    Animator startHide = ShowOrHideView.hideChildView(dateChoiceHolder, dateChoiceHolder.childLayout);
                    dateChoiceHolder.dateSwitch.setChecked(false);
                    startHide.start();

                } else if (dateTmp.size() == 1) {
                    //将 重复提醒次数  条目展示出来
                    showCountView();
                    Animator startOpen = ShowOrHideView.showChildView(dateChoiceHolder);
                    startOpen.start();
                } else if (dateTmp.size() > 1) {
                    Animator startOpen = ShowOrHideView.showChildView(dateChoiceHolder);
                    startOpen.start();
                }
            }
        } else if (dataCacheChange.getType() == DataCacheChange.TYPE_NOTYFLY_DATE_CHANGE) {
            //提醒时间改变 增加事件
            notifyDateRecyclerAdapter.setDateList(AddThingOperationXMLDataCache.getDates());
            Animator startOpen = ShowOrHideView.showChildView(dateChoiceHolder);
            startOpen.start();
            //如果设置的提醒次数大于1   则设置为不重复
            hideCountView();

        } else if (dataCacheChange.getType() == DataCacheChange.TYPE_NOTIFLY_DATE_CANCEL) {
            //时间选择  点击了取消按钮
            ArrayList<Date> dateTmp = notifyDateRecyclerAdapter.getDateList();
            if (dateTmp.size() == 0) {
                DateChoiceHolder dateChoiceHolder = ((DateChoiceHolder) preHolder);
                Animator startHide = ShowOrHideView.hideChildView(dateChoiceHolder, dateChoiceHolder.childLayout);
                startHide.start();
                dateChoiceHolder.dateSwitch.setChecked(false);
            }
        } else if (dataCacheChange.getType() == DataCacheChange.TYPE_CANCEL) {
            //取消事件
            if (currentOperation == OPERATION_CHOICE_DATE) {
                ArrayList<Date> dateTmp = notifyDateRecyclerAdapter.getDateList();
                if (dateTmp.size() == 0) {
                    DateChoiceHolder dateChoiceHolder = ((DateChoiceHolder) preHolder);
                    Animator startHide = ShowOrHideView.hideChildView(dateChoiceHolder, dateChoiceHolder.childLayout);
                    startHide.start();
                    dateChoiceHolder.dateSwitch.setChecked(false);
                }
            } else if (currentOperation == OPERATION_CHOICE_COUNT) {
                //如果上一次的操作是 提醒次数按钮 则 此时隐藏 次数选择的子视图
                NotifyCountHolder notifyCountHolder = (NotifyCountHolder) preHolder;

                if (notifyCountHolder.answerText.getText().equals("不重复")) {
                    Animator startHide = ShowOrHideView.hideChildView(notifyCountHolder, notifyCountHolder.childLayout);
                    startHide.start();
                    notifyCountHolder.countSwitch.setChecked(false);
                }
            }
        } else if (dataCacheChange.getType() == DataCacheChange.TYPE_NOTYFLY_COUNTS_CHANGE) {
            notifyCountHolder.answerText.setText(dataCacheChange.getNotifityCounts());
        }
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
        TextView addDateText;

        public DateChoiceHolder(View itemView) {
            super(itemView);
            dateSwitch = (Switch) itemView.findViewById(R.id.reminder_time_switch);
            childLayout = (LinearLayout) itemView.findViewById(R.id.child_layout);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.child_recycler_view);
            addDateText = (TextView) itemView.findViewById(R.id.choose_notify_date_and_time);
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
}
