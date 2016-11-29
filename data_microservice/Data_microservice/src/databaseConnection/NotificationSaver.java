package databaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import services.Notification;


public class NotificationSaver implements INotificationSaver {
    private final String tableName = "messages";
    private final String values = " VALUES ( ?, ?, ?, ?, ?, ? );";
    private DBConnection connection;

    private final String insertNotification = "INSERT INTO " + tableName +
            " ( timestamp, source_user_id, target_user_id," +
            " target_group_id, priority ,value) " + values;

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
           //e.printStackTrace();
        }
    }

    private void setNotificationOnStatement(Notification notification, PreparedStatement statement) throws SQLException {
        statement.setTimestamp(1, notification.getTime());
        statement.setInt(2, notification.getSenderId());
        statement.setInt(3, notification.getReceivers());
        statement.setInt(4, notification.getReceivers());
        statement.setInt(5, notification.getPriority());
        statement.setString(6, notification.getMessage());
    }
}