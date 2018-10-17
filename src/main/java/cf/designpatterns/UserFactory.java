package cf.designpatterns;

import java.io.InputStream;
import java.util.Properties;

public class UserFactory {

	public static IUserDao getUser() throws Exception {
		
		Properties properties=new Properties();
		InputStream resourceAsStream = UserFactory.class.getClassLoader().getResourceAsStream("factory.properties");
		properties.load(resourceAsStream);
		String property = properties.getProperty("implClass");
		IUserDao newInstance = (IUserDao) Class.forName(property).newInstance();
		resourceAsStream.close();
		return newInstance;
	}
}
