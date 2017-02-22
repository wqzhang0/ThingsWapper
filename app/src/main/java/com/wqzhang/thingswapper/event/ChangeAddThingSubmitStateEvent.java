package com.wqzhang.thingswapper.event;

/**
 * Created by wqzhang on 17-1-8.
 */

public class ChangeAddThingSubmitStateEvent {
    private int Type;

    public final static int TYPE_CAN_CLICK = 1;
    public final static int TYPE_NOT_CLICK = 2;

    private ChangeAddThingSubmitStateEvent() {
    }

    public ChangeAddThingSubmitStateEvent(int type) {
        Type = type;
    }

    public int getType() {
        return Type;
    }
}
