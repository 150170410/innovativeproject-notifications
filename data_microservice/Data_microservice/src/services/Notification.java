package services;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Notification implements Serializable{

	private static final long serialVersionUID = -8922509021222517613L;
	private boolean sendToGroup;
	private int senderId;
	private int receiverId;
	private String tag;
	private int priority;
	private String message;
	private String time;
	
	public Notification(boolean sendToGroup, int senderId, int receiverId, String tag, int priority, String message){
		GregorianCalendar dt = new GregorianCalendar();
		
		this.message = message;
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.tag = tag;
		this.sendToGroup = sendToGroup;
		this.priority = priority;
		this.time = ((Integer.toString(dt.get(Calendar.DAY_OF_MONTH))+ ":" +Integer.toString(dt.get(Calendar.MONTH))+ 
				":") +Integer.toString(dt.get(Calendar.YEAR))+ " " +Integer.toString(dt.get(Calendar.HOUR_OF_DAY))+ 
				":" +Integer.toString(dt.get(Calendar.MINUTE))+ ":" +Integer.toString(dt.get(Calendar.SECOND)));
	}
	
	public String getMessage(){
		return message;
	}
	
	public int getSenderId(){
		return senderId;
	}
	
	public int getReceivers(){
		return receiverId;
	}
	
	public String getTag(){
		return tag;
	}
	
	public boolean isSendToGroup(){
		return sendToGroup;
	}
	
	public String getTime(){
		return time;
	}
	
	public int getPriority(){
		return priority;
	}
}
