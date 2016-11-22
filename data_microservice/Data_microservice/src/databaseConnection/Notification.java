package databaseConnection;

import java.sql.Timestamp;

public class Notification {
    private int messageId;
    private Timestamp receivingTime;
    private int sourceUserId;
    private int targetUserId;
    private int targetGroupId;
    private int priority;
    private String value;

    public Notification(int messageId, Timestamp timestamp, int sourceUserId, int targetUserId,
                        int targetGroupId, int priority, String value) {
        this.messageId = messageId;
        this.receivingTime = timestamp;
        this.sourceUserId = sourceUserId;
        this.targetUserId = targetUserId;
        this.targetGroupId = targetGroupId;
        this.priority = priority;
        this.value = value;
    }

    public int getMessageId() {
        return messageId;
    }

    public Timestamp getReceivingTime() {
        return receivingTime;
    }

    public int getSourceUserId() {
        return sourceUserId;
    }

    public int getTargetUserId() {
        return targetUserId;
    }

    public int getTargetGroupId() {
        return targetGroupId;
    }

    public int getPriority() {
        return priority;
    }

    public String getValue() {
        return value;
    }
}
