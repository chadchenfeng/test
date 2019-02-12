package myrpc;

import java.io.IOException;

public class RpcProvider {

	public static void main(String[] args) throws IOException {
		HelloServiceImpl helloservice=new HelloServiceImpl();
		RpcFramework.export(helloservice,1234);
	}
	
}
