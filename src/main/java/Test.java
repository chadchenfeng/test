import java.util.concurrent.*;
public class Test {
    public static void main(String[]args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        final int loopCount=2000000;
        final CountDownLatch latch = new CountDownLatch(loopCount);
        for (int i=0;i<loopCount;i++){
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("hello Work"+latch.getCount());
                    latch.countDown();
                }
            });
        }
        latch.await();
    }
}