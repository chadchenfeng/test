
public class SynchronizedTest {

	static class Outputer {
		private String lock="";
		public synchronized void output(String name) {
			int len=name.length();
			for(int i=0;i<len;i++) {
				System.out.print(name.charAt(i));
			}
			System.out.println("");
		}
		
		public void output2(String name) {
			synchronized (lock) {
				int len=name.length();
				for(int i=0;i<len;i++) {
					System.out.print(name.charAt(i));
				}
				System.out.println("");
			}
		}
	}
	private void init() {
		Outputer outputer=new Outputer();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					outputer.output("chenfeng");
				}
				
			}
		}).start();
		
		new Thread(()->{
			while(true) {
				try {
					Thread.sleep(6);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				outputer.output2("zihan");
			}
		}).start();
	}
	public static void main(String[] args) {
		new SynchronizedTest().init();
	}
	
}
