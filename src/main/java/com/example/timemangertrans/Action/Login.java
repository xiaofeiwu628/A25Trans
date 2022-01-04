package com.example.timemangertrans.Action;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.timemangertrans.Entienty.Newtype;
import com.example.timemangertrans.Entienty.Rtype;
import com.example.timemangertrans.Entienty.User;
import com.example.timemangertrans.GlobalVariable;
import com.example.timemangertrans.MainActivity;
import com.example.timemangertrans.R;
import com.example.timemangertrans.Service.DBService_User;
import com.example.timemangertrans.ui.TotalShow.TotalShowFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Login extends AppCompatActivity {

    EditText editText_account;
    EditText editText_password;
    Button button_login;//登录
    Button button_register;//注册
    LinearLayout more;//更多地页面
    TextView showmore;//显示
    Button bt_makechangepassword;//确认更改密码
    Button bt_writeoff;//销毁
    Button bt_change;//显示更改操作
    EditText passwordchange;//输入新密码
    List<User> ulist = new ArrayList<User>();
    DBService_User dbs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);/*强制允许主进程联网*/
        super.onCreate(savedInstanceState);
        dbs = new DBService_User();
        ulist = dbs.getAllUser();
        setContentView(R.layout.login_show);
        editText_account = findViewById(R.id.edittext_login_show_account);
        editText_password = findViewById(R.id.edittext_login_show_password);
        button_login= findViewById(R.id.button_login_show_login);
        button_register = findViewById(R.id.button_login_show_register);
        more = findViewById(R.id.more);
        showmore = findViewById(R.id.showmore);
        bt_makechangepassword = findViewById(R.id.button_makechange);
        bt_writeoff = findViewById(R.id.button_login_show_writeoff);
        bt_change = findViewById(R.id.button_login_show_passwordchange);
        passwordchange = findViewById(R.id.newpassword);
        showmore.setText("显示更多");
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivityForResult(intent,1);
            }
        });
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = editText_account.getText().toString();
                String password = editText_password.getText().toString();
                int flag = 0;
                for(User u : ulist){
                    if(u.getAccount().equals(account)&&u.getPassword().equals(password)){
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        //intent.putExtra("rmaster", account);
                        GlobalVariable gv = new GlobalVariable(account);
                        startActivity(intent);
                        flag = 1;
                        break;
                    }
                }
                if(flag == 0){
                    Toast.makeText(Login.this,"账户不存在或密码错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
        showmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                more.setVisibility(View.VISIBLE);
            }
        });

        bt_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordchange.setVisibility(View.VISIBLE);
                bt_makechangepassword.setVisibility(View.VISIBLE);
            }
        });

        bt_writeoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = editText_account.getText().toString();
                String password = editText_password.getText().toString();
                int flag = 0;
                for(User u : ulist){
                    if(u.getAccount().equals(account)&&u.getPassword().equals(password)){
                        flag = 1;
                        dbs.deluser(account);
                        ulist = dbs.getAllUser();
                        break;
                    }
                }
                if(flag == 0){
                    Toast.makeText(Login.this,"账户不存在或密码错误",Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt_makechangepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = editText_account.getText().toString();
                String password = editText_password.getText().toString();
                String newpass = passwordchange.getText().toString();
                int flag = 0;
                for(User u : ulist){
                    if(u.getAccount().equals(account)&&u.getPassword().equals(password)){
                        flag = 1;
                        dbs.RecordU(account,newpass);
                        ulist = dbs.getAllUser();
                        break;
                    }
                }
                if(flag == 0){
                    Toast.makeText(Login.this,"账户不存在或密码错误",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data) {/*设置回传处理*/
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                int  backchanged = data.getIntExtra("backchange",0);
                if (backchanged == 1)
                    ulist = dbs.getAllUser();
                break;
            default:
        }

    }
    }