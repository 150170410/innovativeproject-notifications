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

    static final String messageIdName = "messageId";
    static final String receivingTimeName = "receivingTime";
    static final String sourceUserIdName = "sourceUserId";
    static final String targetUserIdName = "targetUserId";
    static final String targetGroupIdName = "targetGroupId";
    static final String priorityName = "priority";
    static String valueName = "notificationMsg";

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Notification that = (Notification) o;

        return messageId == that.messageId &&
                sourceUserId == that.sourceUserId &&
                targetUserId == that.targetUserId &&
                targetGroupId == that.targetGroupId &&
                priority == that.priority &&
                receivingTime.equals(that.receivingTime) &&
                value.equals(that.value);

    }

    @Override
    public String toString() {
        return "Notification{" +
                "messageId=" + messageId +
                ", receivingTime=" + receivingTime +
                ", sourceUserId=" + sourceUserId +
                ", targetUserId=" + targetUserId +
                ", targetGroupId=" + targetGroupId +
                ", priority=" + priority +
                ", value='" + value + '\'' +
                '}';
    }
}
