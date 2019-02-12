package myrpc;

public class RpcConsumer {

	public static void main(String[] args) {
		HelloService hello = RpcFramework.refer(HelloService.class, "127.0.0.1", 1234);
		String hello2 = hello.hello("cf");
		int age = hello.getAge("cf");
		System.out.println(hello2+ " 年龄：" +age );
	}
}
