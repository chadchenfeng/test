

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceTest {

	public static void main(String[] args) throws InterruptedException{  
		  
        /*// 创建两个Person对象，它们的id分别是101和102。  
        Person p1 = new Person(101);  
        Person p2 = new Person(102);  
        // 新建AtomicReference对象，初始化它的值为p1对象  
        AtomicReference ar = new AtomicReference(p1);  
        // 通过CAS设置ar。如果ar的值为p1的话，则将其设置为p2。  
        ar.compareAndSet(p1, p2);  
        //ar.set(p1);
  
        Person p3 = (Person)ar.get();  
        System.out.println("p3 is "+p3);  
        System.out.println("p3.equals(p1)="+p3.equals(p1));  */
		
		/*AtomicReference<Person> calcount=new AtomicReference<Person>();
		Person p=new Person(1);
		calcount.set(p);
		for(int i=0;i<10;i++) {
			new Thread(()->{
				long c=calcount.get().getId();
				for(int j=0;j<5;j++) {
					while(true) {
						c++;
						Person oldp=calcount.get();
						Person newp=new Person(c);
						//calcount.get().setId(c);
						if(calcount.compareAndSet(oldp, newp)) {
							
							System.out.println(Thread.currentThread().getName()+"------------"+calcount.get());
							break;
						}
					}
					
				}
			}
					new Runnable() {
				
				@Override
				public void run() {
					
					long c=calcount.get().getId();
					for(int j=0;j<5;j++) {
					c++;
					Person oldp=calcount.get();
					Person newp=new Person(c);
					//calcount.get().setId(c);
					calcount.compareAndSet(oldp, newp);
					System.out.println(Thread.currentThread().getName()+"------------"+calcount.get());
					}
				}
			}
					
			).start();
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		System.out.println("================="+calcount.get());
		
		
		
		*/
		
		dfasd111();
		
		
    }  
	
	
	
	

	private static volatile Integer num1 = 0;
    private static AtomicReference<Integer> ar=new AtomicReference<Integer>(num1);

    public static void dfasd111() throws InterruptedException{
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable(){
                @Override
                public void run() {
                    for (int i = 0; i < 10; i++)
                        while(true){
                            Integer temp=ar.get();
                            
                            if(ar.compareAndSet(temp, temp+1)) {
                            	System.out.println(Thread.currentThread().getName()+"-------"+temp);
                            	break;
                            }
                        }
                }       
            }).start();
        }
        Thread.sleep(10000);
        System.out.println(ar.get()); //10000000
    }
        
    public void dfasd1112() throws InterruptedException{
        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable(){
                @Override
                public void run() {
                    for (int i = 0; i < 10000; i++) {
                        num1=num1++;
                    }
                }       
            }).start();
        }
        Thread.sleep(10000);
        System.out.println(num1); //something like 238981
    }

}

class Person {  
    private long id;  
    public Person(long id) {  
        this.id = id;  
    }  
    public String toString() {  
        return "id:"+id;  
    }
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}  
}