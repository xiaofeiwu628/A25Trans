package com.example.timemangertrans.Service;


import android.util.Log;

import com.example.timemangertrans.Dao.DBOpenHelper;
import com.example.timemangertrans.Entienty.Recordmine;
import com.example.timemangertrans.Entienty.Rtype;
import com.example.timemangertrans.Entienty.User;
import com.example.timemangertrans.GlobalVariable;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DBService_Record {
    GlobalVariable gv = new GlobalVariable();
    public Connection conn=null; //打开数据库对象
    public PreparedStatement ps=null;//操作整合sql语句的对象
    public ResultSet rs=null;//查询结果的集合

    //DBService_User 对象
    public static DBService_Record dbServiceLogin =null;


    public DBService_Record(){

    }

    public List<Recordmine> getRecordDataall(){/*总显示界面查询所有的记录，已通过测试*/
        //结果存放集合
        List<Recordmine> list=new ArrayList<Recordmine>();
        //MySQL 语句
        String sql="select * from record where `rmaster` = ?";
        //获取链接数据库对象
        conn= (Connection) DBOpenHelper.getConn();

        try {
            if(conn!=null&&(!conn.isClosed())){

                ps= (PreparedStatement) conn.prepareStatement(sql);
                ps.setString(1,gv.getUseraccount());
                if(ps!=null){
                    rs= (ResultSet) ps.executeQuery();
                    if(rs!=null){
                        while(rs.next()){
                            Recordmine u=new Recordmine();
                            u.setRname(rs.getString("rname"));
                            u.setRmaster(rs.getString("rmaster"));
                            u.setRtype(rs.getString("rtype"));
                            u.setRdate_start(rs.getTimestamp("rdate_start"));
                            u.setRdate_end(rs.getTimestamp("rdate_end"));
                            u.setRremark(rs.getString("rremark"));
                            u.setChecked(rs.getString("checked"));
                            u.setRdate_end_plan(rs.getTimestamp("rdate_end_plan"));
                            u.setRdate_start_plan(rs.getTimestamp("rdate_start_plan"));
                            u.setRstatus(rs.getString("rstatus"));
                            u.setId(rs.getInt("id"));
                            //System.out.println("时间内容是:"+u.getRname());
                            list.add(u);
                            //System.out.println("个数是:"+list.size());
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBOpenHelper.closeAll(conn,ps,rs);//关闭相关操作
        return list;
    }

    public List<Recordmine> getCheckedRecordDataall(){/*总显示界面查询所有的记录，已通过测试*/
        //结果存放集合
        List<Recordmine> list=new ArrayList<Recordmine>();
        //MySQL 语句
        String sql="select * from record where `checked` = 'y' and `rmaster` = ?";
        //获取链接数据库对象
        conn= (Connection) DBOpenHelper.getConn();
        try {
            if(conn!=null&&(!conn.isClosed())){
                ps= (PreparedStatement) conn.prepareStatement(sql);
                ps.setString(1,gv.getUseraccount());
                if(ps!=null){
                    rs= (ResultSet) ps.executeQuery();
                    if(rs!=null){
                        while(rs.next()){
                            Recordmine u=new Recordmine();
                            u.setRname(rs.getString("rname"));
                            u.setRmaster(rs.getString("rmaster"));
                            u.setRtype(rs.getString("rtype"));
                            u.setRdate_start(rs.getTimestamp("rdate_start"));
                            u.setRdate_end(rs.getTimestamp("rdate_end"));
                            u.setRremark(rs.getString("rremark"));
                            u.setChecked(rs.getString("checked"));
                            //System.out.println("时间内容是:"+u.getRname());
                            list.add(u);
                            //System.out.println("个数是:"+list.size());
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBOpenHelper.closeAll(conn,ps,rs);//关闭相关操作
        return list;
    }



    public List<Recordmine> getRereshDataall(String keyword){/*总显示界面根据类型刷新，已通过测试*/
        //结果存放集合
        String keywordnew = keyword+"%";//PreparedStatement本身对like不适合，似乎是因为不能加'   '
        List<Recordmine> list=new ArrayList<Recordmine>();
        //MySQL 语句
        //String sql="SELECT * FROM `record` where rtype like '%?%'";
        String sql="SELECT * FROM `record` where rtype like ?";
        //获取链接数据库对象
        conn= (Connection) DBOpenHelper.getConn();
        try {
            if(conn!=null&&(!conn.isClosed())){

                ps= (PreparedStatement) conn.prepareStatement(sql);
                ps.setString(1,keywordnew);
                if(ps!=null){
                    rs= (ResultSet) ps.executeQuery();
                    if(rs!=null){
                        while(rs.next()){
                            Recordmine u=new Recordmine();
                            u.setRname(rs.getString("rname"));
                            u.setRmaster(rs.getString("rmaster"));
                            u.setRtype(rs.getString("rtype"));
                            u.setRdate_start(rs.getTimestamp("rdate_start"));
                            u.setRdate_end(rs.getTimestamp("rdate_end"));
                            u.setRremark(rs.getString("rremark"));
                            u.setChecked(rs.getString("checked"));
                            u.setRdate_end_plan(rs.getTimestamp("rdate_end_plan"));
                            u.setRdate_start_plan(rs.getTimestamp("rdate_start_plan"));
                            u.setRstatus(rs.getString("rstatus"));
                            u.setId(rs.getInt("id"));
                            //System.out.println("时间内容是:"+u.getRname());
                            list.add(u);
                            //System.out.println("个数是:"+list.size());
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBOpenHelper.closeAll(conn,ps,rs);//关闭相关操作
        Log.d("DBService_Record","有几个呢"+list.size());
        return list;
    }

    public List<Recordmine> getDimDataall(String keyword){/*总显示界面根据类型刷新，已通过测试*/
        //结果存放集合
        String keywordnew = "%"+keyword+"%";//PreparedStatement本身对like不适合，似乎是因为不能加'   '
        List<Recordmine> list=new ArrayList<Recordmine>();
        //MySQL 语句
        //String sql="SELECT * FROM `record` where rtype like '%?%'";
        String sql="SELECT * FROM `record` where rname like ?";
        //获取链接数据库对象
        conn= (Connection) DBOpenHelper.getConn();
        try {
            if(conn!=null&&(!conn.isClosed())){
                ps= (PreparedStatement) conn.prepareStatement(sql);
                ps.setString(1,keywordnew);
                if(ps!=null){
                    rs= (ResultSet) ps.executeQuery();
                    if(rs!=null){
                        while(rs.next()){
                            Recordmine u=new Recordmine();
                            u.setRname(rs.getString("rname"));
                            u.setRmaster(rs.getString("rmaster"));
                            u.setRtype(rs.getString("rtype"));
                            u.setRdate_start(rs.getTimestamp("rdate_start"));
                            u.setRdate_end(rs.getTimestamp("rdate_end"));
                            u.setRremark(rs.getString("rremark"));
                            u.setChecked(rs.getString("checked"));
                            u.setRdate_end_plan(rs.getTimestamp("rdate_end_plan"));
                            u.setRdate_start_plan(rs.getTimestamp("rdate_start_plan"));
                            u.setRstatus(rs.getString("rstatus"));
                            u.setId(rs.getInt("id"));
                            //System.out.println("时间内容是:"+u.getRname());
                            list.add(u);
                            //System.out.println("个数是:"+list.size());
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBOpenHelper.closeAll(conn,ps,rs);//关闭相关操作
        Log.d("DBService_Record","有几个呢"+list.size());
        return list;
    }

    public int PlanrecordInsert(Recordmine recin){/*插入新记录,已通过测试*/
        int result=-1;
        if(true){
            //获取链接数据库对象
            conn= (Connection) DBOpenHelper.getConn();
            //MySQL 语句
            String sql="INSERT INTO `timemanage`.`record`(`rname`, `rmaster`, `rtype`, `rdate_start_plan`, `rdate_end_plan`, `checked` ) VALUES (?, ?, ?, ?, ?, ?)";
            try {

                if((conn!=null)&&(!conn.isClosed())){

                    ps= (PreparedStatement) conn.prepareStatement(sql);

                    ps.setString(1,recin.getRname());
                    ps.setString(2,gv.getUseraccount());
                    ps.setString(3,recin.getRtype());
                    Timestamp tTime = new Timestamp(recin.getRdate_start_plan().getTime());
                    ps.setTimestamp(4,tTime);
                    tTime = new Timestamp(recin.getRdate_end_plan().getTime());
                    ps.setTimestamp(5,tTime);
                    String checked = "n";
                    ps.setString(6,checked);

                    result=ps.executeUpdate();//返回1 执行成功
                    System.out.println("inback="+result);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        DBOpenHelper.closeAll(conn,ps);//关闭相关操作
        return result;
    }

    public int RecordUP(Recordmine recin){/**/
        int result=-1;
        if(true){
            //获取链接数据库对象
            conn= (Connection) DBOpenHelper.getConn();
            //MySQL 语句
            String sql="UPDATE `timemanage`.`record` SET `rdate_start` = ?, `rdate_end` = ?, `checked` = 'y', `rstatus` = ? WHERE `id` = ? and `rmaster` = ? ";
            try {
                if((conn!=null)&&(!conn.isClosed())){

                    ps= (PreparedStatement) conn.prepareStatement(sql);
                    Timestamp tTime = new Timestamp(recin.getRdate_start().getTime());
                    ps.setTimestamp(1,tTime);
                    tTime = new Timestamp(recin.getRdate_end().getTime());
                    ps.setTimestamp(2,tTime);
                    ps.setString(3,recin.getRstatus());
                    ps.setInt(4,recin.getId());
                    ps.setString(5,gv.getUseraccount());
                    result=ps.executeUpdate();//返回1 执行成功
                    System.out.println("inbackaaa="+result);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        DBOpenHelper.closeAll(conn,ps);//关闭相关操作
        return result;
    }

    public int RecordDelete(int id){/**/
        int result=-1;
        if(true){
            //获取链接数据库对象
            conn= (Connection) DBOpenHelper.getConn();
            //MySQL 语句
            String sql="DELETE FROM `record` WHERE `id`= ?";
            try {
                if((conn!=null)&&(!conn.isClosed())){

                    ps= (PreparedStatement) conn.prepareStatement(sql);
                    ps.setInt(1,id);
                    result=ps.executeUpdate();//返回1 执行成功
                    System.out.println("inbackaaa="+result);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        DBOpenHelper.closeAll(conn,ps);//关闭相关操作
        return result;
    }


    public int rtypeInsert(Rtype rtype){/*插入新类型，已通过测试*/
        int result=-1;
        if(true){
            //获取链接数据库对象
            conn= (Connection) DBOpenHelper.getConn();
            //MySQL 语句
            String sql="INSERT INTO `rtype` (`ftype`, `stype`, `cnt` , `rmaster`) VALUES (?, ?, ? ,?)  ";
            try {
                if((conn!=null)&&(!conn.isClosed())){
                    ps= (PreparedStatement) conn.prepareStatement(sql);
                    ps.setString(1,rtype.getFtype());
                    ps.setString(2,rtype.getStype());
                    ps.setInt(3,rtype.getCnt());
                    ps.setString(4,gv.getUseraccount());
                    result=ps.executeUpdate();//返回1 执行成功
                    System.out.println("retype-inback="+result);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        DBOpenHelper.closeAll(conn,ps);//关闭相关操作
        return result;
    }
    public List<Rtype> getRetypeDataall(){/*查询所有的类型，已通过测试*/
        //结果存放集合
        List<Rtype> list=new ArrayList<Rtype>();
        //MySQL 语句
        String sql="select * from rtype where rmaster = ? ";
        //获取链接数据库对象
        conn= (Connection) DBOpenHelper.getConn();
        try {
            if(conn!=null&&(!conn.isClosed())){
                ps= (PreparedStatement) conn.prepareStatement(sql);
                if(ps!=null){
                    ps.setString(1,gv.getUseraccount());
                    rs= (ResultSet) ps.executeQuery();
                    if(rs!=null){
                        while(rs.next()){
                            Rtype t=new Rtype();
                            //System.out.println("时间内容是:"+u.getRname());
                            t.setFtype(rs.getString("ftype"));
                            t.setStype(rs.getString("stype"));
                            t.setCnt(rs.getInt("cnt"));
                            t.setExtended("n");
                            list.add(t);
                            //System.out.println("个数是:"+list.size());
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBOpenHelper.closeAll(conn,ps,rs);//关闭相关操作
        return list;
    }
























    /**
     * 获取MySQL数据库单例类对象
     * */

    public static DBService_Record getDbService(){
        if(dbServiceLogin ==null){
            dbServiceLogin =new DBService_Record();
        }
        return dbServiceLogin;
    }
   /* public int updateUserData(String phone){
        int result=-1;
        if(!StringUtils.isEmpty(phone)){
            //获取链接数据库对象
            conn= DBOpenHelper.getConn();
            //MySQL 语句
            String sql="update user set state=? where phone=?";
            try {
                boolean closed=conn.isClosed();
                if(conn!=null&&(!closed)){
                    ps= (PreparedStatement) conn.prepareStatement(sql);
                    ps.setString(1,"1");//第一个参数state 一定要和上面SQL语句字段顺序一致
                    ps.setString(2,phone);//第二个参数 phone 一定要和上面SQL语句字段顺序一致
                    result=ps.executeUpdate();//返回1 执行成功
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        DBOpenHelper.closeAll(conn,ps);//关闭相关操作
        return result;
    }*/
   /* public int delUserData(String phone){
        int result=-1;
        if((!phone.isEmpty())&&(phone.isEmpty())){
            //获取链接数据库对象
            conn= DBOpenHelper.getConn();
            //MySQL 语句
            String sql="delete from user where phone=?";
            try {
                boolean closed=conn.isClosed();
                if((conn!=null)&&(!closed)){
                    ps= (PreparedStatement) conn.prepareStatement(sql);
                    ps.setString(1, phone);
                    result=ps.executeUpdate();//返回1 执行成功
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        DBOpenHelper.closeAll(conn,ps);//关闭相关操作
        return result;
    }*/

}