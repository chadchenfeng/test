

public class volatileTest {

	public static void main(String[] args) throws InterruptedException {
		T tt=new T();
		/*new Thread(()->{
			System.out.println("start");
			while(tt.t.equals("a")) {
			}
			System.out.println("end");
		}).start();
		
		Thread.sleep(1000);
		*/
		tt.t="aaa";
	}
	
	

}


class T {
	volatile String  t="a";
}