package com.example.timemangertrans.Service;


import com.example.timemangertrans.Dao.DBOpenHelper;
import com.example.timemangertrans.Entienty.Recordmine;
import com.example.timemangertrans.Entienty.User;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DBService_User {

    public Connection conn=null; //打开数据库对象
    public PreparedStatement ps=null;//操作整合sql语句的对象
    public ResultSet rs=null;//查询结果的集合

    //DBService_User 对象
    public static DBService_User dbServiceLogin =null;


    public DBService_User(){

    }

    /**
     * 获取MySQL数据库单例类对象
     * */

    public static DBService_User getDbService(){
        if(dbServiceLogin ==null){
            dbServiceLogin =new DBService_User();
        }
        return dbServiceLogin;
    }
    /**
    *  登录检查
    **/
    public List<User> getAllUser(){/*总显示界面查询所有的记录，已通过测试*/
        //结果存放集合
        List<User> list=new ArrayList<User>();
        //MySQL 语句
        String sql="select * from user";
        //获取链接数据库对象
        conn= (Connection) DBOpenHelper.getConn();
        try {
            if(conn!=null&&(!conn.isClosed())){
                ps= (PreparedStatement) conn.prepareStatement(sql);
                if(ps!=null){
                    rs= (ResultSet) ps.executeQuery();
                    if(rs!=null){
                        while(rs.next()){
                            User u=new User();
                            u.setAccount(rs.getString("account"));
                            u.setPassword(rs.getString("password"));
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

    public Boolean getUserDatacheck(String account,String password){
        //结果存放集合
        List<User> list=new ArrayList<User>();
        //MySQL 语句
        //String sql="select * from user where account = ?";

        Boolean flag = false;
        //获取链接数据库对象
        conn= (Connection) DBOpenHelper.getConn();

        String sql="select * from user where account = ?";
        try {
            if(conn!=null&&(!conn.isClosed())){
                ps= (PreparedStatement) conn.prepareStatement(sql);
                if(ps!=null){
                    ps.setString(1,account);
                    rs= (ResultSet) ps.executeQuery();
                    if(rs!=null){
                        while(rs.next()){
                            if(rs.getString("password").equals(password)){
                                flag = true;
                               // System.out.println("flag = true");
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBOpenHelper.closeAll(conn,ps,rs);//关闭相关操作
      return flag;
    }



    /**
     *    查
     * */

    /*public List<User> getUserDataall(){
        //结果存放集合
        List<User> list=new ArrayList<User>();
        //MySQL 语句
        String sql="select * from user";
        //获取链接数据库对象
        conn= (Connection) DBOpenHelper.getConn();
        try {
            if(conn!=null&&(!conn.isClosed())){
                ps= (PreparedStatement) conn.prepareStatement(sql);
                if(ps!=null){
                    rs= (ResultSet) ps.executeQuery();
                    if(rs!=null){
                        while(rs.next()){
                            User u=new User();
                            u.setAccount(rs.getString("account"));
                            u.setPassword(rs.getString("password"));
                            System.out.println(u.getAccount());
                            System.out.println(u.getPassword());
                            list.add(u);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBOpenHelper.closeAll(conn,ps,rs);//关闭相关操作
        return list;
    }*/

    /**
     * 修改数据库中某个对象的状态   改
     * */

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

    /**
     * 批量向数据库插入数据   增
     * */

    public int insertUserData(String account,String password){
        int result=-1;
        if(true){
            //获取链接数据库对象
            conn= (Connection) DBOpenHelper.getConn();
            //MySQL 语句
            String sql="INSERT INTO user (account,password) VALUES (?,?)";
            try {

                if((conn!=null)&&(!conn.isClosed())){

                        ps= (PreparedStatement) conn.prepareStatement(sql);

                        System.out.println("in-"+account);

                        System.out.println("in-"+password);
                        ps.setString(1,account);//第一个参数 name 规则同上
                        ps.setString(2,password);//第二个参数 phone 规则同上
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

    public int RecordU(String account,String password){/**/
        int result=-1;
        if(true){
            //获取链接数据库对象
            conn= (Connection) DBOpenHelper.getConn();
            //MySQL 语句
            String sql="UPDATE `timemanage`.`user` SET `password` = ? WHERE  `account` = ? ";
            try {
                if((conn!=null)&&(!conn.isClosed())){

                    ps= (PreparedStatement) conn.prepareStatement(sql);
                    ps.setString(1,password);
                    ps.setString(2,account);
                    result=ps.executeUpdate();//返回1 执行成功
                    System.out.println("upbackaaa="+result);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        DBOpenHelper.closeAll(conn,ps);//关闭相关操作
        return result;
    }

    public int deluser(String account){
        int result=-1;
        if(true){
            //获取链接数据库对象
            conn= (Connection) DBOpenHelper.getConn();
            //MySQL 语句
            String sql="delete from user where account= ? ";
            try {
                if((conn!=null)&&(!conn.isClosed())){

                    ps= (PreparedStatement) conn.prepareStatement(sql);
                    ps.setString(1,account);//第一个参数 name 规则同上
                    result=ps.executeUpdate();//返回1 执行成功
                    System.out.println("del="+result);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            String sql2="delete from `record` where rmaster= ? ";
            try {
                if((conn!=null)&&(!conn.isClosed())){

                    ps= (PreparedStatement) conn.prepareStatement(sql2);
                    ps.setString(1,account);//第一个参数 name 规则同上
                    result=ps.executeUpdate();//返回1 执行成功
                    System.out.println("delrec="+result);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        DBOpenHelper.closeAll(conn,ps);//关闭相关操作
        return result;
    }

    /**
     * 删除数据  删
     * */

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