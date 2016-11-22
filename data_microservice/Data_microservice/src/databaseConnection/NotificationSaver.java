package databaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NotificationSaver implements INotificationSaver {
    private final String tableName = Notification.class.getSimpleName();
    private final String values = " VALUES ( ? , ? , ? , ?, ? , ? , ? );";

    private final String insertNotification = "INSERT INTO " + tableName +
            " ( " + Notification.messageIdName + " , " + Notification.receivingTimeName + " , "
            + Notification.sourceUserIdName + " , " + Notification.targetUserIdName + " , "
            + Notification.targetGroupIdName + " , " + Notification.priorityName + " , "
            + Notification.valueName + " ) "
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

            setNotificationOnStatement(notification, insert);

            insert.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setNotificationOnStatement(Notification notification, PreparedStatement statement) throws SQLException {
        statement.setInt(1, notification.getMessageId());
        statement.setTimestamp(2, notification.getReceivingTime());
        statement.setInt(3, notification.getSourceUserId());
        statement.setInt(4, notification.getTargetUserId());
        statement.setInt(5, notification.getTargetGroupId());
        statement.setInt(6, notification.getPriority());
        statement.setString(7, notification.getValue());
    }
}