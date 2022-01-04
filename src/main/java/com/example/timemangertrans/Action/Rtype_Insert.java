package com.example.timemangertrans.Action;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timemangertrans.Entienty.Rtype;
import com.example.timemangertrans.R;
import com.example.timemangertrans.Service.DBService_Record;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Rtype_Insert extends AppCompatActivity {

    private List<String> flist = new ArrayList<String>();
    private List<Rtype> rlist = new ArrayList<Rtype>();
    private String ft = null;
    int mycnt = 1;
    private TextView textview;
    private Spinner spinnertext;
    private ArrayAdapter<String> adapter;
    Button button_insert ;
    Button button_cancel ;
    EditText edit_newsty ;
    int minechange = 0;
    int refreshspinnered = 0;
    DBService_Record dbs = new DBService_Record();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);/*强制允许主进程联网*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rtype_insert);


        rlist = dbs.getRetypeDataall();

        button_insert = findViewById(R.id.button_rtype_insert_ok);
        edit_newsty = findViewById(R.id.edittext_rtype_insert_newstype);
        spinnertext = findViewById(R.id.spinner_rtype_insert_ftype);
        //第二步：为下拉列表定义一个适配器
        Collections.sort(flist);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, flist);
        //第三步：设置下拉列表下拉时的菜单样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        spinnertext.setAdapter(adapter);
        //第五步：添加监听器，为下拉列表设置事件的响应
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
                if(refreshspinnered == 0){
                    refresh_spinner(v);
                    refreshspinnered ++;
                }
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


        button_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rtype ruse = new Rtype();
                for(Rtype t:rlist){
                    if(t.getFtype()!=null) {
                        if (((t.getFtype() + '-' + t.getStype()).equals(ft))) {
                            mycnt = t.getCnt() + 1;
                        }
                    }else{
                        if(t.getStype().equals(ft)){
                            mycnt = t.getCnt() + 1;
                        }
                    }
                }
                ruse.setCnt(mycnt);
                ruse.setFtype(ft);
                ruse.setStype(edit_newsty.getText().toString());
                dbs.rtypeInsert(ruse);
                minechange = 1;
                Toast.makeText(Rtype_Insert.this,"插入成功，请返回查看",Toast.LENGTH_SHORT).show();
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void refresh_spinner(View v) {
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
        Collections.sort(flist);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, flist);
        //第三步：设置下拉列表下拉时的菜单样式
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        spinnertext.setAdapter(adapter);
    }
    @Override
    public void onBackPressed(){
        Intent backintent = new Intent();
        backintent.putExtra("backchange",minechange);
        setResult(RESULT_OK,backintent);
        finish();
    }
}