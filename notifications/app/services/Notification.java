package services;

package rabbitmq;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class Notification implements Serializable{


	private static final long serialVersionUID = -8922509021222517613L;
	
	
	private boolean sendToGroup;
	private int senderId;				
	private int receiver;
	private String tag;
	private int priority;
	private String msg;	
	private String time;
	
	/***
	 * 
	 * @param sendToGroup send message to group or user
	 * @param senderId producer's ID
	 * @param receiver receiver's (user/group) ID
	 * @param tag message topic
	 * @param priority priority
	 * @param msg notification message
	 */
	public Notification(boolean sendToGroup, int senderId, int receiver, String tag, int priority, String msg){
		GregorianCalendar dt = new GregorianCalendar();
		
		this.msg = msg;
		this.senderId = senderId;
		this.receiver = receiver;
		this.tag = tag;
		this.sendToGroup = sendToGroup;
		this.priority = priority;
		this.time = ((Integer.toString(dt.get(Calendar.DAY_OF_MONTH))+ ":" +Integer.toString(dt.get(Calendar.MONTH))+ ":" +Integer.toString(dt.get(Calendar.YEAR))+ " " +Integer.toString(dt.get(Calendar.HOUR_OF_DAY)) + ":" + Integer.toString(dt.get(Calendar.MINUTE)) + ":" + Integer.toString(dt.get(Calendar.SECOND))));

	}
	
	public String getMsg() {
		return msg;
	}

	public int getSenderId() {
		return senderId;
	}

	public int getReceivers() {
		return receiver;
	}

	public String getTag() {
		return tag;
	}

	public boolean isSendToGroup() {
		return sendToGroup;
	}

	public String getTime() {
		return time;
	}

	public int getPriority() {
		return priority;
	}

}

