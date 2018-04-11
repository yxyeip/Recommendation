package Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.sun.org.apache.bcel.internal.generic.Select;
import com.sun.org.apache.xpath.internal.operations.Bool;

import entity.api.Customer;
import util.*;
public class CustomerDao {
	//1成功0失败2用户不存在
	public static int login(String name, String pwd) {
		String sql=String.format("select top 1 * from Recommand..customer where name = '%s'", name);	
		DBconn.init();
		ResultSet resultSet=DBconn.selectSql(sql);
		if(resultSet!=null){
			String password = "";
			try {
				 if (resultSet.next()){
					 password = resultSet.getString("password");
				 }
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				DBconn.closeConn();
			}
			if(password.equals(PasswordUtil.encript(pwd))) {
				return 1;
			}else {
				return 0;
			}
		}else {
		return 2;
		}
	}

	public static boolean register(Customer user) {
		String sql=String.format("Select * from Recommand..customer where name = '%s'", user.getName());
		DBconn.init();
		try {
			if(DBconn.selectSql(sql)==null)
			{
				//插入
				sql=String.format("insert into Recommand..Customer(name,password,sex,birthday,height,weight) values ('%s','%s',%b,%tx,%d,%d)", user.getName(),user.getPassword());
				if(DBconn.addUpdDel(sql)==1) return true;
			}
		} catch (Exception e) {
			System.out.println("插入出错 ");
			e.printStackTrace();
		}finally {
			DBconn.closeConn();
		}	
		return false;		
	}

	public List<Customer> getUserAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}
/*
	private int Id;
	private String name;
	private String password;
	private Boolean sex;
	private Date birthday;
	private int weight;
	private int height;*/

	public static boolean update(String name, String pwd, Boolean sex,Date birthday, int height, int weight){
		String sql=String.format("Select * from Recommand..customer where name = %s", name);
		DBconn.init();
		if(DBconn.selectSql(sql)!=null)
		{
			sql=String.format("update Recommand..customer set password='%s' sex=%b birthday=%tx height =%d weight =&d where name=%s",pwd,sex,birthday,height,weight);
			if(DBconn.addUpdDel(sql)==1)
			return true;
		}
		return false;
	}
	
}
