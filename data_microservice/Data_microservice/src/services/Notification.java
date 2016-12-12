package services;

import java.io.Serializable;
import java.sql.Timestamp;

public class Notification implements Serializable{

	private static final long serialVersionUID = -8922509021222517613L;
	private boolean sendToGroup;
	private int senderId;
	private int receiverId;
	private String tag;
	private int priority;
	private String message;
    private int aggregationType;
	private Timestamp time;

    public Notification(boolean sendToGroup, int senderId, int receiverId, String tag, int priority, String message) {
        this.message = message;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.tag = tag;
        this.sendToGroup = sendToGroup;
        this.priority = priority;
        this.time = new Timestamp(System.currentTimeMillis());
    }

	public Notification(boolean sendToGroup, int senderId, int receiverId, String tag, int priority, String message, int aggregationType){
		this.message = message;
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.tag = tag;
		this.sendToGroup = sendToGroup;
		this.priority = priority;
		this.time = new Timestamp(System.currentTimeMillis());
        this.aggregationType = aggregationType;
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
	
	public Timestamp getTime(){
		return time;
	}
	
	public int getPriority(){
		return priority;
	}

    public int getAggregationType(){
        return aggregationType;
    }
}
