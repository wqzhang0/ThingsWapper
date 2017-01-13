package com.wqzhang.thingswapper.ui;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
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

    private Context mContext;

    private LinearLayout contentLinearLayout;

    private Scroller mScroller;

    private AbsListView.OnScrollListener mOnScrollListener;

    private int mBottomRightHolderWidth = 0;
    private int mBottomLeftHolderWidth = 0;

    private LinearLayout mContentView;
    private RelativeLayout mLeftRelativeView;
    private RelativeLayout mRightRelativeView;

    public SlideContentView(Context context, View contentView, int bottomLayoutId) {
        super(context);

        mContext = getContext();
        mScroller = new Scroller(mContext);

        setOrientation(LinearLayout.HORIZONTAL);
        View.inflate(mContext, R.layout.show_finsh_reminder_item_slide_view_merge, this);
        mContentView = (LinearLayout) findViewById(R.id.view_content);
        mLeftRelativeView = (RelativeLayout) findViewById(R.id.bottom_left);
        mRightRelativeView = (RelativeLayout) findViewById(R.id.bottom_right);

        ViewTreeObserver observerRight = mRightRelativeView.getViewTreeObserver();
        observerRight.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mRightRelativeView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mBottomRightHolderWidth = mRightRelativeView.getWidth();
            }
        });

        ViewTreeObserver observerLeft = mLeftRelativeView.getViewTreeObserver();
        observerLeft.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mLeftRelativeView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mBottomLeftHolderWidth = mLeftRelativeView.getWidth();
            }
        });

        setContentView(contentView);

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


    public LinearLayout getmContentView() {
        return mContentView;
    }


    public RelativeLayout getmLeftRelativeView() {
        return mLeftRelativeView;
    }

    public RelativeLayout getmRightRelativeView() {
        return mRightRelativeView;
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

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                first_press_down = getScaleX();
                first_press_down = event.getX();
                X = (int) event.getRawX();
                Y = (int) event.getY();
                isScollerContentView = false;
                break;
            case MotionEvent.ACTION_MOVE:

                int deltaX = (int) event.getRawX() - X;

                int touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
                if (deltaX == 0) deltaX = 1;
                if (Math.abs(deltaX) > touchSlop) {
                    isScollerContentView = true;
                }
                if (isScollerContentView) {
                    int noTouchSlopScroll = 0;
                    noTouchSlopScroll = deltaX - touchSlop;
                    Log.d(TAG, "deltaX" + deltaX);
                    Log.d(TAG, "touchSlop" + touchSlop);
                    Log.d(TAG, "noTouchSlopScroll" + noTouchSlopScroll);
                    if (Math.abs(noTouchSlopScroll) > mBottomRightHolderWidth) {
                        if (noTouchSlopScroll > 0) {
//                            mContentView.scrollTo(-mBottomRightHolderWidth, 0);
                            smoothScrollTo(-mBottomRightHolderWidth);
                        } else {
                            smoothScrollTo(mBottomRightHolderWidth);
//                            mContentView.scrollTo(mBottomRightHolderWidth, 0);
                        }
                    } else {
                        mContentView.scrollTo(-noTouchSlopScroll, 0);
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                int finalScrollX = mContentView.getScrollX();
                //左移动
                if (Math.abs(finalScrollX) > mBottomRightHolderWidth * 2 / 3) {
                    if (finalScrollX > 0) {
                        smoothScrollTo(mBottomRightHolderWidth);
                    } else {
                        smoothScrollTo(-mBottomRightHolderWidth);
                    }
                } else {
                    shrink();
                }

                break;
            default:
                break;
        }

        return isScollerContentView;

    }


    public void shrink() {
        if (mContentView.getScrollX() != 0) {
            smoothScrollTo(0);
        }
    }

    private void smoothScrollTo(int destX) {
        int scrollX = mContentView.getScrollX();
        int delta = destX - scrollX;
        mScroller.startScroll(scrollX, 0, delta, 0, (1 - Math.abs(scrollX / mBottomRightHolderWidth)) * 1000);
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
