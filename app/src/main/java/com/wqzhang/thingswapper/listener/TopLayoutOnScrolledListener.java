package com.wqzhang.thingswapper.listener;

import android.media.Image;
import android.view.View;
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

    private final String text1 = "继续拖动将新增事件";
    private final String text2 = "松开新增事件";

    public TopLayoutOnScrolledListener(LinearLayout layout) {
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
        topTextView.setText(text1);
//        super.onScrolledToDown();
    }

    @Override
    public void onScrolledToDownComplete() {
//        super.onScrolledToDownComplete();
        topTextView.setText(text2);

    }

    @Override
    public void onScrolledReset() {
        layout.setVisibility(View.INVISIBLE);
//        super.onScrolledReset();
    }
}
