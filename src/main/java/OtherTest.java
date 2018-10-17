import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OtherTest {

	public static int count=0;
	public static void main(String[] args) {

		/*String sql="select bannerpicture_id,goods_id,picture from $(mpt_db).mpt_goods_bannerpicture where goods_id in ('1','2') ORDER BY sort_value DESC";
		String newSql=sql.replaceAll("\\$\\(mpt_db\\)", "mpt_sy");	
		System.out.println(newSql);*/
		/*long d=digui(10000);
			System.out.println(d);
			System.out.println(count);*/
		
		/*long d=digui(10);
		System.out.println(d);
		
		int a=10001;
		System.out.println(Double.parseDouble(String.valueOf(a))/100);
		
		String t="12.3";
		System.out.println(Double.parseDouble(String.valueOf(t))*100);
		
		System.out.println("=========");
		String b=new StringBuilder("b").append("b").toString();
		String string=new StringBuilder("fal").append("se1").toString();
		System.out.println(b.intern()==b);
		System.out.println(string.intern()==string);
		
		
		String str1 = new String("SEU")+ new String("Calvin");   
		System.out.println(str1 == "SEUCalvin");
		System.out.println(str1.intern() == str1); 
		System.out.println("=========");
		String str4 =new StringBuilder("").append("结果为false").toString();
		System.out.println(str4.intern() == str4);
		String str5 =new StringBuffer("5、结果").append("为true").toString();
		System.out.println(str5.intern() == str5);
*/
		
		/*System.out.println("---------------------hashCode------------");
		
		System.out.println("123的hashcode："+"1".hashCode());
		System.out.println(5>>1);
		
		
		
		Map<String, String> map = new LinkedHashMap<String, String>(16,0.75f,true);
	    map.put("apple", "苹果");
	    map.put("watermelon", "西瓜");
	    map.put("banana", "香蕉");
	    map.put("peach", "桃子");

	    map.get("banana");
	    map.get("apple");

	    Iterator iter = map.entrySet().iterator();
	    while (iter.hasNext()) {
	        Map.Entry entry = (Map.Entry) iter.next();
	        System.out.println(entry.getKey() + "=" + entry.getValue());
	    }*/
		List<String> list=new LinkedList<String>();
		list.add("1");
	}
	
	public static long digui(long l){
			if(l==1) {
				System.out.println(l);
				return 1;
			}else {
				long a= l+digui(l-1);
				for(long i=1;i<=l;i++) {
					System.out.print(i+" ");
				}
				System.out.println("===="+a);
				return a;
			}
	}
}
