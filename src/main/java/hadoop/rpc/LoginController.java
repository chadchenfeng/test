package hadoop.rpc;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

public class LoginController {

	public static void main(String[] args) throws IOException {
		LoginServiceInterface proxy=RPC.getProxy(LoginServiceInterface.class, 1L, new InetSocketAddress("weekend110", 10000), new Configuration());
		String result=proxy.login("chenfeng", "123");
		System.out.println(result);
	}
}
