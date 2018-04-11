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
            System.out.println("init [SQLserver���������ʼ��ʧ�ܣ�]");  
            e.printStackTrace();  
        }  
    }
    //��ɾ��
    public static int addUpdDel(String sql){  
        int i = 0;  
        try {  
            PreparedStatement ps =  conn.prepareStatement(sql);  
            i =  ps.executeUpdate();  
        } catch (SQLException e) {  
            System.out.println("sql���ݿ���ɾ���쳣");  
            e.printStackTrace();  
        }            
        return i;  
    } 
    //��
    public static ResultSet selectSql(String sql){  
        try {
        	if(conn!=null) {
            ps =  conn.prepareStatement(sql);  
            rs =  ps.executeQuery();
        	}else {
        		System.out.println("connΪ�գ��������ݿ�����Ƿ���");  
        	}
        } catch (SQLException e) {  
            System.out.println("sql���ݿ��ѯ�쳣");  
            e.printStackTrace();  
        }  
        return rs;  
    }  
    public static void closeConn(){  
        try {  
            conn.close();  
        } catch (SQLException e) {  
            System.out.println("sql���ݿ�ر��쳣");  
            e.printStackTrace();  
        }  
    }  
}
