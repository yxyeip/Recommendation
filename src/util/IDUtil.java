package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IDUtil {
	public static  String generateMenuID(long userid) {
		SimpleDateFormat sdf =   new SimpleDateFormat( "YYYYMMddHHmmss" ); 
		String date = sdf.format(new Date()); 
		date=date.substring(2, date.length()-2);
		String userID=String.valueOf(userid%1000000);
		int l=userID.length();
		for(int i=1;i<=6-l;i++) {
			userID="0"+userID;
		}
		return date+userID;
	}

}
