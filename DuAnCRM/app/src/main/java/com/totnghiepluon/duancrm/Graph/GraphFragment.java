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
import com.totnghiepluon.duancrm.Models.CustomerInfo;
import com.totnghiepluon.duancrm.data.DatabaseHelper;
import com.totnghiepluon.duancrm.data.GetDataFromPreference;
import com.totnghiepluon.duancrm.R;
import com.totnghiepluon.duancrm.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GraphFragment extends BaseFragment {
    private List<CustomerInfo> leadList;
    private List<CustomerInfo> customerList;
    private DatabaseHelper db;
    private BarChart barChart;
    private PieChart pieChart;
    private Description description;
    private String mChartName;
    private XAxis x1;
    private YAxis y1;
    private int sum;
    private Random random;
    private int[] colors = {
            Color.rgb(192, 255, 140), Color.rgb(255, 247, 140), Color.rgb(255, 208, 140),
            Color.rgb(140, 234, 255), Color.rgb(255, 140, 157), Color.rgb(255, 208, 140)
    };
    private GetDataFromPreference getData;
    private int[] number;
    private int length = 1;

    public static GraphFragment createInstance() {

        Bundle args = new Bundle();

        GraphFragment fragment = new GraphFragment();
        fragment.setArguments(args);
        return fragment;
    }

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
        db = new DatabaseHelper(getActivity());
        pieChart = container.findViewById(R.id.pieChart);
        pieChart.setUsePercentValues(true);
        barChart = container.findViewById(R.id.barChart);
        random = new Random();
        mChartName = "Tỉ lệ giữa các khách hàng";
        description = new Description();
        leadList = db.getAllLeads();
        customerList = db.getAllCustomers();
        x1 = barChart.getXAxis();
        y1 = barChart.getAxisLeft();
        number = new int[5];
    }

    private void drawChart() {
        getData = new GetDataFromPreference(getContext());
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        description.setText("Số lượng các khách hàng");
        barChart.setDescription(description);
        number[0] = leadList.size();
        for (int i = 0; i < customerList.size(); i++) {
            number[customerList.get(i).getmPriority()]++;
        }
        sum = number[0];
        int maxvalue = number[0];
        for (int i = 1; i < 5; i++) {
            if (number[i] > maxvalue) {
                maxvalue = number[i];
            }
            sum += number[i];
        }
        barChart.setMaxVisibleValueCount(maxvalue);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(true);
        x1.setGranularity(1f);
        x1.setCenterAxisLabels(true);
        y1.setDrawGridLines(true);
        y1.setSpaceTop(10f);
        barChart.getAxisRight().setEnabled(false);
        setDataToChart();
    }

    private void setDataToChart() {
        float groupSpace = 0.04f;
        float barSpace = 0.02f;
        float barWidth = 0.8f;
        ArrayList<BarEntry> yVal1 = new ArrayList<>();
        ArrayList<BarEntry> yVal2 = new ArrayList<>();
        ArrayList<BarEntry> yVal3 = new ArrayList<>();
        ArrayList<BarEntry> yVal4 = new ArrayList<>();
        ArrayList<BarEntry> yVal5 = new ArrayList<>();
        yVal1.add(new BarEntry(0, number[0]));
        yVal2.add(new BarEntry(1, number[1]));
        yVal3.add(new BarEntry(2, number[2]));
        yVal4.add(new BarEntry(3, number[3]));
        yVal5.add(new BarEntry(4, number[4]));
        BarDataSet[] set = new BarDataSet[5];
        if (barChart.getData() != null && barChart.getData().getDataSetCount() > 0) {
            for (int i = 0; i < 5; i++) {
                set[i] = (BarDataSet) barChart.getData().getDataSetByIndex(i);
            }
            set[0].setValues(yVal1);
            set[1].setValues(yVal2);
            set[2].setValues(yVal3);
            set[3].setValues(yVal4);
            set[4].setValues(yVal5);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            set[0] = new BarDataSet(yVal1, Constants.CUSTOMER_LABEL1[0]);
            set[1] = new BarDataSet(yVal2,  Constants.CUSTOMER_LABEL1[1]);
            set[2] = new BarDataSet(yVal3,  Constants.CUSTOMER_LABEL1[2]);
            set[3] = new BarDataSet(yVal4,  Constants.CUSTOMER_LABEL1[3]);
            set[4] = new BarDataSet(yVal5,  Constants.CUSTOMER_LABEL1[4]);
            set[0].setColor(colors[0]);
            set[1].setColor(colors[1]);
            set[2].setColor(colors[2]);
            set[3].setColor(colors[3]);
            set[4].setColor(colors[4]);
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set[0]);
            dataSets.add(set[1]);
            dataSets.add(set[2]);
            dataSets.add(set[3]);
            dataSets.add(set[4]);
            BarData data = new BarData((dataSets));
            barChart.setData(data);
        }
        barChart.getBarData().setBarWidth(barWidth);
        barChart.groupBars(0, groupSpace, barSpace);
        barChart.invalidate();
    }

    private void drawPieChart() {
        ArrayList<PieEntry> yvalues = new ArrayList<PieEntry>();
        if (sum == 0) {
            sum = 1;
        }
        for (int i = 0; i < 5; i++) {
            if (number[i] == 0) {
                break;
            }
            yvalues.add(new PieEntry(((float) number[i] * 100 / sum), Constants.CUSTOMER_LABEL[i], i));
        }
        PieDataSet dataSet = new PieDataSet(yvalues, "");
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
