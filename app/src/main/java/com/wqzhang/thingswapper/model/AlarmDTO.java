package com.wqzhang.thingswapper.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.wqzhang.thingswapper.dao.greendao.ToDoThing;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wqzhang on 17-2-5.
 * xxxDTO 数据传输对象  命名法则
 */

public class AlarmDTO implements Parcelable {
    private ArrayList<String> toDoThingsContent;
    private ArrayList<Long> toDoThingsID;
    private Date notifyDate;
    private Integer reminderType;
    private ArrayList<Long> notifyIds;

    public AlarmDTO() {
    }

    public AlarmDTO(ArrayList<ToDoThing> toDoThings, Date notifyDate, Integer reminderType) {
        ArrayList<String> contents = new ArrayList<>();
        ArrayList<Long> ids = new ArrayList<>();
        for (ToDoThing toDoThing : toDoThings) {
            contents.add(toDoThing.getReminderContext());
            ids.add(toDoThing.getId());
        }
        this.toDoThingsContent = contents;
        this.toDoThingsID = ids;
        this.notifyDate = notifyDate;
        this.reminderType = reminderType;
    }

    public AlarmDTO(ArrayList<ToDoThing> toDoThings, Date notifyDate, Integer reminderType, ArrayList<Long> notifyId) {
        ArrayList<String> contents = new ArrayList<>();
        ArrayList<Long> ids = new ArrayList<>();
        for (ToDoThing toDoThing : toDoThings) {
            contents.add(toDoThing.getReminderContext());
            ids.add(toDoThing.getId());
        }
        this.toDoThingsContent = contents;
        this.toDoThingsID = ids;
        this.notifyDate = notifyDate;
        this.reminderType = reminderType;
        this.notifyIds = notifyId;
    }

    protected AlarmDTO(Parcel in) {
    }

    public ArrayList<String> getToDoThingsContent() {
        return toDoThingsContent;
    }

    public void setToDoThingsContent(ArrayList<String> toDoThingsContent) {
        this.toDoThingsContent = toDoThingsContent;
    }

    public Date getNotifyDate() {
        return notifyDate;
    }

    public void setNotifyDate(Date notifyDate) {
        this.notifyDate = notifyDate;
    }

    public Integer getReminderType() {
        return reminderType;
    }

    public void setReminderType(Integer reminderType) {
        this.reminderType = reminderType;
    }

    public ArrayList<Long> getToDoThingsID() {
        return toDoThingsID;
    }

    public void setToDoThingsID(ArrayList<Long> toDoThingsID) {
        this.toDoThingsID = toDoThingsID;
    }

    public ArrayList<Long> getNotifyIds() {
        return notifyIds;
    }

    public void setNotifyIds(ArrayList<Long> notifyIds) {
        this.notifyIds = notifyIds;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringList(toDoThingsContent);
        parcel.writeList(toDoThingsID);
        parcel.writeLong(notifyDate.getTime());
        parcel.writeInt(reminderType);
        parcel.writeList(notifyIds);
    }

    public static final Creator<AlarmDTO> CREATOR = new Creator<AlarmDTO>() {
        @Override
        public AlarmDTO createFromParcel(Parcel in) {
            AlarmDTO alarmModel = new AlarmDTO();
            alarmModel.toDoThingsContent = new ArrayList<>();
            alarmModel.toDoThingsID = new ArrayList<>();
            alarmModel.notifyIds = new ArrayList<>();
            in.readStringList(alarmModel.toDoThingsContent);
            in.readList(alarmModel.toDoThingsID, Long.class.getClassLoader());
            alarmModel.notifyDate = new Date(in.readLong());
            alarmModel.reminderType = in.readInt();
            in.readList(alarmModel.notifyIds, Long.class.getClassLoader());
            return alarmModel;

        }

        @Override
        public AlarmDTO[] newArray(int size) {
            return new AlarmDTO[size];
        }
    };
}
