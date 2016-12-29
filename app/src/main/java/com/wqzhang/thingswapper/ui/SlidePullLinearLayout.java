package com.wqzhang.thingswapper.ui;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by wqzhang on 16-12-29.
 */

public class SlidePullLinearLayout extends LinearLayout  {
    public SlidePullLinearLayout(Context context) {
        super(context);
    }

    public SlidePullLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlidePullLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SlidePullLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    private Scroller mScroller;


    public void setScroller(Scroller mScroller) {
        this.mScroller = mScroller;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        // 判断滚动是否完成 true就是未完成
        if (mScroller.computeScrollOffset()) {
            this.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
