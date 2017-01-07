package com.wqzhang.thingswapper.events;

/**
 * Created by wqzhang on 17-1-5.
 */

public class PullFreshScrollingEvent {
    public static final int TYPE_SCROLL_TO = 1;
    public static final int TYPE_START_SCROLL = 2;
    public static final int TYPE_START_SCROLL_WITH_DURATION = 3;
    public static final int TYPE_CHANGE_VIEW = 4;

    private int type;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private int duration;

    private PullFreshScrollingEvent() {
    }

    public PullFreshScrollingEvent(int type) {
        this.type = type;
    }

    public PullFreshScrollingEvent(int endX, int endY) {
        this.endX = endX;
        this.endY = endY;
        this.type = TYPE_SCROLL_TO;
    }

    public PullFreshScrollingEvent(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.type = TYPE_START_SCROLL;
    }

    public PullFreshScrollingEvent(int startX, int startY, int endX, int endY, int duration) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.duration = duration;
        this.type = TYPE_START_SCROLL_WITH_DURATION;
    }


    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndX() {
        return endX;
    }

    public int getEndY() {
        return endY;
    }

    public int getDuration() {
        return duration;
    }

    public int getType() {
        return type;
    }
}
