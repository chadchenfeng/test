package othertest;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {

	public static void main(String[] args) {
		new LockTest().init();
	}
	
	public void init() {
		Outputer outputer = new Outputer();
		for(int i=0;i<5;i++) {
			new Thread(()->{
				try {
					outputer.output("duoxiancheng");
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}).start();
			new Thread(()->{
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				outputer.output("fengjiaozihan");
			}).start();
		}
		
		
//		System.out.println("memory:"+Runtime.getRuntime().freeMemory()/1024/1024);
	}
	
	class Outputer{
		private Lock lock=new ReentrantLock();
		public void output(String name) {
			int len=name.length();
			lock.lock();
			try {
				for(int i=0;i<len;i++) {
					System.out.print(name.charAt(i));
				}
				System.out.println("");
			} catch (Exception e) {
				
			}finally {
				lock.unlock();
			}
			
		}
	}
}
