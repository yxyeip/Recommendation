package recommend;
/**
 * @author Ye xiuyun
 *
 */
import java.util.*;
public class Pearson {
	public static double pearson(List<Double> rate1, List<Double> rate2){
        double res = sumSub(rate1, rate2)/sqrtMulti(rate1, rate2);
        return res;
    }
	
    private static double mean(List<Double> list){
        double sum=0.0;
        for(Double s: list){
            sum += s;
        }
        double res = sum/list.size();
        return  res;
    }
    private static double sumSub(List<Double> l1, List<Double> l2){
        double sum = 0.0;
        double mean1 = mean(l1);
        double mean2 = mean(l2);
        for(int i = 0; i< l1.size(); i++){
            sum+= (l1.get(i)-mean1)*(l2.get(i)-mean2);
        }

        return sum;
    }
    private static double sqrtMulti(List<Double> l1, List<Double> l2){
        double res = 0.0;
        double sum1 = 0.0;
        double sum2 = 0.0;
        for (int i =0; i< l1.size(); i++){
            sum1+= (l1.get(i)-mean(l1))*(l1.get(i)-mean(l1));
            sum2 += (l2.get(i)-mean(l2))*(l2.get(i)-mean(l2));
        }
        res = Math.sqrt(sum1*sum2);
        return res;
    }

}
