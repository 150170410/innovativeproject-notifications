package services;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class ReceiveFromRabbit {
	private static final String EXCHANGE_NAME = "topic_logs";

	private int id = 2;

	public void receive(Notification.Tag tag) throws java.io.IOException, java.lang.InterruptedException, TimeoutException {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, "topic");

		String queueName = Integer.toString(id);
		channel.queueDeclare(queueName, false, false, false, null);

		if(tag != null){
			channel.queueBind(queueName, EXCHANGE_NAME, tag.toString());
		}
		else{
			channel.queueBind(queueName, EXCHANGE_NAME, "*");
		}

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
				System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + notification.getMsg() + " form: " + notification.getSenderId() + "'");
			}
		};
		channel.basicConsume(queueName, true, consumer);
	}

	public void receive(){
		try {
			receive(null);
		} catch (IOException | InterruptedException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
