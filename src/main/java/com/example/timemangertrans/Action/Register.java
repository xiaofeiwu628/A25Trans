package com.example.timemangertrans.Action;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.timemangertrans.Entienty.User;
import com.example.timemangertrans.GlobalVariable;
import com.example.timemangertrans.MainActivity;
import com.example.timemangertrans.R;
import com.example.timemangertrans.Service.DBService_User;

import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity {

    EditText editText_account;
    EditText editText_password;
    Button button_register;
    List<User> ulist = new ArrayList<User>();
    int minechange = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);/*强制允许主进程联网*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_show);

        editText_account = findViewById(R.id.edittext_register_show_account);
        editText_password = findViewById(R.id.edittext_register_show_password);
        button_register = findViewById(R.id.button_register_show_register);
        DBService_User dbs = new DBService_User();
        ulist = dbs.getAllUser();
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = editText_account.getText().toString();
                String password = editText_password.getText().toString();
                int flag = 0;
                for(User u : ulist){
                    if(u.getAccount().equals(account)&&u.getPassword().equals(password)){//已存在
                        //intent.putExtra("rmaster", account);
                        flag = 1;
                        break;
                    }
                }
                if(flag == 1){
                    Toast.makeText(Register.this,"已存在，请勿重复注册",Toast.LENGTH_SHORT).show();
                }
                        else{
                            dbs.insertUserData(account,password);
                            ulist = dbs.getAllUser();
                            minechange = 1;
                            Toast.makeText(Register.this,"添加成功，请返回查看",Toast.LENGTH_SHORT).show();
                        }
            }
        });
    }

    public void onBackPressed(){
        Intent backintent = new Intent();
        backintent.putExtra("backchange",minechange);
        setResult(RESULT_OK,backintent);
        finish();
    }
}