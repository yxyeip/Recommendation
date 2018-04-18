package Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import entity.impl.CustomerFoodRate;

public class CustomerFoodRateDao {
private String userName;
private String foodName;
private int rate;
public CustomerFoodRateDao(String userName,String foodName,int rate) {
	this.userName=userName;
	this.foodName=foodName;
	this.rate=rate;
}
public CustomerFoodRateDao() {
}
public boolean insertOrUpdate() {
	//DBconn dBconn=new DBconn();
	DBconn.init();
	String selectSql=String.format("select top 1 * from Recommand..evaluation where user_name='%s' and food_name='%s'", userName,foodName);
	ResultSet rSet=DBconn.selectSql(selectSql);
	try {
		if(rSet.next()) {//update
			String updateSql=String.format("UPDATE Recommand..evaluation SET rate = %d WHERE user_name='%s' and food_name='%s'", rate,userName,foodName);
			if(DBconn.addUpdDel(updateSql)==0)
				return false;	
		}else {//insert
			String insertSql=String.format("INSERT INTO Recommand..evaluation (user_name, food_name,rate) VALUES ('%s', '%s',%d)", userName,foodName,rate);
			if(DBconn.addUpdDel(insertSql)==0)
				return false;	
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}finally {
		DBconn.closeConn();
	}
	return true;			
}
public static List<CustomerFoodRate> selectCustomerFoodRate(String userName,String kind) {
	List<CustomerFoodRate> customerFoodRates=new LinkedList<CustomerFoodRate>();
	String sql="select name,price,description,rate from(select * from Recommand..food where kinds='"+kind+"')as A left join (select * from Recommand..evaluation where user_name='"+userName+"')as B on A.name=B.food_name";
	DBconn.init();
	ResultSet rSet=DBconn.selectSql(sql);
	try {
		while(rSet.next()) {
			CustomerFoodRate customerFoodRate=new CustomerFoodRate(rSet.getString("name"),rSet.getBigDecimal("price"), rSet.getString("description"),rSet.getInt("rate"));
			customerFoodRates.add(customerFoodRate);
		}
		
	} catch (SQLException e) {
		e.printStackTrace();
	}finally {
		DBconn.closeConn();
	}
	return customerFoodRates;
}
}
