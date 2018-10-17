package cf.reflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyTest {

	public static void main(String[] args) {
		IUserDao user=new UserDaoImpl();
		/*InvocationHandler handel=new ProxyUserDao(user);
		IUserDao newProxyInstance = (IUserDao) Proxy.newProxyInstance(user.getClass().getClassLoader()
				,user.getClass().getInterfaces(), handel);*/
		//Class<?>[] interfaces = IUserDao.class.getInterfaces();
		//IUserDao newProxyInstance = (IUserDao) new ProxyUserDao(user).ProxyIntance();
		IUserDao newProxyInstance = new ProxyUserDao(user).ProxyIntance(IUserDao.class);
		Class<?>[] interfaces = user.getClass().getInterfaces();
		for(Class<?> inteface:interfaces) {
			System.out.println("==="+inteface.getName());
		}
		newProxyInstance.queryInfo("chenfeng");
		newProxyInstance.updateInfo("czh");
	}
}
