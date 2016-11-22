package databaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class NotificationSaver implements INotificationSaver {
    private final String tableName = Notification.class.getSimpleName();
    private final String messageId = "messageId";
    private final String receivingTime = "receivingTime";
    private final String sourceUserId = "sourceUserId";
    private final String targetUserId = "targetUserId";
    private final String targetGroupId = "targetGroupId";
    private final String priority = "priority";
    private String value = "notificationMsg";
    private final String values = " VALUES ( ? , ? , ? , ?, ? , ? , ? );";

    private final String insertNotification = "INSERT INTO " + tableName +
            " ( " + messageId + " , " + receivingTime + " , " + sourceUserId + " , " + targetUserId + " , " + targetGroupId + " , "
            + priority + " , " + value + " ) "
            + values;

    private DBConnection connection;

    public NotificationSaver(DBConnection connection) {
        this.connection = connection;
    }

    @Override
    public void saveToDatabase(Notification notification) {
        try {
            Connection connection = this.connection.getConnection();

            PreparedStatement insert = connection.prepareStatement(insertNotification);
            insert.setInt(1, notification.getMessageId());
            insert.setTimestamp(2, notification.getReceivingTime());
            insert.setInt(3, notification.getSourceUserId());
            insert.setInt(4, notification.getTargetUserId());
            insert.setInt(5, notification.getTargetGroupId());
            insert.setInt(6, notification.getPriority());
            insert.setString(7, notification.getValue());

            insert.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}