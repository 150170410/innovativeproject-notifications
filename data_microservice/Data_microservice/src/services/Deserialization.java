package services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.TimeoutException;

public class Deserialization {
	
	ReceiveFromRabbit rabbit;
	Aggregation aggregation;
	private byte[] notifiByteArr = null;
	
	public Deserialization(String queueName) throws IOException, InterruptedException, TimeoutException{
		rabbit = new ReceiveFromRabbit(queueName);
		rabbit.getNotificationBytes();
	}

	public Deserialization(String queueName, Aggregation aggregation) throws InterruptedException, TimeoutException, IOException {
		rabbit = new ReceiveFromRabbit(queueName, this);
		this.aggregation = aggregation;
		rabbit.configure();
	}

	public void processNotification(byte[] notificationBytes)
	{
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(notificationBytes);
			ObjectInputStream is = new ObjectInputStream(in);
			Notification notification = (Notification)is.readObject();
			aggregation.run(notification);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Notification getNotifiObj(){
		try {
			
			notifiByteArr = rabbit.getNotification();
			if(notifiByteArr == null){
				return null;
			} else {
				ByteArrayInputStream in = new ByteArrayInputStream(notifiByteArr);
				ObjectInputStream is = new ObjectInputStream(in);
				return (Notification)is.readObject();
			}
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
