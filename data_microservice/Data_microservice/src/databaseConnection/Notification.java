package databaseConnection;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class Notification {
    private int messageId;
    private Timestamp timestamp;
    private int sourceUserId;
    private int targetUserId;
    private int targetGroupId;
    private int priority;
    private String value;

    public Notification(int messageId, Timestamp timestamp, int sourceUserId, int targetUserId,
                        int targetGroupId, int priority, String value) {
        this.messageId = messageId;
        this.timestamp = timestamp;
        this.sourceUserId = sourceUserId;
        this.targetUserId = targetUserId;
        this.targetGroupId = targetGroupId;
        this.priority = priority;
        this.value = value;
    }

    public int getMessageId() {
        return messageId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
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
