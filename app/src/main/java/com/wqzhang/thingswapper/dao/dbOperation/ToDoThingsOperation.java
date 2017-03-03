package com.wqzhang.thingswapper.dao.dbOperation;

import com.wqzhang.thingswapper.dao.greendao.DaoSession;
import com.wqzhang.thingswapper.dao.greendao.ToDoThing;
import com.wqzhang.thingswapper.dao.greendao.ToDoThingDao;
import com.wqzhang.thingswapper.dao.greendao.User;
import com.wqzhang.thingswapper.dao.dbOperation.impl.ToDoThingsOperationImpl;
import com.wqzhang.thingswapper.model.ChartDataDTO;
import com.wqzhang.thingswapper.util.Common;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wqzhang on 17-3-2.
 */

public class ToDoThingsOperation implements ToDoThingsOperationImpl {

    static ToDoThingDao toDoThingDao;

    private ToDoThingsOperation() {
    }

    public ToDoThingsOperation(DaoSession mDaoSession) {
        toDoThingDao = mDaoSession.getToDoThingDao();
    }


    @Override
    public ArrayList<ToDoThing> listFinshThingsOrderByFinshTimeDesc() {
        QueryBuilder<ToDoThing> finshQueryBuilder = toDoThingDao.queryBuilder();
        ArrayList<ToDoThing> toDoThings = (ArrayList<ToDoThing>) finshQueryBuilder.where(ToDoThingDao.Properties.Status.eq(Common.STATUS_FINSH)).orderDesc(ToDoThingDao.Properties.FinshDate).list();
        return toDoThings;
    }

    @Override
    public ArrayList<ToDoThing> listNotDoneThingsOrderByCreateTimeDesc() {
        QueryBuilder<ToDoThing> notDoneQueryBuilder = toDoThingDao.queryBuilder();
        ArrayList<ToDoThing> toDoThings = (ArrayList<ToDoThing>) notDoneQueryBuilder.where(ToDoThingDao.Properties.Status.eq(Common.STATUS_TO_BE_DONE)).orderDesc(ToDoThingDao.Properties.CreateDate).list();
        return toDoThings;
    }

    @Override
    public ArrayList<ToDoThing> listAllThingsByUser(User user) {
        return null;
    }

    @Override
    public ArrayList<ToDoThing> listAllThingsByUserId(int userId) {
        return null;
    }

    @Override
    public ArrayList<ToDoThing> listAllThings() {
        Query<ToDoThing> todoThingQueue = toDoThingDao.queryBuilder().build();
        ArrayList<ToDoThing> toDoThings = (ArrayList<ToDoThing>) todoThingQueue.list();
        return toDoThings;
    }

    @Override
    public void removeToDoTingById(Long id) {
        ToDoThing toDoThing = getThingById(id);
        if (toDoThing != null) {
            toDoThingDao.delete(toDoThing);
        }
    }

    @Override
    public ToDoThing getThingById(Long id) {
        ToDoThing toDoThing = null;
        QueryBuilder<ToDoThing> toDoThingQueryBuilder = toDoThingDao.queryBuilder();
        ArrayList<ToDoThing> toDoThings = (ArrayList<ToDoThing>) toDoThingQueryBuilder.where(ToDoThingDao.Properties.Id.eq(id)).list();
        if (toDoThings != null || toDoThings.size() != 0) {
            toDoThing = toDoThings.get(0);
        }
        return toDoThing;
    }

    @Override
    public void saveThing(ToDoThing toDoThing) {
        toDoThingDao.insert(toDoThing);
    }

    @Override
    public void updateThingState(Long id, int state) {
        ToDoThing toDoThing = getThingById(id);
        if (state == Common.STATUS_FINSH) {
            toDoThing.setFinshDate(new Date());
        }
        toDoThing.setStatus(state);
        toDoThingDao.update(toDoThing);
    }

    @Override
    public ArrayList<ChartDataDTO> countRecentWeekNewThings() {
        ArrayList<ChartDataDTO> arrayList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DAY_OF_YEAR, -6);

        for (int i = 0; i < 7; i++) {
            Date dayStart = calendar.getTime();
            calendar.add(calendar.DATE, +1);
            Date dayEnd = calendar.getTime();

            QueryBuilder<ToDoThing> toDoThingQueryBuilder = toDoThingDao.queryBuilder();

            int size = toDoThingQueryBuilder
                    .where(ToDoThingDao.Properties.CreateDate.ge(dayStart), ToDoThingDao.Properties.CreateDate.lt(dayEnd))
                    .list().size();

            arrayList.add(new ChartDataDTO(calendar.getTime(), size));
        }

        return arrayList;
    }

    @Override
    public ArrayList<ChartDataDTO> countRecentWeekFinshThings() {
        ArrayList<ChartDataDTO> arrayList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DAY_OF_YEAR, -6);

        for (int i = 0; i < 7; i++) {
            long dayStart = calendar.getTime().getTime();
            calendar.add(calendar.DATE, +1);
            long dayEnd = calendar.getTime().getTime();

            QueryBuilder<ToDoThing> toDoThingQueryBuilder = toDoThingDao.queryBuilder();

            int size = toDoThingQueryBuilder
                    .where(ToDoThingDao.Properties.FinshDate.ge(dayStart), ToDoThingDao.Properties.FinshDate.lt(dayEnd))
                    .list().size();

            arrayList.add(new ChartDataDTO(calendar.getTime(), size));
        }

        return arrayList;
    }

    @Override
    public ArrayList<ChartDataDTO> countTodayThings() {

        ArrayList<ChartDataDTO> arrayList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date dayStart = calendar.getTime();
        calendar.add(calendar.DATE, +1);
        Date dayEnd = calendar.getTime();

        QueryBuilder<ToDoThing> toDoThingQueryBuilder = toDoThingDao.queryBuilder();

        int todayNewThingsCounts = toDoThingQueryBuilder
                .where(ToDoThingDao.Properties.CreateDate.ge(dayStart), ToDoThingDao.Properties.CreateDate.lt(dayEnd))
                .list().size();

        int todayFinshThingsCounts = toDoThingQueryBuilder
                .where(ToDoThingDao.Properties.FinshDate.ge(dayStart), ToDoThingDao.Properties.FinshDate.lt(dayEnd))
                .list().size();

        int toBeDoneThingsCounts = listNotDoneThingsOrderByCreateTimeDesc().size();

        //依次添加顺序 未做,新增,完成
        arrayList.add(new ChartDataDTO(new Date(), toBeDoneThingsCounts));
        arrayList.add(new ChartDataDTO(new Date(), todayNewThingsCounts));
        arrayList.add(new ChartDataDTO(new Date(), todayFinshThingsCounts));
        return arrayList;
    }

}
