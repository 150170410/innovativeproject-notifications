
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class RecieveFromRabbit {
	
	private static String QUEUE_NAME = null;
	private static byte[] notification = null;
	
	public static void setQueueName(String queueName){
		RecieveFromRabbit.QUEUE_NAME = queueName;
	}
	
	public static void getNotificationBytes() throws IOException, java.lang.InterruptedException, TimeoutException{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(QUEUE_NAME);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		Consumer consumer = new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
			throws IOException{
				notification = body;
			}
		};
		channel.basicConsume(QUEUE_NAME, true, consumer);
	}
	
	public static byte[] getNotification(){
		return notification;
	}
}
