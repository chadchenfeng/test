import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureTest {

	public static void main(String[] args) {
		String ticket= getTicket("feng.chen");
		String result = executeTask(ticket);
		if("timeout".equals(result)) {
			System.out.println("超时返回凭证信息:"+ticket);
		}else {
			System.out.println("返回文件信息"+result);
			
		}
		
	}
	
	public static String getTicket(String userId) {
		String ticket=null;
		String date=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		int random=(int) (Math.random()*10000);
		ticket=date+userId.replace(".", "")+String.format("%04d", random);
		return ticket;
	}
	
	public static String executeTask(String ticket) {
		String result=null;
		ExecutorService executor = Executors.newSingleThreadExecutor();
		FutureTask<String> futureTask=new FutureTask<String>(()-> {
			System.out.println("生成文件……");
			Thread.sleep(1000);
			System.out.println("推送oss");
			System.out.println("凭证入库:"+ticket);
			return "/home/dmp/report/xxx.csv";
		});
		executor.submit(futureTask);
		 try {
			 result= futureTask.get(3, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			result="timeout";
		}finally {
			executor.shutdown();
		}
		return result; 
	}
}
