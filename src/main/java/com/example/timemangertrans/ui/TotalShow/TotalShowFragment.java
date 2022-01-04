package com.example.timemangertrans.ui.TotalShow;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timemangertrans.Action.RecordChange;
import com.example.timemangertrans.Action.RecordShow;
import com.example.timemangertrans.Entienty.Recordmine;
import com.example.timemangertrans.Entienty.Rtype;
import com.example.timemangertrans.Entienty.Newtype;
import com.example.timemangertrans.GlobalVariable;
import com.example.timemangertrans.R;
import com.example.timemangertrans.Service.DBService_Record;
import com.example.timemangertrans.databinding.FragmentTotalshowBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TotalShowFragment extends Fragment {

    private TotalShowViewModel totalshowViewModel;/*不知道干啥用的*/
    private FragmentTotalshowBinding binding;//！！！这一句很奇特，这个类不是我写的,但就是能用
    private List<Recordmine> recordlist_got = new ArrayList<Recordmine>();/*本页面全局记录list*/
    private List<Recordmine> recordlist_toshow = new ArrayList<Recordmine>();/*本页面全局记录list*/
    private List<Rtype> rlist = new ArrayList<Rtype>();/*存放ype表中数据*/
    private List<String> type_show_list = new ArrayList<String>();/*用来给spinner显示类型,也就是spinner中要显示什么的list*/
    TotalshowListAdapter adapter ;/*记录显示的adatper*/
    int backchanged = 0;/*用来判定是否刷新的参数*/
    int spinnertochange = 1;/*用来判定spinner是否需要刷新，防止过度刷新*/
    private Spinner spinnertext;
    private ArrayAdapter<String> adapter_string;
    TextView textView_showtitle;/*spinner设置显示当前选择了什么类型*/
    DBService_Record dbs = new DBService_Record();/*连接数据库用的*/

    /**
     * 一开始获取所有的Ptype，放到获取队列  rtypelist_got  中
     * 创建一个newtypelist队列，每个元素里面有
     *     String fatype;    父类型
     *     String sotype;    子类型
     *     String totalname; 全名（拼接）
     *     String showname;  显示名（加空格）
     *     int cnt;          第几层
     *
     * 然后,一开始，只把层数为1的Rtype加入newtypelist，进行拼接等操作，记录队列也是只显示和第一层名字相同的记录
     * 然后，每点击一下，把父类型是被点击选项的rtype加入类型显示队列，同时在记录队列中把和被点击选项名字相同的记录显示
     * 依次类推，完成全部显示
     * 要注意，每次添加显示之前，要对显示队列按全名的字典序排序
     * 还要注意，这个还没设置返回功能，只能扩展不能返回
     * */

    /*******变量_添加无限扩展子层测试*******/
    /*******变量_添加无限扩展子层测试*******/
    private List<Rtype> rtypelist_got = new ArrayList<Rtype>();/*获得所有类型*/
    private List<Newtype> newtypelist = new ArrayList<Newtype>();/*要进行显示的类型*/
    TotalshowListAdapter_type adapter_type ;/*记录显示的adatper*/
    RecyclerView recyclerView_this_type;
    /*******变量_添加无限扩展子层测试*******/
    /*******变量_添加无限扩展子层测试*******/


    /************************封装不要动*********************/
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        totalshowViewModel =
                new ViewModelProvider(this).get(TotalShowViewModel.class);

        binding = FragmentTotalshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
    /************************封装不要动*********************/
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);/*强制允许主进程联网*/
        super.onCreate(savedInstanceState);

        /*******_onCreate_添加无限扩展子层测试*******/
        /*******_onCreate_添加无限扩展子层测试*******/

        recyclerView_this_type = binding.recyclerviewTotalShowrRecycleType;
        LinearLayoutManager layoutManager_type = new LinearLayoutManager(getActivity());/*制定recycle方式*/
        recyclerView_this_type.setLayoutManager(layoutManager_type);/*将上面制定的recycle方式写进recycle实例*/
        rtypelist_got = dbs.getRetypeDataall();//获得所有类型

        for(Rtype r :rtypelist_got){
            Newtype toaddtype = new Newtype();
            if(r.getCnt()==1){
                toaddtype.setFatype(r.getFtype());
                toaddtype.setSotype(r.getStype());
                if(r.getFtype()!= null){
                    toaddtype.setTotalname(r.getFtype()+'-'+r.getStype());;
                }else{
                    toaddtype.setTotalname(r.getStype());;
                }
                toaddtype.setShowname(toaddtype.getTotalname());
                newtypelist.add(toaddtype);
            }
        }
        Collections.sort(newtypelist);
        adapter_type = new TotalshowListAdapter_type(newtypelist);/*给recycle适配器数据*/
        recyclerView_this_type.setAdapter(adapter_type);/*给recycle实例加入适配器设定*/

        /*******_onCreate_添加无限扩展子层测试*******/
        /*******_onCreate_添加无限扩展子层测试*******/

        /******spinner用****/
        textView_showtitle = binding.textViewTotalShowTitle;
        spinnertext = binding.spinnerTotalShowTypeuse;
        adapter_string = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, type_show_list);
        adapter_string.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnertext.setAdapter(adapter_string);
        spinnertext.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> argO, View argl, int arg2, long arg3) {/*设置选择时响应*/
                // TODO Auto-generated method stub
                /* 将所选spinnertext的值带入myTextView中*/
                textView_showtitle.setText("你选择了:" + adapter_string.getItem(arg2));
                /* 将 spinnertext 显示^*/
                String key =adapter_string.getItem(arg2);
                recordlist_got = dbs.getRereshDataall(key);
                //Log.d("TotalShow","有几个？+"+recordlist.size());
                refresh_recycle(recordlist_got);
                //Toast.makeText(TotalShow.this,adapter_string.getItem(arg2),Toast.LENGTH_SHORT).show();
                argO.setVisibility(View.VISIBLE);
            }
            public void onNothingSelected(AdapterView<?> argO) {
                // TODO Auto-generated method stub
                textView_showtitle.setText("NONE");
                argO.setVisibility(View.VISIBLE);
            }
        });
        spinnertext.setOnTouchListener(new Spinner.OnTouchListener() {/*设置触碰时响应*/
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if(spinnertochange == 1) {
                    refresh_spinner(v);
                    spinnertochange --;
                }
                return false;
            }
        });
        spinnertext.setOnFocusChangeListener(new Spinner.OnFocusChangeListener() {//焦点改变事件处理，没用
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                //v.setVisibility(View.VISIBLE);
                Log.i("spinner", "Spiner FocusChange事件被触发！");
            }
        });
        /******spinner用****/


        /******记录recycle用*****/
        RecyclerView recyclerView_this = binding.recyclerviewTotalShowrEcycle;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());/*制定recycle方式*/
        recyclerView_this.setLayoutManager(layoutManager);/*将上面制定的recycle方式写进recycle实例*/
        recordlist_got = dbs.getRecordDataall();//获得所有记录
        adapter = new TotalshowListAdapter(recordlist_got);/*给recycle适配器数据*/
        recyclerView_this.setAdapter(adapter);/*给recycle实例加入适配器设定*/
        /******记录recycle用*****/


        Button button_add = binding.buttonTotalShowAdd;/*添加新纪录*/
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RecordChange.class);
                intent.putExtra("change","in");
                startActivityForResult(intent,1);
                GlobalVariable gv = new GlobalVariable();
                System.out.println("----------------------"+gv.getUseraccount());
            }
        });
        /**oncreat底部**/
        return root;
    }


    /*******_gn_添加无限扩展子层测试*******/
    /*******_gn_添加无限扩展子层测试*******/
    public  class TotalshowListAdapter_type extends RecyclerView.Adapter<TotalshowListAdapter_type.ViewHolder> {
        private List<Newtype> List_type;/*要显示的资源队列*/
        class ViewHolder extends RecyclerView.ViewHolder {/**/
            TextView show_type;
            View type_view;
            public ViewHolder(View view) {/*获得子项对象，进一步获得子项布局元素*/
                super(view);
                type_view = view;
                show_type = view.findViewById(R.id.textView_total_type_son_type);
            }
        }
        public TotalshowListAdapter_type(List<Newtype> aList) {
            List_type = aList;/*根据构造函数，给要显示的资源队列传值*/
        }/*构造函数，获得数据源*/
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {/*对ViewHolder构造，获得布局对象，返回viewHolder实例*/
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.total_type_son, parent, false);/*设置要显示的子项的整个布局*/
            final ViewHolder holder = new ViewHolder(view);

            holder.type_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();/*获得当前点击对象对应队列中的下标（位置）*/
                    Newtype recuse =  List_type.get(position);/*获得当前点击项目在资源队列中对应的显示对象值*/
                    boolean need = false;
                    for(Rtype r :rtypelist_got){/*循环给队列添加需要扩展的资源*/
                        Newtype toaddtype = new Newtype();
                        if(r.getFtype()!=null) {
                            if (r.getExtended().equals("n")){//未被扩展过的才会进行扩展
                                if (r.getFtype().equals(recuse.getTotalname())) {
                                    toaddtype.setFatype(r.getFtype());
                                    toaddtype.setSotype(r.getStype());
                                    toaddtype.setTotalname(r.getFtype() + '-' + r.getStype());
                                    String block = "->";
                                    if (r.getCnt() >= 3)
                                        for (int i = 3; i <= r.getCnt(); i++) {
                                            block = block + "->";
                                        }
                                    toaddtype.setShowname(block + toaddtype.getTotalname());
                                    newtypelist.add(toaddtype);
                                    r.setExtended("y");
                                    need =true;
                                }
                            }
                        }
                    }
                    if(need == true){/*确定队列增添了内容需要刷新显示type*/
                        Collections.sort(newtypelist);
                        adapter_type.updatatype(newtypelist);
                        adapter_type.notifyDataSetChanged();
                    }
                    recordlist_toshow.clear();
                    for(Recordmine r : recordlist_got){
                        if(r.getRtype().equals(recuse.getTotalname())){
                            recordlist_toshow.add(r);
                        }
                    }
                    refresh_recycle(recordlist_toshow);
                    //Intent intent = new Intent(v.getContext(), RecordShow.class);
                }
            });
            return holder;
        }
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {/*创建时布局中的控件和数据进行匹配*/
            Newtype typeuse = List_type.get(position);/*获得相应位置（position）对应的子项数据*/
            holder.show_type.setText(typeuse.getShowname());
        }
        @Override
        public int getItemCount() {/*返回资源队列子项个数*/
            Log.d("goodActivity", "this is " + String.valueOf(List_type.size()));
            return List_type.size();
        }
        public void updatatype(List<Newtype> stus) {
            this.List_type = stus;
        }
    }
    /*******_gn_添加无限扩展子层测试*******/
    /*******_gn_添加无限扩展子层测试*******/


    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){/*设置回传处理*/
        switch (requestCode){
            case 1:
            case 2:
                backchanged = data.getIntExtra("backchange",0);

                if(backchanged == 1){


                    recordlist_got = dbs.getRecordDataall();
                    refresh_recycle(recordlist_got);
                    rtypelist_got = dbs.getRetypeDataall();//获得所有类型
                    newtypelist.clear();
                    for(Rtype r :rtypelist_got){
                        Newtype toaddtype = new Newtype();
                        if(r.getCnt()==1){
                            toaddtype.setFatype(r.getFtype());
                            toaddtype.setSotype(r.getStype());
                            if(r.getFtype()!= null){
                                toaddtype.setTotalname(r.getFtype()+'-'+r.getStype());;
                            }else{
                                toaddtype.setTotalname(r.getStype());;
                            }
                            toaddtype.setShowname(toaddtype.getTotalname());
                            newtypelist.add(toaddtype);
                        }
                    }
                    Collections.sort(newtypelist);
                    adapter_type = new TotalshowListAdapter_type(newtypelist);/*给recycle适配器数据*/
                    recyclerView_this_type.setAdapter(adapter_type);/*给recycle实例加入适配器设定*/
                    spinnertochange =1;
                    backchanged = 0;

                }
                break;
            default:
        }
    }


    public void refresh_recycle(List<Recordmine> l) {/*更新记录recycle显示*/
        adapter.updataRecord(l);
        adapter.notifyDataSetChanged();//*更新list显示*//*
    }


    public void refresh_spinner(View v) {/*更新spinner显示*/
        rlist = dbs.getRetypeDataall();
        String trans;
        type_show_list.clear();
        for (Rtype r : rlist) {
            if(r.getFtype()!= null){
                trans = r.getFtype()+'-'+r.getStype();
                type_show_list.add(trans);
            }else{
                trans = r.getStype();
                type_show_list.add(trans);
            }
        }
        adapter_string = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, type_show_list);
        //第三步：设置下拉列表下拉时的菜单样式
        adapter_string.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        spinnertext.setAdapter(adapter_string);
    }


    /*记录recycle适配器*/
    public  class TotalshowListAdapter extends RecyclerView.Adapter<TotalshowListAdapter.ViewHolder> {
        private List<Recordmine> RecordList;
        class ViewHolder extends RecyclerView.ViewHolder {/**/
            CheckBox total_checkbox;
            TextView total_name;
            TextView total_time;
            View total_view;
            TextView total_status;
            TextView total_type;
            public ViewHolder(View view) {/*获得子项对象，进一步获得子项布局元素*/
                super(view);
                total_view = view;
                total_checkbox = view.findViewById(R.id.checkBox_total_son_check);
                total_name = view.findViewById(R.id.textView_total_son_showname);
                total_time = view.findViewById(R.id.textView_total_son_showtime);
                total_type = view.findViewById(R.id.textView_total_show_type);
                total_status = view.findViewById(R.id.textView_total_show_status);
            }
        }
        public TotalshowListAdapter(List<Recordmine> aList) {
            RecordList = aList;
            //System.out.println("B处个数是:"+recordlist.size());
        }/*构造函数，获得数据源*/
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {/*对ViewHolder构造，获得布局对象，返回viewHolder实例*/
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.total_son, parent, false);
            final ViewHolder holder = new ViewHolder(view);

            holder.total_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    Recordmine recuse =  RecordList.get(position);
                    Intent intent = new Intent(v.getContext(), RecordShow.class);
                    intent.putExtra("name", recuse.getRname());
                    intent.putExtra("type", recuse.getRtype());
                    if(recuse.getRdate_start_plan()!=null)
                    intent.putExtra("starttime_plan", recuse.getRdate_start_plan().toString());
                    //System.out.println(recuse.getRdate_start());
                    if(recuse.getRdate_end_plan()!=null)
                    intent.putExtra("endtime_plan", recuse.getRdate_end_plan().toString());
                    intent.putExtra("checked", recuse.getChecked());
                    intent.putExtra("id",recuse.getId());
                    if(recuse.getChecked().equals("y")){
                        intent.putExtra("starttime", recuse.getRdate_start().toString());
                        intent.putExtra("endtime", recuse.getRdate_end().toString());
                    }
                    TotalShowFragment.this.startActivityForResult(intent,2);

                }
            });
            return holder;
        }
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {/*创建时布局中的控件和数据进行匹配*/
            Recordmine recorduse = RecordList.get(position);/*获得相应位置（position）对应的子项数据*/
            holder.total_name.setText(recorduse.getRname());
            if(recorduse.getRdate_end_plan()!=null)
                holder.total_time.setText(recorduse.getRdate_end_plan().toString());
            if (recorduse.getChecked().equals("y")) {
                holder.total_checkbox.setChecked(true);
            } else {
                holder.total_checkbox.setChecked(false);
            }
            holder.total_type.setText(recorduse.getRtype());
            if(recorduse.getRstatus()!=null)
            if(!recorduse.getRstatus().isEmpty())
            holder.total_status.setText(recorduse.getRstatus());
        }
        @Override
        public int getItemCount() {/*返回子项个数*/
            Log.d("goodActivity", "this is " + String.valueOf(RecordList.size()));
            return RecordList.size();
        }
        public void updataRecord(List<Recordmine> stus) {
            this.RecordList = stus;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}