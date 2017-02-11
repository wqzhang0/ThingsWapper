package com.wqzhang.thingswapper.tools;

import android.os.Bundle;
import android.util.SparseArray;
import android.util.SparseLongArray;

import com.wqzhang.thingswapper.dao.greendao.ToDoThing;
import com.wqzhang.thingswapper.exceptions.CustomerException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by wqzhang on 17-1-20.
 */

public class NotifyParseUtil {
    static Bundle parseDoubleListToBundle(ArrayList<Long> ids, ArrayList<String> contents) throws CustomerException {
        if (ids.size() != contents.size()) {
            throw new CustomerException("传入ids 和contents 长度不一致");
        }
        Bundle bundle = new Bundle();

        long[] _ids = new long[ids.size()];

        for (int i = 0; i < ids.size(); i++) {
            _ids[i] = ids.get(i).longValue();
        }

        bundle.putLongArray("ids", _ids);
        bundle.putStringArrayList("contents", contents);

        return bundle;
    }

    static ArrayList<String> parseContents(Bundle bundle) {
        ArrayList<String> contents = bundle.getStringArrayList("contents");
        return contents;
    }

    static ArrayList<Long> parseIds(Bundle bundle) {
        long[] _ids = bundle.getLongArray("ids");
        ArrayList<Long> ids = new ArrayList<>();
        for (int i = 0; i < _ids.length; i++) {
            ids.add(_ids[i]);
        }
        return ids;
    }

    static Bundle parseMapToBundle(Map<Long, String> map) throws CustomerException {
        ArrayList<Long> ids = new ArrayList<>();
        ArrayList<String> contents = new ArrayList<>();
        for (Map.Entry<Long, String> entry : map.entrySet()) {
            ids.add(entry.getKey());
            contents.add(entry.getValue());
        }
        return parseDoubleListToBundle(ids, contents);
    }

    static Bundle parseTodoThingToBundle(List<ToDoThing> toDoThings) throws CustomerException {
        Map<Long, String> map = new HashMap<>();
        for (ToDoThing thing : toDoThings) {
            map.put(thing.getId(), thing.getReminderContext());
        }
        return parseMapToBundle(map);
    }

    static Map<Long, String> parseNotifyContents(Bundle bundle) {

        Map<Long, String> map = new HashMap<>();
        ArrayList<Long> ids = parseIds(bundle);
        ArrayList<String> contents = parseContents(bundle);
        for (int i = 0; i < ids.size(); i++) {
            map.put(ids.get(i), contents.get(i));
        }
        return map;
    }

    public static Integer getNotifyType(List<ToDoThing> toDoThings) {
        if (toDoThings == null || toDoThings.size() == 0) return Common.REMINDER_TYPE_NONE;
        //查找需要提醒事项里 提醒的种类
        // REMINDER_TYPE_VERTICAL = 1;
        // REMINDER_TYPE_ALARM = 2;
        // REMINDER_TYPE_EMAIL = 4;
        boolean _vertical = false;
        boolean _alarm = false;
        boolean _email = false;
        for (ToDoThing toDoThing : toDoThings) {
            int reminderType = toDoThing.getReminderType();
            if (reminderType == Common.REMINDER_TYPE_VERTICAL) {
                _vertical = true;
            } else if (reminderType == Common.REMINDER_TYPE_ALARM) {
                _alarm = true;
            } else if (reminderType == Common.REMINDER_TYPE_EMAIL) {
                _email = true;
            } else if (reminderType == (Common.REMINDER_TYPE_VERTICAL | Common.REMINDER_TYPE_ALARM)) {
                _vertical = true;
                _alarm = true;
            } else if (reminderType == (Common.REMINDER_TYPE_VERTICAL | Common.REMINDER_TYPE_EMAIL)) {
                _vertical = true;
                _email = true;
            } else if (reminderType == (Common.REMINDER_TYPE_ALARM | Common.REMINDER_TYPE_EMAIL)) {
                _alarm = true;
                _email = true;
            } else if (reminderType == (Common.REMINDER_TYPE_ALARM | Common.REMINDER_TYPE_EMAIL | Common.REMINDER_TYPE_VERTICAL)) {
                _vertical = true;
                _alarm = true;
                _email = true;
            }
        }
        Integer finalType = 0;
        if (_vertical) {
            finalType |= Common.REMINDER_TYPE_VERTICAL;
        }
        if (_alarm) {

            finalType |= Common.REMINDER_TYPE_ALARM;
        }
        if (_email) {
            finalType |= Common.REMINDER_TYPE_EMAIL;
        }
        return finalType;
    }

    public static boolean isVertical(int type) {
        boolean isVertical = false;
        //震动 type = 1
        if (type == 1 || type == 3 || type == 5 || type == 7) {
            isVertical = true;
        }
        return isVertical;
    }

    public static boolean isEmail(int type) {
        //邮件提醒类型 type = 4;
        boolean isEmail = false;
        if (type == 4 || type == 5 || type == 6 || type == 7) {
            isEmail = true;
        }
        return isEmail;
    }

    public static boolean isAlarm(int type) {
        //闹铃提醒 type = 2;
        boolean isAlarm = false;

        if (type == 2 || type == 3 || type == 6 || type == 7) {
            isAlarm = true;
        }
        return isAlarm;
    }

}
