

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LanLongTest {

	private double Ea=6378137;//   赤道半径  
	private double Eb=6356725;//   极半径 
	
	public static void main(String[] args) {
		LanLongTest test=new LanLongTest();
		List list=test.getPostions(114.12873077,22.54587746,1000,5);
		System.out.println(list.toString());
	}
	/*
	 * 获取某个坐标dis内的count个随机坐标
	 * @param lon,lat 表示当前的经纬度
	 * @param dis 表示多少米之内的
	 * @param count 表示获取多少个随机坐标
	 */
	public List<Map<String, Double>> getPostions(double lon,double lat,double dis,int count){
		List<Map<String, Double>> list=new ArrayList<Map<String, Double>>();
		while(list.size()<count) {
			Map<String, Double> map=GetRectRange(lon,lat,dis);
			double newlon=getRandom((Double)map.get("minLongitude"),(Double)map.get("maxLongitude"));
			double newlat=getRandom((Double)map.get("minLatitude"),(Double)map.get("maxLatitude"));
			double _dis=GetDistance(lon,lat,newlon,newlat);
			if(_dis<=dis) {
				Map<String, Double> pos=new HashMap<String,Double>();
				pos.put("lon", newlon);
				pos.put("lat", newlat);
				list.add(pos);
			}
		}
		return list;
	}
	
	private  double getRandom(double start,double end) {
		return Math.random()*(end-start)+start;
	}


    private  Map<String, Double> GetlatLon(double LAT, double LON, double distance, double angle)

    {

        double dx = distance  * Math.sin(angle * Math.PI / 180.0);

        double dy = distance  * Math.cos(angle * Math.PI / 180.0);

        double ec = Eb + (Ea-Eb) * (90.0 - LAT) / 90.0;

        double ed = ec * Math.cos(LAT * Math.PI / 180);

        double newLon = (dx / ed + LON * Math.PI / 180.0) * 180.0 / Math.PI;

        double newLat = (dy / ec + LAT * Math.PI / 180.0) * 180.0 / Math.PI;
        Map<String, Double> retmap=new HashMap<String, Double>();
        retmap.put("lon", newLon);
        retmap.put("lat", newLat);
        return retmap;
    }

    public  Map<String,Double> GetRectRange(double centorLogitude,double centorlatitude,  double distance)

    {
        Map<String,Double> maxLatitude=GetlatLon(centorlatitude, centorLogitude, distance, 0);

        Map<String,Double> minLatitude=GetlatLon(centorlatitude, centorLogitude, distance, 180);

        Map<String,Double> maxLongitude =GetlatLon(centorlatitude, centorLogitude, distance, 90);

        Map<String,Double> minLongitude =GetlatLon(centorlatitude, centorLogitude, distance, 270);
        
        Map<String,Double> retmap=new HashMap<String,Double>();
        retmap.put("maxLatitude", (Double) maxLatitude.get("lat"));
        retmap.put("minLatitude", (Double) minLatitude.get("lat"));
        retmap.put("maxLongitude", (Double) maxLongitude.get("lon"));
        retmap.put("minLongitude", (Double) minLongitude.get("lon"));
        return retmap;

    }
    
    
    
    private static double EARTH_RADIUS = 6378.137;  
    //private static double EARTH_RADIUS = 6371.393;  
    private static double rad(double d)  
    {  
       return d * Math.PI / 180.0;  
    }  
    /** 
     * 计算两个经纬度之间的距离 
     * @param lat1 
     * @param lng1 
     * @param lat2 
     * @param lng2 
     * @return 
     */ 
    public static double GetDistance( double lng1,double lat1, double lng2, double lat2)  
    {  
       double radLat1 = rad(lat1);  
       double radLat2 = rad(lat2);  
       double a = radLat1 - radLat2;      
       double b = rad(lng1) - rad(lng2);  
       double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +   
        Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));  
       s = s * EARTH_RADIUS;  
       s = Math.round(s * 1000);  
       return s;  
    }  

}
