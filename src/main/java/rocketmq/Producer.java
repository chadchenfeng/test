package rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class Producer {

	public static void main(String[] args) throws Exception {
		DefaultMQProducer producer=new DefaultMQProducer("cf_test1");
		producer.setNamesrvAddr("192.168.2.200:9876");
		producer.start();
		for(int i=0;i<1;i++) {
			Message msg=new Message("TopicTest","TagA",("这是rocketmq的第一个测试:"+i).getBytes(RemotingHelper.DEFAULT_CHARSET));
			SendResult send = producer.send(msg);
			System.out.printf("%s%n",send);
		}
		producer.shutdown();
	}
}
