package com.example.timemangertrans.Action;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.timemangertrans.Entienty.Recordmine;
import com.example.timemangertrans.Entienty.Rtype;
import com.example.timemangertrans.R;
import com.example.timemangertrans.Service.DBService_Record;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class RecordChange extends AppCompatActivity {
    // tv_datedisplay bt_settingdate tv_timedisplay bt_settingtime
    int changetype;
    boolean canbedone = false;
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

    private List<String> flist = new ArrayList<String>();
    private List<Rtype> rlist = new ArrayList<Rtype>();
    private String ft = null;
    private TextView textview;

    private ArrayAdapter<String> adapter;

    Button button_affirm;
    Button button_cancel;
    Button button_addnewtype;
    EditText editText_name;
    Spinner spinnertext;

    String starttime_date = null;
    String starttime_time = null;
    String endtime_time = null;
    String endtime_date = null;
    String starttime;
    String endtime;

    private AlertDialog.Builder builder;
    private int year;
    private int month;
    private int dayOfMonth;
    private int mHour;
    private int mMinute;
    DBService_Record dbs = new DBService_Record();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);/*强制允许主进程联网*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_change);
        bt_settingdate_start = (Button) findViewById(R.id.bt_settingdate_start);
        bt_settingtime_start = (Button) findViewById(R.id.bt_settingtime_start);
        tv_datedisplay_start = (TextView) findViewById(R.id.tv_datedisplay_start);
        tv_timedisplay_start = (TextView) findViewById(R.id.tv_timedisplay_start);

        bt_settingdate_end = (Button) findViewById(R.id.bt_settingdate_end);
        bt_settingtime_end = (Button) findViewById(R.id.bt_settingtime_end);
        tv_datedisplay_end = (TextView) findViewById(R.id.tv_datedisplay_end);
        tv_timedisplay_end = (TextView) findViewById(R.id.tv_timedisplay_end);

        button_affirm = findViewById(R.id.button_record_change_affirm);
        button_cancel = findViewById(R.id.button_record_change_cancel);
        button_addnewtype = findViewById(R.id.button_record_change_addtype);
        editText_name = findViewById(R.id.edittext_record_change_name);
        spinnertext = findViewById(R.id.spinner_record_change_typeuse);
        bt_settingdate_start.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate_start();
            }
        });
        bt_settingtime_start.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getTime_start();
            }
        });
        bt_settingdate_end.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate_end();
            }
        });
        bt_settingtime_end.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getTime_end();
            }
        });
        button_affirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(starttime_date != null && starttime_date != null && endtime_date != null && endtime_time !=null){
                Recordmine newrec = new Recordmine();
                starttime = starttime_date+' '+starttime_time;
                endtime = endtime_date +' '+endtime_time;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date asdate = null;
                try {
                    asdate = sdf.parse(starttime);
                    newrec.setRdate_start_plan(asdate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    asdate = sdf.parse(endtime);
                    newrec.setRdate_end_plan(asdate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                    if(newrec.getRdate_end_plan().compareTo(newrec.getRdate_start_plan())>=0){/*结束时间在开始时间之后*/
                String rname = editText_name.getText().toString();
                newrec.setRname(rname);
                newrec.setRtype(ft);
                DBService_Record dbs = new DBService_Record();

                        dbs.PlanrecordInsert(newrec);

                        minechange = 1;

                        Toast.makeText(RecordChange.this,"插入成功，请返回查看",Toast.LENGTH_SHORT).show();
                    }
                else{
                        Toast.makeText(RecordChange.this,"请设置正确的时间",Toast.LENGTH_SHORT).show();
                }
            }else{
                    Toast.makeText(RecordChange.this,"请设置完整的时间",Toast.LENGTH_SHORT).show();
                }

            }
        });
        button_addnewtype.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aintent = new Intent(RecordChange.this,Rtype_Insert.class);

                startActivityForResult(aintent,1);
            }
        });
        rlist = dbs.getRetypeDataall();/*用来添加spinner显示什么*/
        String trans;
        for (Rtype r : rlist) {
            if(r.getFtype()!= null){
                trans = r.getFtype()+'-'+r.getStype();
                flist.add(trans);
            }else{
                trans = r.getStype();
                flist.add(trans);
            }
        }
        //第二步：为下拉列表定义一个适配器
        Collections.sort(flist);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, flist);
        //第三步：设置下拉列表下拉时的菜单样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        spinnertext.setAdapter(adapter);
        spinnertext.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> argO, View argl, int arg2, long arg3) {
                // TODO Auto-generated method stub
                /* 将所选spinnertext的值带入myTextView中*/
                ft = adapter.getItem(arg2);
                /* 将 spinnertext 显示^*/
                argO.setVisibility(View.VISIBLE);
            }
            public void onNothingSelected(AdapterView<?> argO) {
                // TODO Auto-generated method stub

                argO.setVisibility(View.VISIBLE);
            }
        });
        //将spinnertext添加到OnTouchListener对内容选项触屏事件处理
        spinnertext.setOnTouchListener(new Spinner.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                // 将mySpinner隐藏
                if(minechange == 1)
                refresh_spinner(v);
                Log.i("spinner", "Spiner Touch事件被触发!");
                return false;
            }
        });
        //焦点改变事件处理
        spinnertext.setOnFocusChangeListener(new Spinner.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub

                Log.i("spinner", "Spiner FocusChange事件被触发！");
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                backchanged = data.getIntExtra("backchange", 0);
                minechange = backchanged;
                break;
            default:
        }
    }
    @Override
    public void onBackPressed(){/*返回时回传*/
        Intent backintent = new Intent();
        backintent.putExtra("backchange",minechange);
        setResult(RESULT_OK,backintent);
        finish();
    }
    public void refresh_spinner(View v) {
        String trans;
        rlist = dbs.getRetypeDataall();
        flist.clear();
        for (Rtype r : rlist) {
            if(r.getFtype()!= null){
                trans = r.getFtype()+'-'+r.getStype();
                flist.add(trans);
            }else{
                trans = r.getStype();
                flist.add(trans);
            }
        }
        Collections.sort(flist);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, flist);
        //第三步：设置下拉列表下拉时的菜单样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        spinnertext.setAdapter(adapter);
    }
/******************究极分隔符******************时间显示控件，代码不要动***********************************究极分隔符*******/
    public void getDate_start() {
        builder = new AlertDialog.Builder(RecordChange.this);
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

        builder = new AlertDialog.Builder(RecordChange.this);
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
        builder = new AlertDialog.Builder(RecordChange.this);
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
                        tv_datedisplay_end.setText(endtime_date);
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

    public void getTime_end() {

        builder = new AlertDialog.Builder(RecordChange.this);
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
