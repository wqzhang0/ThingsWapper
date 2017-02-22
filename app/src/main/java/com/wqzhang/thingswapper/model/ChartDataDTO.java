package com.wqzhang.thingswapper.model;

import java.util.Date;

/**
 * Created by wqzhang on 17-1-16.
 */

public class ChartDataDTO {
    private Date date;
    private int Count;

    private ChartDataDTO() {
    }

    public ChartDataDTO(Date date, int count) {
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
