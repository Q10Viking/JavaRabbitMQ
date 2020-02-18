package org.hzz;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Scanner;

/**
 * RabbitMQ,Java发送客户端
 */
public class Sender {
    private static final String QUEUE_NAME="hello";

    public static void main(String[] args) throws Exception {
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

        // create queue
        boolean durable = false;    //durable - RabbitMQ will never lose the queue if a crash occurs
        boolean exclusive = false;  //exclusive - if queue only will be used by one connection
        boolean autoDelete = false; //autodelete - queue is deleted when last consumer unsubscribes
        channel.queueDeclare(QUEUE_NAME,durable, exclusive, autoDelete, null);

        // send message
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入数据");
        String message = scanner.nextLine();
        String exchangeName = "";
        String routingKey = "hello";
        channel.basicPublish(exchangeName,routingKey,null,message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        channel.close();
        connection.close();
    }
}
