package com.wqzhang.thingswapper.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.wqzhang.thingswapper.R;
import com.wqzhang.thingswapper.model.ChartDataModel;

import java.util.ArrayList;

/**
 * Created by wqzhang on 17-1-16.
 */

public class ChartsRecyclerAdapter extends RecyclerView.Adapter {
    private final int TYPE_TODAY_TITTLE = 0;
    private final int TYPE_TODAY_PIECHART = 1;
    private final int TYPE_WEEK_TITTLE = 2;
    private final int TYPE_WEEK_NEW_LINECHART = 3;
    private final int TYPE_WEEK_FINSH_LINECHART = 4;
    private final int TYPE_WEEK_RESULTS = 5;

    LayoutInflater inflater;
    Context context;


    private ArrayList<ChartDataModel> toDayResultData;

    private ChartsRecyclerAdapter() {
    }

    public ChartsRecyclerAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_TODAY_TITTLE) {
            TextView textView = (TextView) inflater.inflate(R.layout.list_item_pool_show_tittle_textview, parent, false);
            textView.setText("今天记录");
            return new DefaultHolder(textView);
        } else if (viewType == TYPE_TODAY_PIECHART) {
            LinearLayout todayLinearLayout = (LinearLayout) inflater.inflate(R.layout.list_item_piechart, parent, false);
            PiechartHolder piechartHolder = new PiechartHolder(todayLinearLayout);
            return piechartHolder;
        } else if (viewType == TYPE_WEEK_TITTLE) {
            TextView textView = (TextView) inflater.inflate(R.layout.list_item_pool_show_tittle_textview, parent, false);
            textView.setText("近七日汇总");
            return new DefaultHolder(textView);
        } else if (viewType == TYPE_WEEK_NEW_LINECHART) {
            LinearLayout todayLinearLayout = (LinearLayout) inflater.inflate(R.layout.list_item_linechart, parent, false);
            return new LinechartHolder(todayLinearLayout);
        } else if (viewType == TYPE_WEEK_FINSH_LINECHART) {
            LinearLayout todayLinearLayout = (LinearLayout) inflater.inflate(R.layout.list_item_linechart, parent, false);
            return new LinechartHolder(todayLinearLayout);
        } else if (viewType == TYPE_WEEK_RESULTS) {
            LinearLayout todayLinearLayout = (LinearLayout) inflater.inflate(R.layout.list_item_linechart, parent, false);
            return new LinechartHolder(todayLinearLayout);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_TODAY_TITTLE:
                break;
            case TYPE_TODAY_PIECHART:
                PiechartHolder piechartHolder = (PiechartHolder) holder;
                if (toDayResultData != null) {
                    piechartHolder.setData(toDayResultData);
                }
                break;
            case TYPE_WEEK_TITTLE:
                break;
            case TYPE_WEEK_NEW_LINECHART:
                break;
            case TYPE_WEEK_FINSH_LINECHART:
                break;
            case TYPE_WEEK_RESULTS:
                break;
            default:
                break;
        }

    }

    @Override
    public int getItemCount() {
        return 6;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public ArrayList<ChartDataModel> getToDayResultData() {
        return toDayResultData;
    }

    public void setToDayResultData(ArrayList<ChartDataModel> toDayResultData) {
        this.toDayResultData = toDayResultData;
        notifyItemChanged(TYPE_TODAY_PIECHART);
    }

    private class PiechartHolder extends RecyclerView.ViewHolder {

        PieChart pieChart;

        public PiechartHolder(View itemView) {
            super(itemView);
            pieChart = (PieChart) itemView.findViewById(R.id.chart);
        }

        public void setData(ArrayList<ChartDataModel> chartDataModelSparseArray) {
            ArrayList<Entry> values = new ArrayList<>();
            for (int i = 0; i < chartDataModelSparseArray.size(); i++) {
                values.add(new Entry(i, (float) chartDataModelSparseArray.get(i).getCount()));
            }
            ArrayList<PieEntry> entries = new ArrayList<>();
            entries.add(new PieEntry(chartDataModelSparseArray.get(0).getCount(), "未做"));
            entries.add(new PieEntry(chartDataModelSparseArray.get(1).getCount(), "新增"));
            entries.add(new PieEntry(chartDataModelSparseArray.get(2).getCount(), "完成"));

            pieChart.setUsePercentValues(true);
            pieChart.getDescription().setEnabled(false);
            pieChart.setExtraOffsets(5, 10, 5, 5);

            pieChart.setDragDecelerationFrictionCoef(0.95f);

            pieChart.setCenterText("今日事项统计\n共计24件事项");

            pieChart.setDrawHoleEnabled(true);
            pieChart.setHoleColor(Color.WHITE);

            pieChart.setTransparentCircleColor(Color.WHITE);
            pieChart.setTransparentCircleAlpha(110);

            pieChart.setHoleRadius(58f);
            pieChart.setTransparentCircleRadius(61f);

            pieChart.setDrawCenterText(true);

            pieChart.setRotationAngle(0);
            // enable rotation of the chart by touch
            pieChart.setRotationEnabled(true);
            pieChart.setHighlightPerTapEnabled(true);

            // pieChart.setUnit(" €");
            // pieChart.setDrawUnitsInChart(true);

            // add a selection listener
            pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, Highlight h) {
//                    Log.d(TAG, "onValueSelected 选择了" + ((PieEntry) e).getLabel());
                }

                @Override
                public void onNothingSelected() {
//                    Log.d(TAG, "onNothingSelected");
                }
            });
            //生成数据


            PieDataSet dataSet = new PieDataSet(entries, "Today Result Charts");

            ArrayList<Integer> colors = new ArrayList<Integer>();
            for (int c : ColorTemplate.COLORFUL_COLORS)
                colors.add(c);
            for (int c : ColorTemplate.JOYFUL_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.VORDIPLOM_COLORS)
                colors.add(c);


            for (int c : ColorTemplate.LIBERTY_COLORS)
                colors.add(c);

            for (int c : ColorTemplate.PASTEL_COLORS)
                colors.add(c);

            colors.add(ColorTemplate.getHoloBlue());

            dataSet.setColors(colors);

            dataSet.setValueLinePart1OffsetPercentage(80.f);
            dataSet.setValueLinePart1Length(0.2f);
            dataSet.setValueLinePart2Length(0.4f);
            dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);


            PieData data = new PieData(dataSet);

            data.setValueFormatter(new PercentFormatter());
            data.setValueTextSize(11f);
            data.setValueTextColor(Color.BLACK);
            pieChart.setData(data);

            pieChart.animateY(600, Easing.EasingOption.EaseInOutQuad);

        }
    }

    private class LinechartHolder extends RecyclerView.ViewHolder {

        LineChart lineChart;

        public LinechartHolder(View itemView) {
            super(itemView);
            lineChart = (LineChart) itemView.findViewById(R.id.chart);
        }

        public void setData(SparseArray<ChartDataModel> chartDataModelSparseArray) {
            ArrayList<Entry> values = new ArrayList<>();
            for (int i = 0; i < chartDataModelSparseArray.size(); i++) {
                values.add(new Entry(i, (float) chartDataModelSparseArray.get(i).getCount()));
            }

            LineDataSet lineDataSet = new LineDataSet(values, "今日");
            lineDataSet.enableDashedLine(10f, 5f, 0f);
            lineDataSet.enableDashedHighlightLine(10f, 5f, 0f);
            lineDataSet.setColor(Color.WHITE);
            lineDataSet.setCircleColor(Color.WHITE);
            lineDataSet.setLineWidth(1f);
            lineDataSet.setCircleRadius(2f);
            lineDataSet.setDrawCircleHole(false);
            lineDataSet.setValueTextSize(9f);
            lineDataSet.setDrawFilled(true);
            lineDataSet.setFormLineWidth(1f);
            lineDataSet.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            lineDataSet.setFormSize(15.f);

            lineDataSet.setHighLightColor(Color.RED);//设置点击高亮线   横竖
            lineDataSet.setHighlightEnabled(false);//取消显示

            lineDataSet.setFillColor(Color.GREEN);

            //不添加外宽框
            lineChart.setDrawBorders(false);
            lineChart.setNoDataText("暂无数据展示");
            lineChart.setDrawGridBackground(false); // 是否显示表格颜色
            lineChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度
            lineChart.setDoubleTapToZoomEnabled(false);//禁止双击
            lineChart.getAxisLeft().setEnabled(false);//隐藏左侧坐标轴


            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(lineDataSet); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(dataSets);


            XAxis xAxis = lineChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);


            lineChart.getDescription().setEnabled(false);
            // set data
            lineChart.setData(data);
            lineChart.animateX(600);
        }

    }

    private class DefaultHolder extends RecyclerView.ViewHolder {
        public DefaultHolder(View itemView) {
            super(itemView);
        }
    }
}