package services;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.concurrent.*;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class ReceiveFromRabbit {
	public ConcurrentLinkedQueue<Notification> messages = new ConcurrentLinkedQueue<Notification>();
	private static final String QUEUE_NAME = "nokia";

	private int id = 2;

	public void receive() throws java.io.IOException, java.lang.InterruptedException, TimeoutException {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println(" [*] Waiting for messages.");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				Notification notification = null;
				try {
					notification = byteToObject(body);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				System.out.println(" [x] Received from: "+ QUEUE_NAME + ": '" + notification.getMessage() + "'");
				messages.add(notification);
			}
		};

		channel.basicConsume(QUEUE_NAME, true, consumer);

		channel.close();
		connection.close();

	}


	protected static Notification byteToObject(byte[] body) throws IOException, ClassNotFoundException {
		Notification notification;
		ByteArrayInputStream bis = new ByteArrayInputStream(body);
		ObjectInput in = null;
		try {
			in = new ObjectInputStream(bis);
			notification = (Notification) in.readObject();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				// ignore close exception
			}
		}
		return notification;
	}

}