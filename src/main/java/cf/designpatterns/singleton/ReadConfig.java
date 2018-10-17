package cf.designpatterns.singleton;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadConfig {

	private static ReadConfig instance=new ReadConfig();
	private static ThreadLocal<String> param;
	private ReadConfig() {
		try {
			readFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static ReadConfig getIntance(int i) {
		System.out.println("===="+param.get());
		String t=param.get()+"-"+i;
		param.set(t);
		return instance;
	}
	

	private void readFile() throws IOException {
		Properties p=new Properties();
		InputStream in = ReadConfig.class.getClassLoader().getResourceAsStream("factory.properties");
		System.out.println("读了一次文件");
		try {
			p.load(in);
			String property = p.getProperty("implClass");
			param=new ThreadLocal<String>();
			ReadConfig.param.set(property);
		} catch (IOException e) {
			e.printStackTrace();
		}
		in.close();
	}
	
	public String getParam() {
		return this.param.get();
	}
	
}
