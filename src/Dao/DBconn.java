package Dao;

import java.sql.*;

public class DBconn {
	static String url = "jdbc:sqlserver://Lenovo-PC:1433;databaseName=Recommand";   
    static String username = "sa";   
    static String password = "123";   
    public static Connection  conn = null;  
    static ResultSet rs = null;  
    static PreparedStatement ps =null;  
    public static void init(){  
        try {  
        	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
            conn = DriverManager.getConnection(url,username,password);  
        } catch (Exception e) {  
            System.out.println("init [SQLserver驱动程序初始化失败！]");  
            e.printStackTrace();  
        }  
    }
    //增删改
    public static int addUpdDel(String sql){  
        int i = 0;  
        try {  
            PreparedStatement ps =  conn.prepareStatement(sql);  
            i =  ps.executeUpdate();  
        } catch (SQLException e) {  
            System.out.println("sql数据库增删改异常");  
            e.printStackTrace();  
        }            
        return i;  
    } 
    //查
    public static ResultSet selectSql(String sql){  
        try {
        	if(conn!=null) {
            ps =  conn.prepareStatement(sql);  
            rs =  ps.executeQuery();
        	}else {
        		System.out.println("conn为空！请检查数据库服务是否开启");  
        	}
        } catch (SQLException e) {  
            System.out.println("sql数据库查询异常");  
            e.printStackTrace();  
        }  
        return rs;  
    }  
    public static void closeConn(){  
        try {  
            conn.close();  
        } catch (SQLException e) {  
            System.out.println("sql数据库关闭异常");  
            e.printStackTrace();  
        }  
    }  
}
