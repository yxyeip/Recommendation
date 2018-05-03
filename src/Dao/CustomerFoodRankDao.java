package Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import entity.impl.CustomerFoodRank;

public class CustomerFoodRankDao {
private String userName;
private int foodId;
private int rank;
private int foodNumber=336;
public CustomerFoodRankDao(String userName,int foodId,int rank) {
	this.userName=userName;
	this.foodId=foodId;
	this.rank=rank;
}
public CustomerFoodRankDao() {
}
public boolean insertOrUpdate() {
	//DBconn dBconn=new DBconn();
	DBconn.init();
	String selectSql=String.format("select top 1 * from Recommand..evaluation where user_name='%s' and food_id='%d'", userName,foodId);
	ResultSet rSet=DBconn.selectSql(selectSql);
	try {
		if(rSet.next()) {//update
			String updateSql=String.format("UPDATE Recommand..evaluation SET rank = %d WHERE user_name='%s' and food_id='%d'", rank,userName,foodId);
			if(DBconn.addUpdDel(updateSql)==0)
				return false;	
		}else {//insert
			String insertSql=String.format("INSERT INTO Recommand..evaluation (user_name, food_id,rank) VALUES ('%s', '%d',%d)", userName,foodId,rank);
			if(DBconn.addUpdDel(insertSql)==0)
				return false;	
		}
	} catch (SQLException e) {
		e.printStackTrace();
		return false;
	}finally {
		DBconn.closeConn();
	}
	return true;			
}
public static List<CustomerFoodRank> selectCustomerFoodRank(String userName,String kind) {
	List<CustomerFoodRank> customerFoodRanks=new LinkedList<CustomerFoodRank>();
	String sql="select name,price,description,rank,id from(select * from Recommand..food where kinds='"+kind+"')as A left join (select * from Recommand..evaluation where user_name='"+userName+"')as B on A.id=B.food_id";
	DBconn.init();
	ResultSet rSet=DBconn.selectSql(sql);
	try {
		while(rSet.next()) {
			CustomerFoodRank customerFoodRank=new CustomerFoodRank(rSet.getString("name"),rSet.getBigDecimal("price"), rSet.getString("description"),rSet.getInt("rank"),rSet.getInt("id"));
			customerFoodRanks.add(customerFoodRank);
		}
		
	} catch (SQLException e) {
		e.printStackTrace();
	}finally {
		DBconn.closeConn();
	}
	return customerFoodRanks;
}
public static List<Double> UserRankList(String userName){
	List<Double> rankList=new LinkedList<Double>();
	DBconn.init();
	String sql=String.format("select * from Recommand..evaluation where user_name='%s' order by food_id", userName);
	ResultSet rSet=DBconn.selectSql(sql);
	int i=0;
	try {
		while(rSet.next()) {
			if(rSet.getInt("food_id")>i) {//Ã»ÓÐÆÀ·Ö
				rankList.add(0.0);				
			}
			rankList.add((double) rSet.getInt("rank"));
			i++;
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}finally {
		DBconn.closeConn();
	}
	
	return rankList;
	
}

}
