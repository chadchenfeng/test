package cf.designpatterns.singleton;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum SingletonEnum {

	intance;
	
	private static String param;
	
	public static SingletonEnum getInstance() {
		return intance;
	}
	
	public String getparam() {
		return param;
	}
	
	private SingletonEnum() {
		try {
			readFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void readFile() throws IOException {
		Properties p=new Properties();
		InputStream in = ReadConfig.class.getClassLoader().getResourceAsStream("factory.properties");
		System.out.println("读了一次文件");
		try {
			p.load(in);
			String property = p.getProperty("implClass");
			param=property;
		} catch (IOException e) {
			e.printStackTrace();
		}
		in.close();
	}
}
