package com.wqzhang.thingswapper.UI;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.wqzhang.thingswapper.R;

/**
 * Created by wqzhang on 16-11-29.
 * contentView 封装类
 */

public class SlideContentView extends LinearLayout {
    private final String TAG = "SlideContentView";

    public SlideContentView(Context context) {
        super(context);
        initView();
    }

    public SlideContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideContentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SlideContentView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private Context mContext;

    private LinearLayout contentLinearLayout;

    private Scroller mScroller;

    private AbsListView.OnScrollListener mOnScrollListener;

    private int mBottomRightHolderWidth = 120;
    private int mBottomLeftHolderWidth = 120;

    private LinearLayout mContentView;
    private RelativeLayout mLeftRelativeView;
    private RelativeLayout mRightRelativeView;

    private void initView() {
        mContext = getContext();
        mScroller = new Scroller(mContext);
        setOrientation(LinearLayout.HORIZONTAL);
        View.inflate(mContext, R.layout.slide_view_merge, this);
        mContentView = (LinearLayout) findViewById(R.id.view_content);
        mLeftRelativeView = (RelativeLayout) findViewById(R.id.bottom_left);
        mRightRelativeView = (RelativeLayout) findViewById(R.id.bottom_right);
    }

    public void setContentView(View view) {
        mContentView.addView(view);
    }

    float first_press_down = 0;

    int X = 0, Y = 0;
    int TAN = 2;
    boolean isScollerContentView = false;

    public void onRequeirTouchEvent(MotionEvent event) {
        Log.d(TAG, "onRequeirTouchEvent");

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                first_press_down = getScaleX();
                first_press_down = event.getX();
                X = (int) event.getX();
                Y = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                int deltaX = (int) event.getX() - X;
                int deltaY = (int) event.getY() - Y;

//                if (Math.abs(deltaX) < 35 && Math.abs(deltaY) < 15) {
                if (deltaX == 0) deltaX = 1;
                if (Math.abs(deltaY) / Math.abs(deltaX) > TAN) {
                    //上下滑动
                } else {
                    mContentView.scrollTo((int) (-(event.getX() - X)), 0);
//                    mContentView.scrollTo(10, 0);
                }

//                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;

        }
    }

    public void shrink() {
//        if (getScrollX() != 0) {
        smoothScrollTo(0);

//        }
    }

    private void smoothScrollTo(int destX) {
        int scrollX = getScrollX();
        int delta = destX - scrollX;
        // 以三倍时长滑向destX，效果就是慢慢滑动
        mScroller.startScroll(scrollX, 0, delta, 0, Math.abs(delta) * 3);
        invalidate();
    }

}
