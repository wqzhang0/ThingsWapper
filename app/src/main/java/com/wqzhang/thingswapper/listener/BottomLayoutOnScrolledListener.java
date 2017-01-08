package com.wqzhang.thingswapper.listener;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.listener.abs.OnScrolledListener;

/**
 * Created by wqzhang on 16-12-29.
 */

public class BottomLayoutOnScrolledListener extends OnScrolledListener {
    LinearLayout layout;
    TextView textView;

    private final  String text1 = "继续拖动将切换至已做清单";
    private final  String text2 = "松开切换至已做清单";

    public BottomLayoutOnScrolledListener(LinearLayout layout) {
        this.layout = layout;
        textView = (TextView) layout.findViewById(R.id.bottomTextView);
    }

    @Override
    public void onScrolledToUp() {
        layout.setVisibility(View.VISIBLE);
        textView.setText(text1);
    }


    @Override
    public void onScrolledToUpComplete() {
        textView.setText(text2);
        layout.setVisibility(View.VISIBLE);
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
        textView.setText(text1);
//        super.onScrolledReset();
    }
}
