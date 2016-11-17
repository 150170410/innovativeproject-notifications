package services;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Notification implements Serializable{

	private static final long serialVersionUID = -8922509021222517613L;
	
	private String msg;
	private int senderId;
	private List<Integer> receivers;
	private Tag tag;
	private int importance;

	public Notification(){
		receivers = new LinkedList<Integer>();
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public List<Integer> getReceivers() {
		return receivers;
	}

	public void addReceiver(int receiver) {
		receivers.add(receiver);
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public int getImportance() {
		return importance;
	}

	public void setImportance(int importance) throws BadValueException{
		if(importance < 1 || importance > 3 ){
			throw new BadValueException();
		}
		this.importance = importance;
	}
	
	public enum Tag{
		ERROR, COMPLETED;
	}
		
}
