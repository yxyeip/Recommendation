package recommend;
/**
 * @author Ye xiuyun
 *
 */
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import Dao.CustomerDao;
import Dao.CustomerFoodRankDao;
import util.MapUtil;

public class SparsityRemoval {

	
	public static void Process( String userName,List<Double> ranklist1) {
		//List<Double> ranklist1=CustomerFoodRankDao.UserRankList(userName);
		
		CBR(userName,ranklist1);
		AverageFilling(ranklist1);
	}
	
	private static void CBR(String userName,List<Double> ranklist1) {
		// 寻找前K个相似的用户
		TreeMap <String ,Double> familiar= kfamiliarCustomer(userName,ranklist1);
		//get top K familiar's rank 
		TreeMap <String,List<Double>>familiarRank = new TreeMap <String,List<Double>>();
		for (String name : familiar.keySet()) {
			familiarRank.put(name, CustomerFoodRankDao.UserRankList(userName));
		}		
		//change UserRankList's value according by k familiar's rank
		
		for (int i=0;i<ranklist1.size();i++) {
			Double rank=ranklist1.get(i);
			if(rank>-0.000001 && rank<+0.000001) {
				ranklist1.set(i, GetEstimateValue(familiar,familiarRank,i));
			}		
		}
	}
	
	private static void AverageFilling(List<Double> ranklist1) {		
		Double avg=0.0;
		for (Double rank : ranklist1) {
		avg+=rank;
		}
		avg=avg/ranklist1.size();
		for (Double rank : ranklist1) {
			if(rank>-0.000001 && rank<+0.000001) {
				rank=avg;
			}
		}
	}
	
	private static TreeMap<String, Double> kfamiliarCustomer(String userName,List<Double> ranklist1) {
		HashMap<String, Double> userFamiliar=new HashMap<String,Double>();		
		List<String>userNames=CustomerDao.getAllCustomerNames();
		for (String name : userNames) {
			List<Double> ranklist2=CustomerFoodRankDao.UserRankList(name);
			//calculate 
			Double similarity=Pearson.pearson(ranklist1, ranklist2);
			userFamiliar.put(name, similarity);
		}
		MapUtil<String, Double> mapUtil=new MapUtil<String, Double>();
		//get top 5 familiar customers
		TreeMap<String, Double> topK=mapUtil.getTopKNode(userFamiliar, 5);		
		return topK;
	}
	
	private static Double GetEstimateValue(TreeMap <String ,Double> familiar,TreeMap <String,List<Double>>familiarRank,int index) {
		Double estimateValue=0.0;
		Double fenzi=0.0;
		Double fenmu=0.0;
		for (String name : familiar.keySet()) {
			fenzi+=familiar.get(name)*familiarRank.get(name).get(index);
			fenmu+=familiar.get(name);
		}
		estimateValue=fenzi/fenmu;
		return estimateValue;
	}	
}
