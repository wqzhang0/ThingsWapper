package com.wqzhang.thingswapper.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.github.mikephil.charting.data.BaseEntry;

import java.util.Date;

/**
 * Created by wqzhang on 17-1-22.
 */

public class ChartEntry extends BaseEntry implements Parcelable {
    protected ChartEntry(Parcel in) {
    }

    private Date date;

    public ChartEntry(Date date, float y) {
        super(y);
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public static final Creator<ChartEntry> CREATOR = new Creator<ChartEntry>() {
        @Override
        public ChartEntry createFromParcel(Parcel in) {
            return new ChartEntry(in);
        }

        @Override
        public ChartEntry[] newArray(int size) {
            return new ChartEntry[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
