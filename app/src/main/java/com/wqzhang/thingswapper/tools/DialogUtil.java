package com.wqzhang.thingswapper.tools;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.wqzhang.thingswapper.dao.BusinessProcess;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wqzhang on 17-2-5.
 */

public class DialogUtil {
    public static void showNotifyNow(Context context, ArrayList<String> sequences) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
               List<Long> _ids =  BusinessProcess.getInstance().readExpiredToDoThing().getNotifyIds();
                for(Long _tmpIds : _ids){
                    BusinessProcess.getInstance().setPreNotifyDate(_tmpIds,new Date());
                }
            }
        });
        builder.show();
    }

}
