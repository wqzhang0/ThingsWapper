package com.wqzhang.thingswapper.model;

import java.util.Date;

/**
 * Created by wqzhang on 17-1-16.
 */

public class ChartDataModel {
    private Date date;
    private int Count;

    private ChartDataModel() {
    }

    public ChartDataModel(Date date, int count) {
        this.date = date;
        this.Count = count;
    }

    public Date getDate() {
        return date;
    }

    public int getCount() {
        return Count;
    }

}
