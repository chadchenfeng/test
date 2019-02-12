import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpClientTest {

	public static void main(String[] args) {
		//testFetch01();
		for(int j=0;j<1;j++) {
			mutilThread();
		}
		
	}
	
	public static void testFetch01() {
		try {
			DefaultHttpClient httpclient=new  DefaultHttpClient();
			HttpGet httpget=new HttpGet("http://127.0.0.1:8765/service-/hi?name=cf&&token=123");
			HttpResponse resp = httpclient.execute(httpget);
			HttpEntity entity = resp.getEntity();
			if(entity!=null) {
				InputStream content = entity.getContent();
				BufferedReader br = new BufferedReader(new InputStreamReader(content,"utf-8")) ;
				String line=null;
				if((line=br.readLine())!=null) {
					System.out.println(line);
				}
				String contentCharSet = EntityUtils.getContentCharSet(entity);
				System.out.println("字符集编码"+contentCharSet+" 返回状态："+resp.getStatusLine().getStatusCode());
				content.close();
			}
			httpclient.getConnectionManager().shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public static void mutilThread() {
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
//				sendPost();
				testFetch01();
			}
		};
		for(int i=0;i<10;i++) {
			System.out.println("============第"+i);
			new Thread(runnable).start();
		}
	}
	
	public static void sendPost() {
		try {
			DefaultHttpClient httpclient=new  DefaultHttpClient();
			
			
			HttpPost httppost=new HttpPost("http://api.cn.1gdt.com/mpt-goods/wxd36756759908803f/queryGoodsDetail");
			httppost.setHeader("x-ctx", "tP6KbRYcT48CmvoZqLkCnl8T%2FnBsasJcHgBKbPsC4fDwLXH%2FlwaebjOjYflE6IF3Ys75Pu23sQGbOuDsgNEZhKcBARWqOsHayAKfZ%2BeUwh1M6WE4SUFT5jpLsRybarR0xsLRlUlRzIrtQjd9zLhqUw9Wahw%2FMYkYLCqkPhRC49g%3D");
			//设置参数体
			StringEntity se=new StringEntity("{\"goodsId\": \"1\"}");
			/*List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			formparams.add(new BasicNameValuePair("goodsId","1"));
			HttpEntity param=new UrlEncodedFormEntity(formparams);
			httppost.setEntity(param);*/
			httppost.setEntity(se);
			HttpResponse resp = httpclient.execute(httppost);
			HttpEntity entity = resp.getEntity();
			String string = new String(EntityUtils.toString(entity).getBytes("ISO8859-1"),"utf-8");
			System.out.println("currentThread"+Thread.currentThread().getName()+"  "+string);
		} catch (Exception e) {
		}
		
		
	}
}
