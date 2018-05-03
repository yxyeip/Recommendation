package Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import entity.api.Food;
import entity.impl.FoodImpl;

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
}
