import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

public class URLtest {

	public static void main(String[] args) {
		for(;;) {
			try {
				String urlString="http://api.cn.1gdt.com/mpt-goods/wxd36756759908803f/queryStylishGoods";
				URL url=new URL(urlString);
				/*Proxy proxy = new Proxy(Proxy.Type.HTTP,new
						InetSocketAddress("68.168.134.107",8443));
				URLConnection conn = url.openConnection(proxy); 

				InputStream openStream = conn.getInputStream();*/
//				InputStream openStream = url.openStream();
				
				HttpURLConnection conn=(HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				int responseCode = conn.getResponseCode();
				System.out.println("服务端相应代码："+responseCode);
				InputStream openStream = conn.getInputStream();
				InputStreamReader inputStreamReader = new InputStreamReader(openStream,"utf-8");
				BufferedReader br=new BufferedReader(inputStreamReader);
				String line=null;
				while((line=br.readLine())!=null) {
					System.out.println(line);
				}
				br.close();
				inputStreamReader.close();
				openStream.close();
				Thread.sleep(1000);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
			
		}
		
	}
}
