package Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import entity.api.Customer;
import entity.impl.CustomerImpl;

public class GuestDao {
	//1 success
	//2 already exist
	//3 user not exist
	//4 sql failed
	public static int addNewGuest(String guest,String master) {
		//check username exist in table customer
		DBconn.init();
		String sql=String.format("select * from customer where name='%s'", guest);
		ResultSet rSet=DBconn.selectSql(sql);		
		try {
			if(rSet.next()) {
				sql=String.format("INSERT INTO Recommand..guest (guest, master,state) VALUES ('%s', '%s','%b')", guest,master,true);
				int i=DBconn.addUpdDel(sql);
				if(i!=0) {
					return 1;
					
				}else {
					return 2;
				}
			}else {
				return 3;
			}
		} catch (SQLException e) {			
			e.printStackTrace();
			return 4;
		}finally {
			DBconn.closeConn();
		}
	}
	
	public static boolean deleteNewGuest(String guest,String master) {
		String sql=String.format("DELETE FROM Recommand..guest WHERE guest = '%s' and master='%s' and state=1 ", guest,master);
		DBconn.init();
		if(DBconn.addUpdDel(sql)!=1) {
			return false;
		}
		DBconn.closeConn();
		return true;		
	}
	
	public static boolean deleteOldGuest(String guest,String master) {
		String sql=String.format("DELETE FROM Recommand..guest WHERE guest = '%s' and master='%s' and state=0", guest,master);
		DBconn.init();
		if(DBconn.addUpdDel(sql)!=1) {
			return false;
		}
		return true;		
	}
	public static List<Customer>getNewGuest(String master){
		List<Customer> guestList=new LinkedList<Customer>();
		String sql=String.format("select name,sex,birthday from Recommand..guest as A join Recommand..customer as B on A.guest=B.name where master ='%s' and state =1", master);
		DBconn.init();
		ResultSet rSet=DBconn.selectSql(sql);
		try {
			while(rSet.next()) {
				Customer customer=new CustomerImpl();
				customer.setName(rSet.getString("name"));
				customer.setBirthday(rSet.getDate("birthday"));
				customer.setSex(rSet.getBoolean("sex"));
				guestList.add(customer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBconn.closeConn();
		}
		return guestList;
	}
	//return all guests of this master
	public static List<Customer>getGuestsOfMaster(String master){
		List<Customer>customers=new ArrayList<Customer>();
		String sql=String.format("select name, sex, birthday,height,weight from Recommand..customer  join (select guest from Recommand..guest where master='%s'and state = 1) as A on A.guest=customer.name", master);
		DBconn.init();
		ResultSet rSet=DBconn.selectSql(sql);
		try {
			while(rSet.next()) {
				Customer customer=new CustomerImpl();
				customer.setName(rSet.getString("name"));
				customer.setBirthday(rSet.getDate("birthday"));
				customer.setHeight(rSet.getInt("height"));
				customer.setWeight(rSet.getInt("weight"));
				customer.setSex(rSet.getBoolean("sex"));
				customers.add(customer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBconn.closeConn();
		}
		return customers;
	}
	
}
