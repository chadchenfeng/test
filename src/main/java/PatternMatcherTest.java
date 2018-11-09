import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternMatcherTest {

	public static void main(String[] args) {
		/*String sourceString="名字:{name},年龄:{age},学校:{school}";
		Map<String,String> param=new HashMap<String,String>();
		param.put("name", "cf");
		param.put("age", "28");
		param.put("school", "江西师大");
		System.out.println(replaceWithMap(sourceString,param));*/
		
		String s=", if(a.scan_count is null,0,a.scan_count,(date,lastdate)) as scan_count , if(a.scan_user is null,0,a.scan_user) as scan_user";
		System.out.println(replaceString(s,""));
	}
	
	
	public static String replaceWithMap(String sourceString, Map<String, String> param) {
        if(null==sourceString || "".equals(sourceString) || param.isEmpty()) {
        	return sourceString;
        }

        Pattern pattern = Pattern.compile("\\{(.*?)\\}");
        Matcher matcher;
        String targetString = sourceString;
        matcher = pattern.matcher(sourceString);
        while (matcher.find()) {
            try {
                String key = matcher.group();
                String keyclone = key.substring(1, key.length() - 1).trim();
                Object value = param.get(keyclone);
                if (value != null) {
                    targetString = targetString.replace(key, value.toString());
                }
            } catch (Exception e) {
                throw new RuntimeException("String formatter failed", e);
            }
        }
        return targetString;
    }
	
	public static String replaceString(String sourceString,String replaceStr) {
		if(null==sourceString || "".equals(sourceString)) {
        	return sourceString;
        }
		 Pattern pattern = Pattern.compile("\\(.*\\)");
		 Matcher matcher;
	     String targetString = sourceString;
	     matcher = pattern.matcher(sourceString);
	     while(matcher.find()) {
	    	 String key = matcher.group();
	    	 targetString=targetString.replace(key.substring(1, key.length() - 1), replaceStr);
	     }
	     return targetString;
	     
	}
}
