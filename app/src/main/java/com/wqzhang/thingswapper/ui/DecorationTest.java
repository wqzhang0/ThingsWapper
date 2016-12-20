package com.wqzhang.thingswapper.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by wqzhang on 16-10-30.
 * item 分割条  需要继承ItemDecoration
 */

public class DecorationTest extends RecyclerView.ItemDecoration {
    private final String TAG = "DecorationTest";
    private static int[] attrs = new int[]{android.R.attr.listDivider};
    private Drawable mDrawable;

    public DecorationTest(Context context) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs);
        mDrawable = typedArray.getDrawable(0);
        mDrawable.setColorFilter(Color.parseColor("#123123"), PorterDuff.Mode.ADD);
        typedArray.recycle();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//        super.onDraw(c, parent, state);
//        int left = parent.getLeft();
//        int right = parent.getRight();
//        int top = parent.getTop();
//        int bottom = parent.getBottom();
//
//        mDrawable.setBounds(left,top,right,bottom);
//        mDrawable.draw(c);
        Log.d(TAG, "onDraw");
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + layoutParams.bottomMargin;
            int bottom = top + mDrawable.getIntrinsicHeight();
            //marginleft 10    margin right 10
            mDrawable.setBounds(left - 10, top - 1, right - 10, bottom + 1);

            mDrawable.draw(c);
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        Log.d(TAG, "onDrawOver");
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        Log.d(TAG, "getItemOffsets");
        outRect.set(0, 0, 0, 1);
//        super.getItemOffsets(outRect, view, parent, state);
    }
}
