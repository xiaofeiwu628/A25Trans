package com.example.timemangertrans.ui.notifications;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timemangertrans.Entienty.NewRecordmine;
import com.example.timemangertrans.Entienty.Recordmine;
import com.example.timemangertrans.Entienty.Rtype;
import com.example.timemangertrans.R;
import com.example.timemangertrans.Service.DBService_Record;
import com.example.timemangertrans.databinding.FragmentNotificationsBinding;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {
    SatAdapt adapter;
    private NotificationsViewModel statisticsViewModel;
    private FragmentNotificationsBinding binding;
    DBService_Record dbs = new DBService_Record();/*连接数据库用的*/
    List<Recordmine> recordlist_got = new ArrayList<Recordmine>();/*本页面全局记录list*/
    List<NewRecordmine> newrecordlist = new ArrayList<NewRecordmine>();
    TextView show_name ;
    TextView show_check;
    TextView show_lasttime;
    TextView show_intime_eff;
    TextView show_intime_ineff;
    TextView show_overtime_eff;
    TextView show_overtime_ineff;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);/*强制允许主进程联网*/
        super.onCreate(savedInstanceState);
        statisticsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
/****************************/
        show_name = binding.textViewSatShowname;
        show_check = binding.textViewSatShownocheck;
        show_lasttime = binding.textViewSatShowLasttime;
        show_intime_eff = binding.textViewSatShowIntimeEff;
        show_intime_ineff = binding.textViewSatShowIntimeIneff;
        show_overtime_eff = binding.textViewSatShowOvertimeEff;
        show_overtime_ineff = binding.textViewSatShowOvertimrIneff;




        RecyclerView recyclerView_this = binding.recyclerviewSatisticsRecycle;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());/*制定recycle方式*/
        recyclerView_this.setLayoutManager(layoutManager);/*将上面制定的recycle方式写进recycle实例*/
        adapter = new SatAdapt(newrecordlist);
        recyclerView_this.setAdapter(adapter);
        binding.buttonSatisticsSearch.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String key = binding.editTextSatisticsSearch.getText().toString();
                recordlist_got.clear();
                newrecordlist.clear();
                recordlist_got = dbs.getRecordDataall();



                for(Recordmine rgot :recordlist_got){//遍历得到的队列

                    if(rgot.getRname().equals(key)){//获得重名的


                    int flag = 0;//先假设设改got的元素在插入队列没有重名的
                    long minutes_actual = 0;


                    for(NewRecordmine rnew : newrecordlist){//遍历要插入队列
                        if(rgot.getRname().equals(rnew.getName())){//该got元素与某个插入元素重名


                            if(rgot.getChecked().equals("n")){//未记录的加一计数
                                rnew.setNorecord(rnew.getNorecord()+1);
                                flag = 1;
                            }
                            else{//已记录的更新插入队列
                                minutes_actual = 0-ChronoUnit.MINUTES.between(Instant.ofEpochMilli(rgot.getRdate_end().getTime()), Instant.ofEpochMilli(rgot.getRdate_start().getTime()));
                                rnew.setLasttime(rnew.getLasttime()+ minutes_actual);//将插入队列中的那个元素的时间相加

                                if (rgot.getRstatus().equals("拖延高效")){
                                    rnew.setOvwetime_efficient(rnew.getOvwetime_efficient()+1);
                                }else if(rgot.getRstatus().equals("拖延低效")){
                                    rnew.setOvertime_inefficiency(rnew.getOvertime_inefficiency()+1);
                                }else if(rgot.getRstatus().equals("及时高效")){
                                    rnew.setIntime_efficient(rnew.getIntime_efficient()+1);
                                }else if(rgot.getRstatus().equals("及时低效")){
                                    rnew.setIntime_inefficiency(rnew.getIntime_inefficiency());
                                }

                                flag = 1;
                                break;
                            }



                        }
                    }


                    if(flag == 0) {//got元素确实未被插入

                        NewRecordmine nr = new NewRecordmine();
                        if (rgot.getChecked().equals("n")) {//未记录的加一计数
                            nr.setName(rgot.getRname());
                            nr.setNorecord(1);
                        } else {
                            minutes_actual = 0 - ChronoUnit.MINUTES.between(Instant.ofEpochMilli(rgot.getRdate_end().getTime()), Instant.ofEpochMilli(rgot.getRdate_start().getTime()));
                            nr.setLasttime(minutes_actual);
                            nr.setName(rgot.getRname());
                            if (rgot.getRstatus().equals("拖延高效")) {
                                nr.setOvwetime_efficient(nr.getOvwetime_efficient() + 1);
                            } else if (rgot.getRstatus().equals("拖延低效")) {
                                nr.setOvertime_inefficiency(nr.getOvertime_inefficiency() + 1);
                            } else if (rgot.getRstatus().equals("及时高效")) {
                                nr.setIntime_efficient(nr.getIntime_efficient() + 1);
                            } else if (rgot.getRstatus().equals("及时低效")) {
                                nr.setIntime_inefficiency(nr.getIntime_inefficiency());
                            }
                            System.out.println(key + "+++" + rgot.getRname());
                        }
                        newrecordlist.add(nr);

                    }
                }





                }






                refresh_recycle(newrecordlist);
            }
        });
        // adapter = new SatAdapt(recordlist_got);/*给recycle适配器数据*/
        // recyclerView_this.setAdapter(adapter);/*给recycle实例加入适配器设定*/






        /******************/
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void refresh_recycle(List<NewRecordmine> l) {/*更新记录recycle显示*/
        if(l.size()!=0){

        adapter.updatatype(l);
        adapter.notifyDataSetChanged();//*更新list显示*//*
    }}

    public  class SatAdapt extends RecyclerView.Adapter<NotificationsFragment.SatAdapt.ViewHolder> {
        private List<NewRecordmine> List_type;/*要显示的资源队列*/
        class ViewHolder extends RecyclerView.ViewHolder {/**/
            TextView show_name;

            View newrecord_view;
            public ViewHolder(View view) {/*获得子项对象，进一步获得子项布局元素*/
                super(view);
                newrecord_view = view;
                show_name = view.findViewById(R.id.textView_satson_name);
            }
        }
        public SatAdapt(List<NewRecordmine> aList) {
            List_type = aList;/*根据构造函数，给要显示的资源队列传值*/
        }/*构造函数，获得数据源*/
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {/*对ViewHolder构造，获得布局对象，返回viewHolder实例*/
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sat_son, parent, false);/*设置要显示的子项的整个布局*/
            final NotificationsFragment.SatAdapt.ViewHolder holder = new NotificationsFragment.SatAdapt.ViewHolder(view);

            holder.newrecord_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();/*获得当前点击对象对应队列中的下标（位置）*/
                    NewRecordmine recuse =  List_type.get(position);/*获得当前点击项目在资源队列中对应的显示对象值*/
                    show_name.setText(recuse.getName());
                    show_check.setText("未记录的数量为："+recuse.getNorecord());
                    System.out.println("time-+-+--+-+--**************"+recuse.getLasttime());
                    long minutes = recuse.getLasttime();
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
                    show_lasttime.setText("总的持续时间为："+durationtime_plan);
                    show_intime_eff.setText("评价为及时高效的次数为："+recuse.getIntime_efficient());
                    show_intime_ineff.setText("评价为及时低效的次数为："+recuse.getIntime_inefficiency());
                    show_overtime_eff.setText("评价为拖延高效的次数为："+recuse.getOvwetime_efficient());
                    show_overtime_ineff.setText("评价为拖延低效的次数为："+recuse.getOvertime_inefficiency());
                    // refresh_recycle(recordlist_toshow);
                    //Intent intent = new Intent(v.getContext(), RecordShow.class);
                }
            });
            return holder;
        }
        @Override
        public void onBindViewHolder(NotificationsFragment.SatAdapt.ViewHolder holder, int position) {/*创建时布局中的控件和数据进行匹配*/
            NewRecordmine newrecorduse = List_type.get(position);/*获得相应位置（position）对应的子项数据*/
            //holder.show_type.setText(typeuse.getShowname());
            System.out.println("++++++++++++++++++1+++++++++++++++++++++");
            holder.show_name.setText(newrecorduse.getName());
        }
        @Override
        public int getItemCount() {/*返回资源队列子项个数*/
            Log.d("goodActivity", "this is " + String.valueOf(List_type.size()));
            return List_type.size();
        }
        public void updatatype(List<NewRecordmine> stus) {
            this.List_type = stus;
        }
    }
}