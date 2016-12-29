package com.wqzhang.thingswapper.listener.abs;

/**
 * Created by wqzhang on 16-12-29.
 * 父类
 * 定义事件
 * 1.默认状态(正常滑动,未到达Top|Bottom点)
 * 2.下拉过程状态
 * 3.下拉完成状态
 * 4.上拉过程状态
 * 5.上拉完成状态
 */

public abstract class OnScrolledListener {

    public abstract void onScrolledToUp();

    public abstract void onScrolledToUpComplete();

    public abstract void onScrolledToDown();

    public abstract void onScrolledToDownComplete();

    public abstract void onScrolledReset();
}

