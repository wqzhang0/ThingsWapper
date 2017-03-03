package com.wqzhang.thingswapper.listener;

import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.listener.abs.OnScrolledListener;

/**
 * Created by wqzhang on 16-12-29.
 */

public class BottomLayoutOnScrolledListener extends OnScrolledListener {
    LinearLayout layout;
    TextView bottomTextView;
    ImageView bottomImgView;
    Context context;
    private final String lablePullChangeList = "继续拖动切换清单";
    private final String lableReleaseChangeList = "松开即可切换清单";

    final int STATE_NOSTATE = -1;
    final int STATE_PULL = 1;
    final int STATE_RELEASE = 2;

    int state = STATE_NOSTATE;

    public BottomLayoutOnScrolledListener(Context context, LinearLayout layout) {
        this.context = context;
        this.layout = layout;
        bottomTextView = (TextView) layout.findViewById(R.id.bottomTextView);
        bottomImgView = (ImageView) layout.findViewById(R.id.bottomImageView);
    }

    @Override
    public void onScrolledToUp() {
        layout.setVisibility(View.VISIBLE);

        if (state == STATE_PULL) {
            //状态无效
        } else {
            state = STATE_PULL;
            bottomTextView.setText(lablePullChangeList);
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.translate);
            animation.setInterpolator(new AccelerateDecelerateInterpolator());
            layout.startAnimation(animation);
        }


    }


    @Override
    public void onScrolledToUpComplete() {
        bottomTextView.setText(lableReleaseChangeList);
        layout.setVisibility(View.VISIBLE);
        state = STATE_RELEASE;
//        super.onScrolledToUpComplete();
    }

    @Override
    public void onScrolledToDown() {
//        super.onScrolledToDown();
    }

    @Override
    public void onScrolledToDownComplete() {
//        super.onScrolledToDownComplete();
    }

    @Override
    public void onScrolledReset() {
        layout.setVisibility(View.INVISIBLE);
        bottomTextView.setText(lablePullChangeList);
        layout.clearAnimation();

//        super.onScrolledReset();
    }
}
