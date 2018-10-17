

public class ParentTest  
{  
  
    public static void main(String[] args)  
    {  
        System.out.println("parent thread begin ");  
          
        ChildThread t1 = new ChildThread("thread1");  
        ChildThread t2 = new ChildThread("thread2");  
        t1.start();  
        t2.start();  
  
        System.out.println("parent thread over ");  
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