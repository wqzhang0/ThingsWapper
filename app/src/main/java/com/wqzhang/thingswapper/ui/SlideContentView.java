package com.wqzhang.thingswapper.ui;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.wqzhang.thingswapper.R;

/**
 * Created by wqzhang on 16-11-29.
 * contentView 封装类
 */

public class SlideContentView extends LinearLayout implements View.OnClickListener {
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
    private ImageButton mFinshImgBtn;

    public LinearLayout getmContentView() {
        return mContentView;
    }


    public RelativeLayout getmLeftRelativeView() {
        return mLeftRelativeView;
    }

    public RelativeLayout getmRightRelativeView() {
        return mRightRelativeView;
    }

    public ImageButton getmFinshImgBtn() {
        return mFinshImgBtn;
    }


    private void initView() {
        mContext = getContext();
        mScroller = new Scroller(mContext);

        setOrientation(LinearLayout.HORIZONTAL);
        View.inflate(mContext, R.layout.slide_view_merge, this);
        mContentView = (LinearLayout) findViewById(R.id.view_content);
        mLeftRelativeView = (RelativeLayout) findViewById(R.id.bottom_left);
        mRightRelativeView = (RelativeLayout) findViewById(R.id.bottom_right);
        mFinshImgBtn = (ImageButton) findViewById(R.id.finsh);
    }

    public void setContentView(View view) {
        mContentView.addView(view);
    }


    float first_press_down = 0;

    int X = 0, Y = 0;
    int TAN = 2;
    boolean isScollerContentView = false;

    public boolean onRequeirTouchEvent(MotionEvent event) {
//        Log.d(TAG, "onRequeirTouchEvent");
        boolean result = false;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                first_press_down = getScaleX();
                first_press_down = event.getX();
                X = (int) event.getX();
                Y = (int) event.getY();
                result = false;
                break;
            case MotionEvent.ACTION_MOVE:

                int deltaX = (int) event.getX() - X;
                int deltaY = (int) event.getY() - Y;

                int touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
//                if (Math.abs(deltaX) < 35 && Math.abs(deltaY) < 15) {
                if (deltaX == 0) deltaX = 1;
//
//                if(Math.abs(deltaY)>touchSlop){
//
//                }
                if (Math.abs(deltaX) > touchSlop) {
                    result = true;
                    if (Math.abs(deltaX) > 350) {
                        deltaX = deltaX > 0 ? 350 : -350;
                    }
                    mContentView.scrollTo((int) (-deltaX), 0);
                }

                break;
            case MotionEvent.ACTION_UP:
                int finalScrollX = mContentView.getScrollX();

                //左移动
                if (Math.abs(finalScrollX) > 350 * 2 / 3) {
                    if (finalScrollX > 0) {
                        smoothScrollTo(350);
                    } else {
                        smoothScrollTo(-350);
                    }
                } else {
                    shrink();
                }

                break;
            default:
                break;
        }

        return result;

    }


    public void shrink() {
        if (mContentView.getScrollX() != 0) {
            smoothScrollTo(0);
        }
    }

    private void smoothScrollTo(int destX) {
        int scrollX = mContentView.getScrollX();
        int delta = destX - scrollX;
        mScroller.startScroll(scrollX, 0, delta, 0, 1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        // 判断滚动是否完成 true就是未完成
        if (mScroller.computeScrollOffset()) {
            mContentView.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.finsh) {
            Log.d(TAG, "finsh btn is clicked");
        }
    }
}
