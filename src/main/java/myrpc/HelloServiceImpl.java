package myrpc;

public class HelloServiceImpl implements HelloService{

	@Override
	public String hello(String name) {
		return "hello, "+name;
	}

	@Override
	public int getAge(String name) {
		return 29;
	}

}
