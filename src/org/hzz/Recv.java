package org.hzz;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Recv {
    private static final String QUEUE_NAME="hello";
    public static void main(String[] args) throws Exception{
        String uri = System.getenv("CLOUDAMQP_URL");
        if(uri==null){
            uri = "amqp://nkaqscnr:xze2AHhpjQCb3vl86wJgaxwbUb28uL_p@hornet.rmq.cloudamqp.com/nkaqscnr";
        }
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(uri);
        factory.setRequestedHeartbeat(30);
        factory.setConnectionTimeout(30000);

        // 获取TCP连接
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 声明queue
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        // 异步等待
        DeliverCallback deliverCallback = (consumerTag,delivery)->{
            String message = new String(delivery.getBody(),"utf-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,consumerTag->{});
    }
}
