package recommend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import Dao.CustomerFoodRankDao;
import Dao.FoodDao;
import entity.api.Customer;
import util.MapUtil;

public class GeneratedMenu {
	
private List<Customer>customers;
public GeneratedMenu(List<Customer>customers) {
	this.customers=customers;	
}
//return food's id list
public List<Integer> GeneratedMenu(){
	List<Integer>menuList=new ArrayList<Integer>();
	List<List< Double>> allRankList=new LinkedList<List< Double>>();
	for (Customer customer : customers) {
		allRankList.add(CustomerFoodRankDao.UserRankList(customer.getName()));
	}
	//稀疏性
	for(int i=0;i<customers.size();i++) {
		SparsityRemoval.Process(customers.get(i).getName(), allRankList.get(i));
	}
	//標準熱量
	int sCalorificValue =Calorific.getStandardCalorificValue(customers);
	//用戶喜愛值之和
	HashMap<Integer,Double> sumRank=new HashMap<Integer,Double>();
	for(int i=0;i<allRankList.get(i).size();i++) {
		double sum=0.0;
		for (List<Double> rankList : allRankList) {
			sum+=rankList.get(i);
		}
		sumRank.put(i, sum);
	}
	//從大到小排序
	MapUtil<Integer,Double> mapUtil=new MapUtil<Integer,Double>();
	TreeMap <Integer,Double>sortedSumRank=mapUtil.sortMap(sumRank);
	
	Map<Integer, Integer> calorificMap=FoodDao.getCalorificMap();
	int calorific=0;
	 Iterator<Integer> it = sortedSumRank.keySet().iterator();  
     while (it.hasNext()) { 
    	 Integer food_id=it.next();
    	 calorific+=calorificMap.get(food_id);
    	 if(calorific<sCalorificValue) {
    		 menuList.add(food_id);
    	 }else {
    		 break;
    	 }
     }
		
     return menuList;
}

}
