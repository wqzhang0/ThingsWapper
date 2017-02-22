package com.wqzhang.thingswapper.util;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wqzhang on 16-12-30.
 */

public class LayoutAnimator {
    public static class LayoutHeightUpdateListener implements ValueAnimator.AnimatorUpdateListener {

        private final View mVview;

        public LayoutHeightUpdateListener(View view) {
            mVview = view;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            final ViewGroup.LayoutParams lp = mVview.getLayoutParams();
            lp.height = (int) animation.getAnimatedValue();
            mVview.setLayoutParams(lp);
        }

    }

    public static Animator ofHeight(View view, int start, int end) {
        final ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new LayoutHeightUpdateListener(view));
        return animator;
    }
}
