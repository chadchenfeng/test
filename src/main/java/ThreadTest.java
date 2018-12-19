import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadTest  
{  
  
    public static void main(String[] args) throws Exception  
    {  
       /* System.out.println("parent thread begin ");  
          
        ChildThread t1 = new ChildThread("thread1");  
        ChildThread t2 = new ChildThread("thread2");  
        t1.start();  
        t2.start();  
  
        System.out.println("parent thread over ");  
        */
        ExecutorService threadpool = Executors.newFixedThreadPool(5);
        threadpool.execute(()->{
        	for(int i=0;i<10;i++)
        		System.out.println("线程1--"+i);
        });
        
        threadpool.shutdown();
        System.out.println("结束！！！！！！");
    }  
}

  
class ChildThread extends Thread  
{  
    private String name = null;  
  
    public ChildThread(String name)  
    {  
        this.name = name;  
    }  
  
    @Override  
    public void run()  
    {  
        System.out.println(this.name + "--child thead begin");  
  
        try  
        {  
            Thread.sleep(5000);  
        }  
        catch (InterruptedException e)  
        {  
            System.out.println(e);  
        }  
  
        System.out.println(this.name + "--child thead over");  
    }  
}  