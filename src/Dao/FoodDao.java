package Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import entity.api.Customer;
import entity.api.Food;
import entity.impl.FoodImpl;
import entity.impl.OrderItemImpl;

public class FoodDao {
	public static List<FoodImpl> getFoodListByKind(String kinds){
		List<FoodImpl> allFood=new LinkedList<FoodImpl>();
		String sql=String.format("select * from Recommand..food where kinds='%s'", kinds);
		DBconn.init();
		ResultSet rSet=DBconn.selectSql(sql);
		try {
			while(rSet.next()) {
				FoodImpl food=new FoodImpl(kinds,rSet.getString("name"),
						rSet.getBigDecimal("price"),rSet.getString("description"));
				allFood.add(food);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allFood;
	}
	public static Map<Integer, Integer> getCalorificMap(){
		Map<Integer, Integer> calorificMap=new HashMap<Integer, Integer>();
		String sql="select id,heat from Recommand..food";
		DBconn.init();
		ResultSet rSet=DBconn.selectSql(sql);
		try {
			rSet=DBconn.selectSql(sql);
			while(rSet.next()) {
				calorificMap.put(rSet.getInt("id"), rSet.getInt("heat"));
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBconn.closeConn();
		}
		return calorificMap;
	}
	
	public static List<Food> getFoodList(List<Integer>foodIdList){
		List<Food>foods=new ArrayList<Food>();
		if(foodIdList==null)
			return null;
		if(foodIdList.size()==0)
			return null;
		String sql=String.format("select * from Recommand..food where id in(%s)", foodIdList.toString().substring(1,foodIdList.toString().length()-1));
		DBconn.init();
		ResultSet resultSet=DBconn.selectSql(sql);
		try {
			while(resultSet.next()) {
				Food food=new FoodImpl();
				food.setId(resultSet.getInt("id"));
				food.setName(resultSet.getString("name"));
				food.setPrice(resultSet.getBigDecimal("price"));
				food.setDescription(resultSet.getString("description"));
				foods.add(food);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBconn.closeConn();
		}
		return foods;
		
	}
	
	public static  Food getFoodById(int id) {
		Food food=new FoodImpl();
		String sql=String.format("select * from Recommand..food where id= %d", id);
		DBconn.init();
		ResultSet rSet=DBconn.selectSql(sql);
		try {
			while(rSet.next()) {
				food.setId(rSet.getInt("id"));
				food.setName(rSet.getString("name"));
				food.setPrice(rSet.getBigDecimal("price"));
				food.setDescription(rSet.getString("description"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBconn.closeConn();
		}
		return food;
	}
}
