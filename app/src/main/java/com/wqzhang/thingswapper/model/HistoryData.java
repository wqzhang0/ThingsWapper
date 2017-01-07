package com.wqzhang.thingswapper.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wqzhang on 17-1-7.
 */

public class HistoryData {
    private String content;
    private int notifyType;
    private ArrayList<Date> dates;
    private String notifyCounts;

    public HistoryData(String content, int notifyType, ArrayList<Date> dates, String notifyCounts) {
        this.content = content;
        this.notifyType = notifyType;
        this.dates = dates;
        this.notifyCounts = notifyCounts;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(int notifyType) {
        this.notifyType = notifyType;
    }

    public ArrayList<Date> getDates() {
        return dates;
    }

    public void setDates(ArrayList<Date> dates) {
        this.dates = dates;
    }

    public String getNotifyCounts() {
        return notifyCounts;
    }

    public void setNotifyCounts(String notifyCounts) {
        this.notifyCounts = notifyCounts;
    }
}
