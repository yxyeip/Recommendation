/**
 * 
 */
package recommend;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import entity.api.Customer;

/**
 * @author Ye xiuyun
 *
 */
public class Calorific {
	
	public static int getStandardCalorificValue(List<Customer> customers) {
		//int calorificValue=0;
		double restingMetabolic=0;
		for (Customer customer : customers) {
			int age=getAge(customer.getBirthday());
			if(customer.getSex()) {//is male
				//男性静息代谢率：10 * 体重（kg）+ 6.25 * 身高（cm）- 5 * 年龄+5
				restingMetabolic +=10*customer.getHeight()+6.25*customer.getHeight()-5*age+5;
				
			}else{//is female
				//女性静息代谢率：10 * 体重（kg）+ 6.25 * 身高（cm）- 45 * 年龄-161
				restingMetabolic +=10*customer.getHeight()+6.25*customer.getHeight()-45*age-161;
			}
		}
		return (int) (restingMetabolic*1.375);
	}

	private static int getAge(Date birthDay) {
		// 获取当前系统时间
		Calendar cal = Calendar.getInstance();
		// 如果出生日期大于当前时间，则抛出异常
		if (cal.before(birthDay)) {
			throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
		}
		// 取出系统当前时间的年、月、日部分
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		// 将日期设置为出生日期
		cal.setTime(birthDay);
		// 取出出生日期的年、月、日部分
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
		// 当前年份与出生年份相减，初步计算年龄
		int age = yearNow - yearBirth;
		// 当前月份与出生日期的月份相比，如果月份小于出生月份，则年龄上减1，表示不满多少周岁
		if (monthNow <= monthBirth) {
			// 如果月份相等，在比较日期，如果当前日，小于出生日，也减1，表示不满多少周岁
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth)
					age--;
			} else {
				age--;
			}
		}
		return age;
	}
}
