import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MapToJsonTest {

	public static void main(String[] args) {
		Map<String,String> map=new HashMap<String,String>();
		map.put("1", "chenfeng");
		map.put("2", "chenjiao");
		map.put("3", "chenzh");
		System.out.println(map.toString());
		
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		list.add(map);
		System.out.println(list.toString());
	}
}
