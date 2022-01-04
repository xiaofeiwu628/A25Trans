package com.example.timemangertrans.Action;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.timemangertrans.Entienty.Recordmine;
import com.example.timemangertrans.R;
import com.example.timemangertrans.Service.DBService_Record;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

public class RecordShow extends AppCompatActivity {
    int minechange = 0;
    int backchanged = 0;
    /*********时间控件变量*******/
    private TextView tv_datedisplay_start;
    private Button bt_settingdate_start;
    private TextView tv_timedisplay_start;
    private Button bt_settingtime_start;
    private TextView tv_datedisplay_end;
    private Button bt_settingdate_end;
    private TextView tv_timedisplay_end;
    private Button bt_settingtime_end;
    private Button bt_delete;
    Date sdate_plan = null;
    private AlertDialog.Builder builder;
    private int year_get;
    private int month_get;
    private int dayOfMonth_get;
    private int mHour_get;
    private int mMinute_get;
    private int dele;
    /*以下四个分别为控件获得的时间*/
    String starttime_date_get;
    String starttime_time_get;
    String endtime_time_get;
    String endtime_date_get;
    long minutes;
    /*********时间控件变量*******/
    String starttime_up;
    String endtime_up;
    Date startdate_up;
    Date enddate_up;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
       protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_show);
        TextView showname = findViewById(R.id.textView_record_show_showname);
        TextView showtype = findViewById(R.id.textView_record_show_showtype);
        DBService_Record dbs = new DBService_Record();
        TextView showstarttime_plan = findViewById(R.id.textView_record_show_showstarttime_plan);
        TextView showendtime_plan = findViewById(R.id.textView_record_show_showendtime_plan);
        TextView showdurationtime_plan = findViewById(R.id.textView_record_show_showedurationtime_plan);
        dele = 0;
        LinearLayout linear_checkedy = findViewById(R.id.linear_recordchangeshow_checkedy);
        LinearLayout linear_set = findViewById(R.id.linear_recordchangeshow_settime);
        TextView showstarttime = findViewById(R.id.textView_record_show_showstarttime);
        TextView showendtime = findViewById(R.id.textView_record_show_showendtime);
        TextView showdurationtime = findViewById(R.id.textView_record_show_showedurationtime);
        //Button button_change = findViewById(R.id.button_record_show_change);

        bt_delete = findViewById(R.id.button_recordchangeshow_delete);

        Intent intent =  getIntent();
        String name = intent.getStringExtra("name");
        String type = intent.getStringExtra("type");
        String starttime_plan = intent.getStringExtra("starttime_plan");
        String endtime_plan = intent.getStringExtra("endtime_plan");
        String starttime = intent.getStringExtra("starttime");
        String endtime = intent.getStringExtra("endtime");
        String checked = intent.getStringExtra("checked");
        int id = intent.getIntExtra("id",0);


        Button button_affirm = findViewById(R.id.button_record_show_change);
        button_affirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dele == 0){
                    if (starttime_date_get != null && starttime_time_get != null && endtime_date_get != null && endtime_time_get != null) {
                        Recordmine newrec = new Recordmine();

                        starttime_up = starttime_date_get + ' ' + starttime_time_get;
                        endtime_up = endtime_date_get + ' ' + endtime_time_get;

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        Date asdate_actual_up = null;
                        try {
                            asdate_actual_up = sdf.parse(starttime_up);
                            newrec.setRdate_start(asdate_actual_up);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Date aedate_actual_up = null;
                        try {
                            aedate_actual_up = sdf.parse(endtime_up);
                            newrec.setRdate_end(aedate_actual_up);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (newrec.getRdate_end().compareTo(newrec.getRdate_start()) >= 0) {/*结束时间在开始时间之后*/
                            /***********/

                            long minutes_actual = ChronoUnit.MINUTES.between(Instant.ofEpochMilli(asdate_actual_up.getTime()), Instant.ofEpochMilli(aedate_actual_up.getTime()));
                            String durationtime_actual = "小于一分钟";
                /*System.out.println("实际开始时间为------------"+asdate_actual_up);
                System.out.println("实际结束时间为------------"+aedate_actual_up);
                System.out.println("实际持续时间为------------"+minutes_actual);*/
                            if (minutes_actual >= 1400) {
                                long ahours = minutes_actual / 60;
                                long time = minutes_actual % 60;
                                long day = ahours / 24;
                                long bhours = ahours % 24;
                                durationtime_actual = day + "天" + bhours + "小时" + time + "分钟";
                            } else if (minutes_actual > 60 && minutes_actual < 1440) {
                                long hours = minutes_actual / 60;
                                long time = minutes_actual % 60;
                                durationtime_actual = hours + "小时" + time + "分钟";
                            } else {
                                durationtime_actual = minutes_actual + "分钟";
                            }

                            String intimeornot;
                            String efficientornot;

                            if (newrec.getRdate_end().compareTo(sdate_plan) >= 0) {/*实际结束时间在预计之后*/
                                intimeornot = "拖延";
                            } else {
                                intimeornot = "及时";
                            }

                            if (minutes >= minutes_actual) {
                                efficientornot = "高效";
                            } else {
                                efficientornot = "低效";
                            }

                            String newstatus = intimeornot + efficientornot;
                            showstarttime.setText(starttime_up);
                            showendtime.setText(endtime_up);
                            showdurationtime.setText(durationtime_actual);
                            /***********************/
                            newrec.setId(id);
                            newrec.setRstatus(newstatus);

                            dbs.RecordUP(newrec);
                            Toast.makeText(RecordShow.this, "记录成功，请返回查看", Toast.LENGTH_SHORT).show();
                            minechange = 1;
                            linear_checkedy.setVisibility(View.VISIBLE);
                            linear_set.setVisibility(View.GONE);
                            minechange = 1;
                        } else {
                            Toast.makeText(RecordShow.this, "请设置正确的时间", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(RecordShow.this, "请设置完整的时间", Toast.LENGTH_SHORT).show();
                    }

            }else{
                    Toast.makeText(RecordShow.this,"该记录已删除，请勿操作",Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dele == 0){
                dbs.RecordDelete(id);
                dele++;
                Toast.makeText(RecordShow.this,"删除成功，请返回",Toast.LENGTH_SHORT).show();
                minechange = 1;
            }else{
                    Toast.makeText(RecordShow.this,"该记录已删除，请勿操作",Toast.LENGTH_SHORT).show();
                }

            }
        });
        /*********时间控件变量*******/
        bt_settingdate_start = (Button) findViewById(R.id.bt_settingdate_recordchangeshow_start);
        bt_settingtime_start = (Button) findViewById(R.id.bt_settingtime_recordchangeshow_start);
        tv_datedisplay_start = (TextView) findViewById(R.id.tv_date_recordchangeshow_start);
        tv_timedisplay_start = (TextView) findViewById(R.id.tv_time_recordchangeshow_start);

        bt_settingdate_end = (Button) findViewById(R.id.bt_settingdate_recordchangeshow_end);
        bt_settingtime_end = (Button) findViewById(R.id.bt_settingtime_recordchangeshow_end);
        tv_datedisplay_end = (TextView) findViewById(R.id.tv_date_recordchangeshow_end);
        tv_timedisplay_end = (TextView) findViewById(R.id.tv_time_recordchangeshow_end);

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
        /*********时间控件变量*******/




        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            if(starttime_plan!=null)
            sdate_plan= sdf.parse(starttime_plan);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date edate_plan = null;
        try {
            if(endtime_plan!=null)
            edate_plan = sdf.parse(endtime_plan);
        } catch (ParseException e) {
            e.printStackTrace();
        }
         minutes = ChronoUnit.MINUTES.between(Instant.ofEpochMilli(Objects.requireNonNull(sdate_plan).getTime()), Instant.ofEpochMilli( Objects.requireNonNull(edate_plan).getTime()));
        String durationtime_plan = "小于一分钟";
        if(minutes >=1400 ){
            long ahours = minutes / 60;
            long time = minutes%60;
            long day = ahours / 24;
            long bhours = ahours %24;
            durationtime_plan = day+"天"+bhours+"小时"+time+"分钟";
        }else if(minutes > 60 && minutes < 1440){
            long hours = minutes / 60;
            long time = minutes%60;
            durationtime_plan = hours+"小时"+time+"分钟";
        }else{
            durationtime_plan = minutes+"分钟";
        }

        showname.setText(name);
        showtype.setText(type);
        showstarttime_plan.setText(starttime_plan);
        showendtime_plan.setText(endtime_plan);
        showdurationtime_plan.setText(durationtime_plan);

        if(checked.equals("y")){/*已记录的显示设置*/
            Date sdate_actual = null;
            try {
                sdate_actual= sdf.parse(starttime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date edate_actual = null;
            try {
                edate_actual= sdf.parse(endtime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long minutes_actual = ChronoUnit.MINUTES.between(Instant.ofEpochMilli(sdate_actual.getTime()), Instant.ofEpochMilli( edate_actual.getTime()));
            String durationtime_actual = "小于一分钟";
            /*System.out.println("实际开始时间为------------"+sdate_actual);
            System.out.println("实际结束时间为------------"+edate_actual);
            System.out.println("实际持续时间为------------"+minutes_actual);*/
            if(minutes_actual >=1400 ){
                long ahours = minutes_actual / 60;
                long time = minutes_actual%60;
                long day = ahours / 24;
                long bhours = ahours %24;
                durationtime_actual = day+"天"+bhours+"小时"+time+"分钟";
            }else if(minutes_actual > 60 && minutes_actual < 1440){
                long hours = minutes_actual / 60;
                long time = minutes_actual%60;
                durationtime_actual = hours+"小时"+time+"分钟";
            }else{
                durationtime_actual = minutes_actual+"分钟";
            }
            showstarttime.setText(starttime);
            showendtime.setText(endtime);
            showdurationtime.setText(durationtime_actual);
            linear_checkedy.setVisibility(View.VISIBLE);
            linear_set.setVisibility(View.GONE);
        }

















        //button_change.setVisibility(View.GONE);
       /* button_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentc = new Intent(RecordShow.this,RecordChange.class);
                intentc.putExtra("name",name);
                intentc.putExtra("type",type);
                intentc.putExtra("starttime",starttime);
                intentc.putExtra("endtime",endtime);
                intentc.putExtra("change","up");
                startActivityForResult(intent,1);
                startActivity(intentc);
            }
        });*/
    }
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                backchanged = data.getIntExtra("backchange", 0);
                minechange = backchanged;
                System.out.println("showlook!!!+" + backchanged);
                break;
            default:
        }
    }
    @Override
    public void onBackPressed(){
        Intent backintent = new Intent();
        backintent.putExtra("backchange",minechange);
        setResult(RESULT_OK,backintent);
        finish();
    }

    /******************究极分隔符******************时间显示控件，代码不要动***********************************究极分隔符*******/
    public void getDate_start() {
        builder = new AlertDialog.Builder(RecordShow.this);
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
                        year_get = datePicker.getYear();
                        month_get = datePicker.getMonth() + 1;
                        dayOfMonth_get = datePicker.getDayOfMonth();
                        starttime_date_get = new StringBuffer()
                                .append(year_get)
                                .append("-")
                                .append(month_get < 10 ? "0" + month_get : month_get)
                                .append("-")
                                .append(dayOfMonth_get < 10 ? "0" + dayOfMonth_get
                                        : dayOfMonth_get).toString();
                        tv_datedisplay_start.setText(starttime_date_get);
                        //System.out.println(starttime_date);
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

        builder = new AlertDialog.Builder(RecordShow.this);
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
                        mHour_get = timePicker.getCurrentHour();
                        mMinute_get = timePicker.getCurrentMinute();
                        // 时间小于10的数字 前面补0 如01:12:00
                        starttime_time_get = new StringBuffer()
                                .append(mHour_get < 10 ? "0" + mHour_get
                                        : mHour_get)
                                .append(":")
                                .append(mMinute_get < 10 ? "0" + mMinute_get
                                        : mMinute_get).toString();
                        tv_timedisplay_start
                                .setText(starttime_time_get);
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
        builder = new AlertDialog.Builder(RecordShow.this);
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
                        year_get = datePicker.getYear();
                        month_get = datePicker.getMonth() + 1;
                        dayOfMonth_get = datePicker.getDayOfMonth();
                        endtime_date_get = new StringBuffer()
                                .append(year_get)
                                .append("-")
                                .append(month_get < 10 ? "0" + month_get : month_get)
                                .append("-")
                                .append(dayOfMonth_get < 10 ? "0" + dayOfMonth_get
                                        : dayOfMonth_get).toString();
                        tv_datedisplay_end.setText(endtime_date_get);
                        System.out.println(starttime_date_get);
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

        builder = new AlertDialog.Builder(RecordShow.this);
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
                        mHour_get = timePicker.getCurrentHour();
                        mMinute_get = timePicker.getCurrentMinute();
                        // 时间小于10的数字 前面补0 如01:12:00
                        endtime_time_get = new StringBuffer()
                                .append(mHour_get < 10 ? "0" + mHour_get
                                        : mHour_get)
                                .append(":")
                                .append(mMinute_get < 10 ? "0" + mMinute_get
                                        : mMinute_get).toString();
                        tv_timedisplay_end
                                .setText(endtime_time_get);
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