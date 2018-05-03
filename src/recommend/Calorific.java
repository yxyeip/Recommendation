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
				//���Ծ�Ϣ��л�ʣ�10 * ���أ�kg��+ 6.25 * ��ߣ�cm��- 5 * ����+5
				restingMetabolic +=10*customer.getHeight()+6.25*customer.getHeight()-5*age+5;
				
			}else{//is female
				//Ů�Ծ�Ϣ��л�ʣ�10 * ���أ�kg��+ 6.25 * ��ߣ�cm��- 45 * ����-161
				restingMetabolic +=10*customer.getHeight()+6.25*customer.getHeight()-45*age-161;
			}
		}
		return (int) (restingMetabolic*1.375);
	}

	private static int getAge(Date birthDay) {
		// ��ȡ��ǰϵͳʱ��
		Calendar cal = Calendar.getInstance();
		// ����������ڴ��ڵ�ǰʱ�䣬���׳��쳣
		if (cal.before(birthDay)) {
			throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
		}
		// ȡ��ϵͳ��ǰʱ����ꡢ�¡��ղ���
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		// ����������Ϊ��������
		cal.setTime(birthDay);
		// ȡ���������ڵ��ꡢ�¡��ղ���
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
		// ��ǰ����������������������������
		int age = yearNow - yearBirth;
		// ��ǰ�·���������ڵ��·���ȣ�����·�С�ڳ����·ݣ��������ϼ�1����ʾ������������
		if (monthNow <= monthBirth) {
			// ����·���ȣ��ڱȽ����ڣ������ǰ�գ�С�ڳ����գ�Ҳ��1����ʾ������������
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
