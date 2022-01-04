package com.example.timemangertrans.ui.ShowWei;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.timemangertrans.Action.Kv;
import com.example.timemangertrans.Action.RecordChange;
import com.example.timemangertrans.Entienty.Recordmine;
import com.example.timemangertrans.Entienty.Rtype;
import com.example.timemangertrans.R;
import com.example.timemangertrans.Service.DBService_Record;
import com.example.timemangertrans.databinding.FragmentShowweiBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShowWeiFragment extends Fragment {
    private ShowWeiViewModel showweiViewModel;
    private FragmentShowweiBinding binding;

    int changetype;
    int backchanged = 0;//下一层是否修改
    int minechange = 0;//我处是否更改
    private TextView tv_datedisplay_start;
    private Button bt_settingdate_start;
    private TextView tv_timedisplay_start;
    private Button bt_settingtime_start;
    private TextView tv_datedisplay_end;
    private Button bt_settingdate_end;
    private TextView tv_timedisplay_end;
    private Button bt_settingtime_end;

    private ArrayAdapter<String> adapter;

    Button button_affirm;
    Date startdate;
    Date enddate;
    String starttime_date;
    String starttime_time;
    String endtime_time;
    String endtime_date;
    String starttime;
    String endtime;
    private List<Recordmine> recordlist = new ArrayList<Recordmine>();
    private AlertDialog.Builder builder;
    private int year;
    private int month;
    private int dayOfMonth;
    private int mHour;
    private int mMinute;
    DBService_Record dbs = new DBService_Record();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        showweiViewModel =
                new ViewModelProvider(this).get(ShowWeiViewModel.class);

        binding = FragmentShowweiBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        //final TextView textView = binding.textNotifications;

        List<Kv> td = new ArrayList<Kv>();



        bt_settingdate_start = binding.wBtSettingdateStart;
        bt_settingtime_start = binding.wBtSettingtimeStart;
        tv_datedisplay_start = binding.wTvDatedisplayStart;
        tv_timedisplay_start = binding.wTvTimedisplayStart;

        bt_settingdate_end = binding.wBtSettingdateEnd;
        bt_settingtime_end = binding.wBtSettingtimeEnd;
        tv_datedisplay_end = binding.wTvDatedisplayEnd;
        tv_timedisplay_end = binding.wTvTimedisplayEnd;

        button_affirm = binding.buttonWOk;
        recordlist = dbs.getCheckedRecordDataall();
        button_affirm.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if(starttime_date != null && starttime_date != null && endtime_date != null && endtime_time !=null){

                td.clear();
                starttime = starttime_date + ' ' + starttime_time;/*输入的开始时间*/
                endtime = endtime_date + ' ' + endtime_time;/*获得输入的结束时间*/
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date asdate = null;
                try {
                    startdate = sdf.parse(starttime);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    enddate = sdf.parse(endtime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                    if(enddate.compareTo(startdate)>=0){/*结束时间在开始时间之后*/


                /*****获得时间，两个date******/
                //long minutes = ChronoUnit.MINUTES.between(Instant.ofEpochMilli(edate.getTime()), Instant.ofEpochMilli( sdate .getTime()));
                long minutes = 0;

                for (Recordmine r : recordlist) {
                    int q;
                    int h;
                    q = startdate.compareTo(r.getRdate_end());//记录开始时间减查询开始时间，大于零说明记录开始时间在查询开始时间之后，即实体在参数之后，返回1。2.3.。。
                    h = r.getRdate_start().compareTo(enddate);//查询结束时间减记录结束时间，大于零说明查询结束时间
                    if (r.getRdate_start().compareTo(startdate) > 0 && enddate.compareTo(r.getRdate_end()) > 0) {
                        minutes = 0 - ChronoUnit.MINUTES.between(Instant.ofEpochMilli(r.getRdate_end().getTime()), Instant.ofEpochMilli(r.getRdate_start().getTime()));
                    } else if (startdate.compareTo(r.getRdate_start()) > 0 && r.getRdate_end().compareTo(startdate) > 0) {/*前骑墙*/
                        minutes = 0 - ChronoUnit.MINUTES.between(Instant.ofEpochMilli(r.getRdate_end().getTime()), Instant.ofEpochMilli(startdate.getTime()));

                    } else if (enddate.compareTo(r.getRdate_start()) > 0 && r.getRdate_end().compareTo(enddate) > 0) {/*后骑墙*/
                        minutes = 0 - ChronoUnit.MINUTES.between(Instant.ofEpochMilli(enddate.getTime()), Instant.ofEpochMilli(r.getRdate_start().getTime()));
                    }
                    Kv kvuse = new Kv((int) minutes, "↙"+r.getRname());

                    if (minutes > 0)
                        td.add(kvuse);
                }
                        List<Kv> kl = new ArrayList<Kv>();
                        for(Kv t : td){//遍历合格队列
                            int flag = 0;
                            for(Kv k : kl){//遍历要插入队列
                                if(t.getName().equals(k.getName())){//如果合格元素与插入队列中的某个元素重名
                                    k.setLastTime(k.getLastTime()+t.getLastTime());//将插入队列中的那个元素的时间相加
                                    flag = 1;
                                    break;
                                }
                            }
                            if(flag == 0){
                                kl.add(t);
                            }
                        }


                        drawPtc(kl);


                    }
                     else{
                        Toast.makeText(getContext(),"请设置正确的时间",Toast.LENGTH_SHORT).show();
                    }

            }else {
                    Toast.makeText(getContext(),"请设置完整的时间",Toast.LENGTH_SHORT).show();
                }

            }
        });












        bt_settingdate_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate_start();
            }
        });
        bt_settingtime_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTime_start();
            }
        });
        bt_settingdate_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate_end();
            }
        });
        bt_settingtime_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTime_end();
            }
        });




        return root;
    }
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
        barChart = binding.RBarChart;
//        entries.add(new BarEntry(0, 0));
        int cnt = 0;
        for(Kv kv : kvList) {

            entries.add(new BarEntry(cnt, kv.getLastTime()));//添加时间数
            cnt++;
        }

        XAxis xAxis = barChart.getXAxis();
        //是否显示X轴的线(与X轴垂直的线),默认为true



        dataset = new BarDataSet(entries, "结果如此");
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
       //     System.out.println(kv.getName()+"===================添加的时间++++++++++++"+kv.getLastTime());
            labels.add(kv.getName());
        }
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));


        ///////////////////////////////////////////
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(cnt);
        xAxis.setTextSize(10);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /******************究极分隔符******************时间显示控件，代码不要动***********************************究极分隔符*******/
    public void getDate_start() {
        builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.date_dialog, null);
        final DatePicker datePicker = (DatePicker) view
                .findViewById(R.id.date_picker);
        // 设置日期简略显示 否则详细显示 包括:星期周
        datePicker.setCalendarViewShown(false);
        // 设置date布局
        builder.setView(view);
        builder.setTitle("设置日期信息");
        builder.setPositiveButton("确  定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        year = datePicker.getYear();
                        month = datePicker.getMonth() + 1;
                        dayOfMonth = datePicker.getDayOfMonth();
                        starttime_date = new StringBuffer()
                                .append(year)
                                .append("-")
                                .append(month < 10 ? "0" + month : month)
                                .append("-")
                                .append(dayOfMonth < 10 ? "0" + dayOfMonth
                                        : dayOfMonth).toString();
                        tv_datedisplay_start.setText(starttime_date);

                        dialog.cancel();
                    }
                });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    public void getTime_start() {

        builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.time_dialog, null);
        final TimePicker timePicker = (TimePicker) view
                .findViewById(R.id.time_picker);
        timePicker.setIs24HourView(true);
        // 设置time布局
        builder.setView(view);
        builder.setTitle("设置时间信息");
        builder.setPositiveButton("确  定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mHour = timePicker.getCurrentHour();
                        mMinute = timePicker.getCurrentMinute();
                        // 时间小于10的数字 前面补0 如01:12:00
                        starttime_time = new StringBuffer()
                                .append(mHour < 10 ? "0" + mHour
                                        : mHour)
                                .append(":")
                                .append(mMinute < 10 ? "0" + mMinute
                                        : mMinute).toString();
                        tv_timedisplay_start
                                .setText(starttime_time);
                        dialog.cancel();
                    }

                });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();

    }

    public void getDate_end() {
        builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.date_dialog, null);
        final DatePicker datePicker = (DatePicker) view
                .findViewById(R.id.date_picker);
        // 设置日期简略显示 否则详细显示 包括:星期周
        datePicker.setCalendarViewShown(false);
        // 设置date布局
        builder.setView(view);
        builder.setTitle("设置日期信息");
        builder.setPositiveButton("确  定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        year = datePicker.getYear();
                        month = datePicker.getMonth() + 1;
                        dayOfMonth = datePicker.getDayOfMonth();
                        endtime_date = new StringBuffer()
                                .append(year)
                                .append("-")
                                .append(month < 10 ? "0" + month : month)
                                .append("-")
                                .append(dayOfMonth < 10 ? "0" + dayOfMonth
                                        : dayOfMonth).toString();
                        tv_datedisplay_end.setText(starttime_date);

                        dialog.cancel();
                    }
                });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    public void getTime_end() {

        builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.time_dialog, null);
        final TimePicker timePicker = (TimePicker) view
                .findViewById(R.id.time_picker);
        timePicker.setIs24HourView(true);
        // 设置time布局
        builder.setView(view);
        builder.setTitle("设置时间信息");
        builder.setPositiveButton("确  定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mHour = timePicker.getCurrentHour();
                        mMinute = timePicker.getCurrentMinute();
                        // 时间小于10的数字 前面补0 如01:12:00
                        endtime_time = new StringBuffer()
                                .append(mHour < 10 ? "0" + mHour
                                        : mHour)
                                .append(":")
                                .append(mMinute < 10 ? "0" + mMinute
                                        : mMinute).toString();
                        tv_timedisplay_end
                                .setText(endtime_time);
                        dialog.cancel();
                    }

                });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();

    }
/******************究极分隔符******************时间显示控件，代码不要动***********************************究极分隔符*******/
}
