package com.wqzhang.thingswapper.tools;

import android.animation.Animator;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by wqzhang on 17-1-9.
 */

public class ShowOrHideView {
    /**
     * 方法用于 switch 被选择时的动画效果
     *
     * @param parentHolder
     * @return
     */
    public static Animator showChildView(RecyclerView.ViewHolder parentHolder) {
        View parent = (View) parentHolder.itemView.getParent();
        if (parent == null)
            throw new IllegalStateException("Cannot animate the layout of a view that has no parent");
        int start = parentHolder.itemView.getMeasuredHeight();
        parentHolder.itemView.measure(View.MeasureSpec.makeMeasureSpec(parent.getMeasuredWidth(), View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        int end = parentHolder.itemView.getMeasuredHeight();
        final Animator animator = LayoutAnimator.ofHeight(parentHolder.itemView, start, end);
        return animator;
    }

    public static Animator hideChildView(RecyclerView.ViewHolder parentHolder, View childView) {
        View parent = (View) parentHolder.itemView.getParent();
        if (parent == null)
            throw new IllegalStateException("Cannot animate the layout of a view that has no parent");
        int start = parentHolder.itemView.getMeasuredHeight();
        childView.measure(View.MeasureSpec.makeMeasureSpec(parent.getMeasuredWidth(), View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        int childHeght = childView.getHeight();
        parentHolder.itemView.measure(View.MeasureSpec.makeMeasureSpec(parent.getMeasuredWidth(), View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        int end = start - childHeght;
        final Animator animator = LayoutAnimator.ofHeight(parentHolder.itemView, start, end);
        return animator;
    }

}
