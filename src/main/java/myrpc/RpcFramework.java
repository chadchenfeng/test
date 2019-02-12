package myrpc;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RpcFramework {

	public static void export(Object service,int port) throws IOException {
		if(service==null) {
			throw new IllegalArgumentException("service is null");
		}
		if(port>65535 || port<=0) {
			throw new IllegalArgumentException("invalid port "+port);
		}
		System.out.println("暴露服务 "+service.getClass().getName() +" 端口 "+port);
		ServerSocket server=new ServerSocket(port);
		while(true) {
			Socket socket = server.accept();
			new Thread(()->{
				try {
					InputStream inputStream = socket.getInputStream();
					ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
					String methodname = objectInputStream.readUTF();
					System.out.println("==read1"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date()));
					Class<?>[] parameterTypes = (Class<?>[]) objectInputStream.readObject();
					System.out.println("==read2"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date()));
					Object[] arguments = (Object[]) objectInputStream.readObject();
					System.out.println("==read3"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date()));
					Method method = service.getClass().getMethod(methodname, parameterTypes);
					Object result = method.invoke(service, arguments);
					System.out.println("服务处理并响应："+result.toString());
					ObjectOutputStream output=new ObjectOutputStream(socket.getOutputStream());
					output.writeObject(result);
					
					output.close();
					objectInputStream.close();
					socket.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}) 
			.start();
			
		}
		
	}
	
	public static<T> T refer(Class<T> interfaceclass,String host,int port) {
		if(interfaceclass==null) {
			throw new IllegalArgumentException("interfaceclass is null");
		}
		if(port>65535 || port<=0) {
			throw new IllegalArgumentException("invalid port "+port);
		}
		System.out.println("rpc调用服务："+host+ "方法："+interfaceclass.getName());
		Class<?>[] interfaces = interfaceclass.getInterfaces();
		Class<?>[] interfaces2= new Class<?>[] {interfaceclass};
		@SuppressWarnings({ "unchecked", "resource" })
		T newProxyInstance = (T) Proxy.newProxyInstance(interfaceclass.getClassLoader()
				, new Class<?>[] {interfaceclass}, /*new InvocationHandlerImpl(host, port)*/(proxy,method,arguments)->{
					System.out.println("进入代理");
					Socket socket=new Socket(host, port);
					OutputStream outputStream = socket.getOutputStream();
					ObjectOutputStream output=new ObjectOutputStream(outputStream);
					output.writeUTF(method.getName());
					System.out.println("==write1"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date()));
					output.flush();
					Thread.sleep(2000);
					output.writeObject(method.getParameterTypes());
					System.out.println("==write2"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date()));
					Thread.sleep(2000);
					output.writeObject(arguments);
					System.out.println("==write3"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date()));
					//客户端读取并返回服务端的响应
					ObjectInputStream input=new ObjectInputStream(socket.getInputStream());
					Object result = input.readObject();
					if(result instanceof Throwable) {
						throw (Throwable)result;
					}
					input.close();
					outputStream.close();
					socket.close();
					
					return result;
				}
				);
		
		return newProxyInstance;
	}
}
