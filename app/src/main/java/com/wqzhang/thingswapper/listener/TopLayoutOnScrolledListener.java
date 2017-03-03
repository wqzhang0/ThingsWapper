package com.wqzhang.thingswapper.listener;

import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.listener.abs.OnScrolledListener;

/**
 * Created by wqzhang on 16-12-29.
 */

public class TopLayoutOnScrolledListener extends OnScrolledListener {
    LinearLayout layout;
    TextView topTextView;
    ImageView topImageView;
    Context context;

    final int STATE_NOSTATE = -1;
    final int STATE_PULL = 1;
    final int STATE_RELEASE = 2;

    int state = STATE_NOSTATE;

    private final String lablePleasePull = "继续拖动将新增事件";
    private final String lableReleaseToNewAction = "松开新增事件";

    public TopLayoutOnScrolledListener(Context context, LinearLayout layout) {
        this.context = context;
        this.layout = layout;
        topTextView = (TextView) layout.findViewById(R.id.TopTextView);
        topImageView = (ImageView) layout.findViewById(R.id.TopImageView);
    }

    @Override
    public void onScrolledToUp() {
//        super.onScrolledToUp();
    }

    @Override
    public void onScrolledToUpComplete() {
//        super.onScrolledToUpComplete();
    }

    @Override
    public void onScrolledToDown() {
        layout.setVisibility(View.VISIBLE);
        if (state == STATE_PULL) {
            //状态无效
        } else {
            state = STATE_PULL;
            topTextView.setText(lablePleasePull);
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.rotate);
            animation.setInterpolator(new AccelerateDecelerateInterpolator());
            topImageView.startAnimation(animation);
        }
//        super.onScrolledToDown();
    }

    @Override
    public void onScrolledToDownComplete() {
//        super.onScrolledToDownComplete();
        topTextView.setText(lableReleaseToNewAction);
        if (state == STATE_RELEASE) {
            //状态无效
        } else {
            state = STATE_RELEASE;

            topTextView.setText(lablePleasePull);
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.scale);
            animation.setInterpolator(new AccelerateDecelerateInterpolator());
            topImageView.startAnimation(animation);
        }
    }

    @Override
    public void onScrolledReset() {
        layout.setVisibility(View.INVISIBLE);
        state = STATE_NOSTATE;
        topImageView.clearAnimation();
//        super.onScrolledReset();
    }
}
