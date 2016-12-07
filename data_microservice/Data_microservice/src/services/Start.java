package services;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import databaseConnection.*;


public class Start {
	
	public static void main(String[] args) throws IOException, InterruptedException, TimeoutException{
		Deserialization des = new Deserialization("nokia");
		DBConnection connection = new DBConnection("127.0.0.1:3306/notifications", "root", "root");
		NotificationSaver sender = new NotificationSaver(connection);
		Notification notif;
		
		while(true){
			try{
				Thread.sleep(5000);
				notif = des.getNotifiObj();
				if(notif == null)
					continue;
				sender.saveToDatabase(notif);
				System.out.println(notif.getMessage());
			} catch (NullPointerException ex){
				ex.printStackTrace();
			}
		}
	}
}
