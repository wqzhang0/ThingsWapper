package com.wqzhang.thingswapper.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.wqzhang.thingswapper.MainApplication;
import com.wqzhang.thingswapper.dao.BusinessProcessImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wqzhang on 17-2-5.
 */

public class DialogUtil {

    private static AlertDialog.Builder builder;

    private static AlertDialog.Builder getBuilder() {
        if (builder == null) {
            builder = new AlertDialog.Builder(MainApplication.getDialogContext());
        }
        return builder;
    }

    public static void showNotifyNow(Context context, ArrayList<String> sequences) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog.Builder builder = getBuilder();
        String finalMsg = "";
        for (int i = 0; i < sequences.size(); i++) {
            if (i == sequences.size() - 1) {
                finalMsg += sequences.get(i);
            } else {
                finalMsg += sequences.get(i) + "\n\n";
            }
        }
        builder.setTitle("时间到了");
        builder.setMessage(finalMsg);
        builder.setNegativeButton("确定", null);
        builder.show();
    }

    public static void showHistoryThings(Context context, ArrayList<String> sequences) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog.Builder builder = getBuilder();
        builder.setTitle("你离开期间的通知");
        String finalMsg = "";

        for (int i = 0; i < sequences.size(); i++) {
            if (i == sequences.size() - 1) {
                finalMsg += sequences.get(i);
            } else {
                finalMsg += sequences.get(i) + "\n\n";
            }
        }
        builder.setMessage(finalMsg);
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                List<Long> tmpIds = BusinessProcessImpl.getInstance().listOnlineUserExpiredThings().getNotifyIds();
                for (Long id : tmpIds) {
                    BusinessProcessImpl.getInstance().updatePreNotifyDate(id, new Date());
                }
            }
        });
        builder.show();
    }

    public static void showAskLink(Context context,  String  sequences) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog.Builder builder =  new AlertDialog.Builder(context);
        builder.setTitle("注意");

        builder.setMessage(sequences);
        builder.setPositiveButton("跳过", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("AGA","选择了跳过");
            }
        });
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("AGA","选择了确定");
            }
        });
        builder.show();
    }

}
