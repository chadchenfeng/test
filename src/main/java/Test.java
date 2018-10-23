import java.util.concurrent.*;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.timeout.IdleStateHandler;
public class Test {
    public static void main(String[]args) throws InterruptedException {
    	 EmbeddedChannel channel = new EmbeddedChannel(new IdleStateHandler(1, 1, 1),new MyInHandler());
         channel.writeInbound("123".getBytes());
    	 while (true) {
             TimeUnit.SECONDS.sleep(2);
             System.out.println(".");
         }
//    	System.out.println("----:"+Runtime.getRuntime().availableProcessors());
//    	
//        ExecutorService executorService = Executors.newFixedThreadPool(5);
//        final int loopCount=2000000;
//        final CountDownLatch latch = new CountDownLatch(loopCount);
//        for (int i=0;i<loopCount;i++){
//            executorService.submit(new Runnable() {
//                @Override
//                public void run() {
//                    System.out.println("hello Work"+latch.getCount());
//                    latch.countDown();
//                }
//            });
//        }
//        latch.await();
    }
    
    private static class MyInHandler extends ChannelInboundHandlerAdapter {
    	
        @Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			System.out.println("read data");
		}

		@Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            System.out.println("event: " + evt);
        }
    }
}


