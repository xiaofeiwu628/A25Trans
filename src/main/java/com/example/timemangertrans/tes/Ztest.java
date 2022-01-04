package com.example.timemangertrans.tes;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.timemangertrans.Action.Kv;
import com.example.timemangertrans.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class Ztest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ztest);
        List<Kv> td = new ArrayList<Kv>();
        Kv t1 = new Kv(60,"t1");
        Kv t2 = new Kv(90,"t2");
        td.add(t1);
        td.add(t2);
        drawPtc(td);

    }
    //显示的图表
    public BarChart barChart;
    //保存数据的实体（下面定义了两组数据集合）
    public ArrayList<BarEntry> entries = new ArrayList<>();
    public ArrayList<BarEntry> entries1 = new ArrayList<>();
    //数据的集合（每组数据都需要一个数据集合存放数据实体和该组的样式）
    public BarDataSet dataset;
    public BarDataSet dataset1;
    //表格下方的文字
    public ArrayList<String> labels = new ArrayList<String>();

    public void drawPtc(List<Kv> kvList) {
        barChart = findViewById(R.id.R_bar_chart);
//        entries.add(new BarEntry(0, 0));
        int cnt = 0;
        for(Kv kv : kvList) {
            entries.add(new BarEntry(cnt, kv.getLastTime()));
            cnt++;
        }

        XAxis xAxis = barChart.getXAxis();
        //是否显示X轴的线(与X轴垂直的线),默认为true



        dataset = new BarDataSet(entries, "第一组数据");
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataset);
        dataSets.add(dataset1);

        BarData data = new BarData(dataset);
        barChart.setData(data);



        //设置单个点击事件
/*        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, Highlight highlight) {
                Toast.makeText(RecordActivity.this,"  ",Toast.LENGTH_LONG).show();
            }


            @Override
            public void onNothingSelected() {

            }
        });*/
        //设置显示动画效果
        barChart.animateY(2000);
        //设置图标右下放显示文字
//        barChart.setDescription("MPandroidChart Test");
        xAxis.setDrawLabels(true);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLabels(true);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labels.size());
        xAxis.setCenterAxisLabels(true);//设置标签居中
        for(Kv kv : kvList) {
            labels.add(kv.getName());
        }
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        ///////////////////////////////////////////
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(cnt);
        xAxis.setTextSize(10);
    }


}