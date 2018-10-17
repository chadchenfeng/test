package cf.reflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyUserDao implements InvocationHandler {

	private Object target;
	public ProxyUserDao(Object obj) {
		this.target=obj;
	}
	
	public <T> T ProxyIntance(Class<T> T) {
	       T t= (T) Proxy.newProxyInstance(target.getClass().getClassLoader()
				,target.getClass().getInterfaces(), this);
	       return t;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		boolean flag=false;
		System.out.println("记录日志");
		if(method.getName().startsWith("update")||method.getName().startsWith("delete")) {
			flag=true;
			
		}
		
		if(flag) {
			System.out.println("打开事物");
		}
		Object invoke = method.invoke(target, args);
		if(flag) {
			System.out.println("关闭事物");
		}
		
		return invoke;
	}

}
