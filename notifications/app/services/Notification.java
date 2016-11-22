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

	/***
	 *
	 * @param sendToGroup send message to group or user
	 * @param senderId producer's ID
	 * @param receiverId receiver's (user/group) ID
	 * @param tag message topic
	 * @param priority priority
	 * @param message notification message
	 */
	public Notification(boolean sendToGroup, int senderId, int receiverId, String tag, int priority, String message){
		GregorianCalendar time = new GregorianCalendar();

		this.message = message;
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.tag = tag;
		this.sendToGroup = sendToGroup;
		this.priority = priority;
		this.time = ((Integer.toString(time.get(Calendar.DAY_OF_MONTH))+ ":" +Integer.toString(time.get(Calendar.MONTH))+ ":" +Integer.toString(time.get(Calendar.YEAR))+ " " +Integer.toString(time.get(Calendar.HOUR_OF_DAY)) + ":" + Integer.toString(time.get(Calendar.MINUTE)) + ":" + Integer.toString(time.get(Calendar.SECOND))));

	}

	public String getMessage() {
		return message;
	}

	public int getSenderId() {
		return senderId;
	}

	public int getReceivers() {
		return receiverId;
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