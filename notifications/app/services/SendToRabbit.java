package services;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.Channel;

public class SendToRabbit {
    private static String QUEUE_NAME;   
    
    public static void setQueueName(String QUEUE_NAME){
        SendToRabbit.QUEUE_NAME = QUEUE_NAME;
    }
    
    public static void send(String message) throws java.io.IOException, TimeoutException{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        
        channel.close();
        connection.close();
    }
}