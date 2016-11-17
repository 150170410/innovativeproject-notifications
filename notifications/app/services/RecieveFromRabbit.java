package services;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class RecieveFromRabbit{
    private static String QUEUE_NAME;
    private static String message = null;
    
    public static void setQueueName(String QUEUE_NAME){
        RecieveFromRabbit.QUEUE_NAME = QUEUE_NAME;
    }
    
    public static String getMessage() throws java.io.IOException, java.lang.InterruptedException, TimeoutException{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properies, byte[] body)
            throws IOException {
                RecieveFromRabbit.message = new String(body, "UTF-8");
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
        return RecieveFromRabbit.message;
    }
}