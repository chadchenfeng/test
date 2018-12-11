package rocketmq;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

public class Consumer {

	public static void main(String[] args) throws Exception {
		DefaultMQPushConsumer  consumer=new DefaultMQPushConsumer("cf_test1");
		consumer.setNamesrvAddr("192.168.2.200:9876");
		consumer.subscribe("TopicTest", "*");
		consumer.registerMessageListener(new MessageListenerConcurrently() {
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
	                ConsumeConcurrentlyContext context) {
	                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
	                try {
						String msg=new String(msgs.get(0).getBody(),"utf-8");
						System.out.println("msg:"+msg);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
	                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	            }
		});
		consumer.start();
		System.out.printf("Consumer Started.%n");
		consumer.shutdown();
	}
}
