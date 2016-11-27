package services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.TimeoutException;

public class Deserialization {
	
	ReceiveFromRabbit rabbit;
	private byte[] notifiByteArr = null;
	
	public Deserialization(String queueName) throws IOException, InterruptedException, TimeoutException{
		rabbit = new ReceiveFromRabbit(queueName);
		rabbit.getNotificationBytes();
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
