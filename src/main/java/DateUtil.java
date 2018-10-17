import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static void main(String[] args) {
		System.out.println(new Date().getTime()+"----"+System.currentTimeMillis());
		
		System.out.println(convertTimeToLong("2018-12-12 12:12:12"));
		System.out.println(TimeDifference(convertTimeToLong(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())),convertTimeToLong("2018-12-12 12:12:12")));
	}
	public static Long convertTimeToLong(String time) {  
	    Date date = null;  
	    try {  
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	        date = sdf.parse(time);  
	        return date.getTime();  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	        return 0L;  
	    }  
	} 
	
	
	public static String TimeDifference(long start, long end) {
		 
		  long between = end - start;
		  long day = between / (24 * 60 * 60 * 1000);
		  long hour = (between / (60 * 60 * 1000) - day * 24);
		  long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
		  long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		  long ms = (between - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000
		    - min * 60 * 1000 - s * 1000);
		  String timeDifference = day + "天" + hour + "小时" + min + "分" + s + "秒" + ms
		    + "毫秒";
		  return timeDifference;
		 }
}
