package rabbitConnection;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.TimeoutException;

public class Deserialization {
	private String queueName = "hello";
	ReceiveFromRabbit rabbit = new ReceiveFromRabbit(queueName);
	
	public Notification getNotificationObject(){
		
		try{
			rabbit.getNotificationBytes();
			ByteArrayInputStream in = new ByteArrayInputStream(rabbit.getNotification());
			ObjectInputStream is = new ObjectInputStream(in);
			return (Notification)is.readObject();
		}catch (IOException | InterruptedException | TimeoutException | ClassNotFoundException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		return null;
	}
}
