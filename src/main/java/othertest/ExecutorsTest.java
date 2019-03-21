package othertest;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorsTest {

	
	public static void main(String[] args) throws InterruptedException {
		Runtime runtime = Runtime.getRuntime();
		long freeMemory = runtime.freeMemory()/1024/1024;
		long totalMemory = runtime.totalMemory()/1024/1024;
		System.out.println("freememory:"+freeMemory+"  totalMemory:"+totalMemory);
		
		ExecutorService threadpool=Executors.newFixedThreadPool(10);
		System.out.println(" start"+runtime.freeMemory()/1024/1024);
//		ThreadPoolExecutor threadpool = new ThreadPoolExecutor(8, 8, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
		for(int i=0;i<100;i++) {
			threadpool.submit(()->{
				try {
					Thread.sleep(1000);
					System.out.println(" ---"+Thread.currentThread());
					System.out.println(" ==="+runtime.freeMemory()/1024/1024);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
			System.out.println(i+" ==="+runtime.freeMemory()/1024/1024);
		}
//		threadpool.shutdown();
		Thread.currentThread().sleep(50000);
		System.gc();
		System.out.println(" end"+ Runtime.getRuntime().freeMemory()/1024/1024);
		
	}
}
