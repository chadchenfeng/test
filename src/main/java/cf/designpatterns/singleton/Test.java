package cf.designpatterns.singleton;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

	public static void main(String[] args) throws InterruptedException {

//		int i=0;
//		while(i<20) {
//			R r=new R();
//			Thread t=new Thread(r);
//			t.start();
//			i++;
//			
//		}
//		
		ExecutorService pool=Executors.newFixedThreadPool(5);
		for(int i=0;i<5;i++) {
			Thread.sleep(100);
			pool.execute(new R());
		}
	}
	
}

class R implements Runnable{
	private static int i=0;
	
	@Override
	public void run() {
		ReadConfig r=ReadConfig.getIntance(i++);
		System.out.println(r.getParam()+"----"+Thread.currentThread().getName());
		
	}
	
}