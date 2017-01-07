package com.wqzhang.thingswapper.exceptions;

import android.util.Log;

public class CustomerException extends Exception {
    private String msg;

    private String TAG = "CustomerException";

    public CustomerException() {
        super();
    }

    public CustomerException(String msg) {
        super();
        this.msg = msg;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
        System.out.println(msg);
        Log.e(TAG, msg);
    }
}
