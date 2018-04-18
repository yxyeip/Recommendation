package Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

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
}
