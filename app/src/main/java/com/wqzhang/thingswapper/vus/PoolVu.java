package com.wqzhang.thingswapper.vus;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.adapters.ChartsRecyclerAdapter;
import com.wqzhang.thingswapper.model.ChartDataModel;

import java.util.ArrayList;

/**
 * Created by wqzhang on 17-1-13.
 */

public class PoolVu implements Vu {
    private final String TAG = "PoolVu";
    View view;
    RecyclerView recyclerView;


    @Override
    public void init(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.pool_show_main_layout, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.charts_recyclerview);

    }

    @Override
    public View getView() {
        return view;
    }

    public void setToDayResultData(ArrayList<ChartDataModel> toDayResultData) {
        ((ChartsRecyclerAdapter) recyclerView.getAdapter()).setToDayResultData(toDayResultData);
    }
//
//    public void setTodayPieChartDate() {
//        todayPieChart.setUsePercentValues(true);
//        todayPieChart.getDescription().setEnabled(false);
//        todayPieChart.setExtraOffsets(5, 10, 5, 5);
//
//        todayPieChart.setDragDecelerationFrictionCoef(0.95f);
//
//        todayPieChart.setCenterText("今日事项统计\n共计24件事项");
//
//        todayPieChart.setDrawHoleEnabled(true);
//        todayPieChart.setHoleColor(Color.WHITE);
//
//        todayPieChart.setTransparentCircleColor(Color.WHITE);
//        todayPieChart.setTransparentCircleAlpha(110);
//
//        todayPieChart.setHoleRadius(58f);
//        todayPieChart.setTransparentCircleRadius(61f);
//
//        todayPieChart.setDrawCenterText(true);
//
//        todayPieChart.setRotationAngle(0);
//        // enable rotation of the chart by touch
//        todayPieChart.setRotationEnabled(true);
//        todayPieChart.setHighlightPerTapEnabled(true);
//
//        // todayPieChart.setUnit(" €");
//        // todayPieChart.setDrawUnitsInChart(true);
//
//        // add a selection listener
//        todayPieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
//            @Override
//            public void onValueSelected(Entry e, Highlight h) {
//                Log.d(TAG, "onValueSelected 选择了" + ((PieEntry) e).getLabel());
//            }
//
//            @Override
//            public void onNothingSelected() {
//                Log.d(TAG, "onNothingSelected");
//            }
//        });
//        //生成数据
//
//        ArrayList<PieEntry> entries = new ArrayList<>();
//        entries.add(new PieEntry(5, "未做"));
//        entries.add(new PieEntry(50, "新增"));
//        entries.add(new PieEntry(1, "完成"));
//
//
//        PieDataSet dataSet = new PieDataSet(entries, "Today Result Charts");
//
//        ArrayList<Integer> colors = new ArrayList<Integer>();
//        for (int c : ColorTemplate.COLORFUL_COLORS)
//            colors.add(c);
//        for (int c : ColorTemplate.JOYFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.VORDIPLOM_COLORS)
//            colors.add(c);
//
//
//        for (int c : ColorTemplate.LIBERTY_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.PASTEL_COLORS)
//            colors.add(c);
//
//        colors.add(ColorTemplate.getHoloBlue());
//
//        dataSet.setColors(colors);
//
//        dataSet.setValueLinePart1OffsetPercentage(80.f);
//        dataSet.setValueLinePart1Length(0.2f);
//        dataSet.setValueLinePart2Length(0.4f);
//        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
//
//
//        PieData data = new PieData(dataSet);
//
//        data.setValueFormatter(new PercentFormatter());
//        data.setValueTextSize(11f);
//        data.setValueTextColor(Color.BLACK);
//        todayPieChart.setData(data);
//
//        todayPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
//
//    }
//
//    public void setTodayLineChartDate() {
//        ArrayList<Entry> values = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            Random random = new Random();
//            values.add(new Entry(i, i * random.nextInt(3)));
//        }
//
//
//        LineDataSet lineDataSet = new LineDataSet(values, "今日");
//        lineDataSet.enableDashedLine(10f, 5f, 0f);
//        lineDataSet.enableDashedHighlightLine(10f, 5f, 0f);
//        lineDataSet.setColor(Color.WHITE);
//        lineDataSet.setCircleColor(Color.WHITE);
////        lineDataSet.setDrawCircles(false);
//        lineDataSet.setLineWidth(1f);
//        lineDataSet.setCircleRadius(2f);
//        lineDataSet.setDrawCircleHole(false);
//        lineDataSet.setValueTextSize(9f);
//        lineDataSet.setDrawFilled(true);
//        lineDataSet.setFormLineWidth(1f);
//        lineDataSet.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
//        lineDataSet.setFormSize(15.f);
//
//        lineDataSet.setHighLightColor(Color.RED);//设置点击高亮线   横竖
//        lineDataSet.setHighlightEnabled(false);//取消显示
//
////        if (Utils.getSDKInt() >= 18) {
////            // fill drawable only supported on api level 18 and above
////            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
////            set1.setFillDrawable(drawable);
////        }
////        else {
//        lineDataSet.setFillColor(Color.GREEN);
//
//        //不添加外宽框
//        today_linechart.setDrawBorders(false);
//        today_linechart.setNoDataText("暂无数据展示");
//        today_linechart.setDrawGridBackground(false); // 是否显示表格颜色
//        today_linechart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度
//        today_linechart.setDoubleTapToZoomEnabled(false);//禁止双击
//        today_linechart.getAxisLeft().setEnabled(false);//隐藏左侧坐标轴
//
////        YAxis leftAxis = today_linechart.getAxisLeft();
////        leftAxis.setAxisMaximum(20f);
////        leftAxis.setAxisMinimum(0f);
////        leftAxis.setYOffset(20f);
////        leftAxis.setDrawZeroLine(false);
//
//        // limit lines are drawn behind data (and not on top)
////        leftAxis.setDrawLimitLinesBehindData(true);
//
////        YAxis rightAxis = today_linechart.getAxisRight();
////        rightAxis.setDrawAxisLine(false);
////        rightAxis.setDrawZeroLine(false);
////        rightAxis.setDrawLimitLinesBehindData(false);
//
//        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
//        dataSets.add(lineDataSet); // add the datasets
//
//        // create a data object with the datasets
//        LineData data = new LineData(dataSets);
//
//
//        XAxis xAxis = today_linechart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setDrawGridLines(false);
//
////        xAxis.setDrawAxisLine(false);
////        xAxis.setDrawAxisLine(true);
//
//        today_linechart.getDescription().setEnabled(false);
//        // set data
//        today_linechart.setData(data);
//
//        today_linechart.animateX(200);
//
//    }


    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

}
