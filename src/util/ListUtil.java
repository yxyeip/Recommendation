package util;

import java.util.ArrayList;
import java.util.Collections;


public class ListUtil {
	public static ArrayList<Double> getTopK(ArrayList<Double> sumRankList,int k){
		
		Collections.sort(sumRankList);
		return (ArrayList<Double>) sumRankList.subList(0, k);
	}

}
