package com.totnghiepluon.duancrm.Graph;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.totnghiepluon.duancrm.Base.BaseFragment;
import com.totnghiepluon.duancrm.GetDataFromPreference;
import com.totnghiepluon.duancrm.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GraphFragment extends BaseFragment {

    public static GraphFragment createInstance() {

        Bundle args = new Bundle();

        GraphFragment fragment = new GraphFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private BarChart barChart;
    private PieChart pieChart;
    private Description description;
    private String mChartName;
    private XAxis x1;
    private YAxis y1;
    private Random random;
    public int[] colors = {
            Color.rgb(192, 255, 140), Color.rgb(255, 247, 140), Color.rgb(255, 208, 140),
            Color.rgb(140, 234, 255), Color.rgb(255, 140, 157), Color.rgb(255, 208, 140)
    };
    private GetDataFromPreference getData;
    private List<Integer> listMonth;
    private int length;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_graph;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState, View rootView) {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_graph, container, false);
        init(view);
        drawChart();
        drawPieChart();
        printDataFromAPI();
        return view;
    }

    private void printDataFromAPI() {
    }

    private void init(View container) {
        pieChart = container.findViewById(R.id.pieChart);
        pieChart.setUsePercentValues(true);
        barChart = container.findViewById(R.id.barChart);
        random = new Random();
        mChartName = "pieChart";
        description = new Description();
        listMonth = new ArrayList<>();
        x1 = barChart.getXAxis();
        y1 = barChart.getAxisLeft();

    }

    private void drawChart() {
        getData = new GetDataFromPreference(getContext());
        createData();
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        description.setText("Huy's chart");
        barChart.setDescription(description);
        barChart.setMaxVisibleValueCount(100);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(true);
        x1.setGranularity(1f);
        x1.setCenterAxisLabels(true);
        y1.setDrawGridLines(true);
        y1.setSpaceTop(10f);
        barChart.getAxisRight().setEnabled(false);
        setDataToChart();
    }

    private void createData() {
        length = random.nextInt(15) + 5;
        for (int i = 0; i < length; i++) {
            listMonth.add(random.nextInt(200));
        }
        getData.doSave(listMonth, "a");
    }

    private void setDataToChart() {
        float groupSpace = 0.04f;
        float barSpace = 0.02f;
        float barWidth = 0.46f;
        List<Integer> testList = getData.loadGameSetting("a");
        ArrayList<BarEntry> yVal1 = new ArrayList<>();
        ArrayList<BarEntry> yVal2 = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            yVal1.add(new BarEntry(i, testList.get(i)));
//            yVal2.add(new BarEntry(i, 0.3f));
        }
        BarDataSet set1, set2;
        if (barChart.getData() != null && barChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) barChart.getData().getDataSetByIndex(0);
            set2 = (BarDataSet) barChart.getData().getDataSetByIndex(1);
            set1.setValues(yVal1);
            set2.setValues(yVal2);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVal1, "first");
            set1.setColor(getResources().getColor(R.color.shortchart));
            set2 = new BarDataSet(yVal2, "second");
            set2.setColor(getResources().getColor(R.color.highchart));
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            dataSets.add(set2);
            BarData data = new BarData((dataSets));
            barChart.setData(data);
        }
        barChart.getBarData().setBarWidth(barWidth);
        barChart.groupBars(0, groupSpace, barSpace);
        barChart.invalidate();
    }

    private void drawPieChart() {
        ArrayList<PieEntry> yvalues = new ArrayList<PieEntry>();
        yvalues.add(new PieEntry(8f, "January", 0));
        yvalues.add(new PieEntry(15f, "February", 1));
        yvalues.add(new PieEntry(12f, "March", 2));
        yvalues.add(new PieEntry(25f, "April", 3));
        yvalues.add(new PieEntry(23f, "May", 4));
        PieDataSet dataSet = new PieDataSet(yvalues, "Results");
        PieData data = new PieData(dataSet);

        data.setValueFormatter(new PercentFormatter());
        pieChart.setData(data);
        Description description = new Description();
        description.setText(mChartName);
        pieChart.setDescription(description);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(0f);
        pieChart.setHoleRadius(0);
        dataSet.setColors(colors);
        data.setValueTextSize(17f);
        data.setValueTextColor(getResources().getColor(R.color.black));
        pieChart.invalidate();
    }
}
