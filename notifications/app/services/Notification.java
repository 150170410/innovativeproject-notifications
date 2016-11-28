package services;

import java.io.Serializable;
import java.util.Calendar;
import java.sql.Timestamp;


public class Notification implements Serializable{


	private static final long serialVersionUID = -8922509021222517613L;

	private boolean sendToGroup;
	private int senderId;
	private int receiverId;
	private String tag;
	private int priority;
	private String message;
	private Timestamp time;

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
		this.message = message;
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.tag = tag;
		this.sendToGroup = sendToGroup;
		this.priority = priority;
		this.time = new Timestamp(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND, 0);
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

	public Timestamp getTime() {
		return time;
	}

	public int getPriority() {
		return priority;
	}

}