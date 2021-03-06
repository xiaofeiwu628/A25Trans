package com.example.timemangertrans.Dao;


import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBOpenHelper {

    private static String driver = "com.mysql.jdbc.Driver";//MySQL 驱动
    private static String url = "jdbc:mysql://gz-cdb-k9a005sd.sql.tencentcdb.com:57824/timemanage?useUnicode=true&characterEncoding=utf8";//MYSQL数据库连接Url
    private static String user = "root";//用户名
    private static String password = "lh123456";//密码

    /**
     * 连接数据库
     * */

    public static java.sql.Connection getConn(){
        Connection conn = null;/**/
        try {
            Class.forName(driver);//获取MYSQL驱动，经测试可运行
            conn = (Connection) DriverManager.getConnection(url, user, password);//获取连接
            System.out.println("链接成功！！！");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("驱动加载失败！！！");
            System.out.println("驱动加载失败！！！");
            System.out.println("驱动加载失败！！！");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("连接失败！！！");
            System.out.println("连接失败！！！");
            System.out.println("连接失败！！！");
        }
        return conn;/*这里只是获取，用的话一定要检验是否空值！！！*/
    }

    /**
     * 关闭数据库
     * */

    public static void closeAll(Connection conn, PreparedStatement ps){
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 关闭数据库
     * */

    public static void closeAll(Connection conn, PreparedStatement ps, ResultSet rs){
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}