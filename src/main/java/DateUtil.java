import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class DateUtil {

	public static void main(String[] args) throws ParseException {
		/*System.out.println(new Date().getTime()+"----"+System.currentTimeMillis());
		
		System.out.println(convertTimeToLong("2018-12-12 12:12:12"));
		System.out.println(TimeDifference(convertTimeToLong(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())),convertTimeToLong("2018-12-12 12:12:12")));
	*/
		Map<String, String> getMonthDayByWeek = GetMonthDayByWeek("20181116");
		System.out.println(getMonthDayByWeek.toString());
		
		System.out.println(getLastDayOfWeek(2018,49));
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
	
	/*
	 * 获取某个月的周期（按周）起始截止日期
	 * 思路：
	 * 起始日期：取得某月的第一天，根据这一天获得这一天属于第几周A，获取A的第一天A_1，判断A_1是否属于这个月份，若属于，则起始日期为A_1,若不属于，则需要取下一周的第一天
	 * 截止日期：获取某月最后一天所在周几N，然后对最后一天再加7-N天。
	 */
	public static Map<String,String> GetMonthDayByWeek(String month) throws ParseException {
		Map<String,String> map=new HashMap<>();
		String firstdayofmonth=month.substring(0,6)+"01";
		int year=Integer.valueOf(month.substring(0,4));
		String months=month.substring(4,6);
		String start="";
		String end="";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		Date firstdate = simpleDateFormat.parse(firstdayofmonth);
		int weekOfYear = getWeekOfYear(firstdate);
		Date firstDayOfWeek = getFirstDayOfWeek(year,weekOfYear);
		String day = simpleDateFormat.format(firstDayOfWeek);
		String substring = day.substring(4, 6);
		if(substring.equals(months)) {
			start=day;
		}else {
			start=simpleDateFormat.format(getFirstDayOfWeek(year,weekOfYear+1));
		}
		
		Date monthLastDay = getMonthLastDay(year,Integer.valueOf(months));
		
		int dayofWeek = getDayofWeek(monthLastDay);
		Date enddate = addDay(monthLastDay,7-dayofWeek);
		end=simpleDateFormat.format(enddate);
		map.put("startdate", start);
		map.put("enddate", end);
		return map;
	}
	
	/**
	 * 获取某月的最后一天
	 */
	public static Date getMonthLastDay(int year,int month) {
		Calendar calendar=Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DATE));
		return calendar.getTime();
	}

	/**
	 * 获取某一天属于第几周
	 * @param date
	 * @return
	 */
	 public static int getWeekOfYear(final Date date) {
	    	final Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        int i = cal.get(Calendar.WEEK_OF_YEAR);
	        return i;
	    }
	
	/**
     * 获取某周得最后一天
     * @param year 年
     * @param week 周
     * @return
     */
    public static Date getLastDayOfWeek(int year, int week){
        Calendar c = new GregorianCalendar();
        System.out.println("-------"+c.getTime());
        c.set(Calendar.YEAR, year);
        c.set(Calendar.WEEK_OF_YEAR,  week);
        c.setFirstDayOfWeek(Calendar.MONDAY);
        System.out.println("========"+c.getTime());
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 5);
        c.set(Calendar.HOUR, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        System.out.println("++++++++"+c.getTime());

        return c.getTime();
    }
    
    /**
     * 获取某周得第一天
     * @param year 年
     * @param week 周
     * @return
     */
    public static Date getFirstDayOfWeek(int year, int week){
        Calendar c = new GregorianCalendar();

        c.set(Calendar.YEAR, year);
        c.set(Calendar.WEEK_OF_YEAR,  week);
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        return c.getTime();
    }
    /**
     * 日期加N天
     */
    public static Date addDay(Date dt, int day) {
		Calendar time=Calendar.getInstance();
		time.setTime(dt);
		time.add(Calendar.DATE, day);
		return time.getTime();
	}
    
    /**
     * 根据一个日期获取所在的周几
     */
    public static int getDayofWeek(Date date) {
    	Calendar c=Calendar.getInstance();
    	c.setTime(date);
    	int dayofweek=0;
    	if(c.get(Calendar.DAY_OF_WEEK)==1) {
    		dayofweek=7;
    	}else {
    		dayofweek=c.get(Calendar.DAY_OF_WEEK)-1;
    	}
    	return dayofweek;
    }
}
