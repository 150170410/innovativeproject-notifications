package databaseConnection;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

public class NotificationReader implements INotificationReader {
    private final String tableName = Notification.class.getSimpleName();
    private final String selectBasedOnUserId = "SELECT * FROM " + tableName + " WHERE " + Notification.targetUserIdName + "=";

    private DBConnection dbConnection;

    public NotificationReader(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Notification> getNotification(int userId) {
        List<Notification> notificationList = new LinkedList<>();
        try {
            Statement statement = dbConnection.getConnection().createStatement();
            ResultSet result = statement.executeQuery(selectBasedOnUserId + userId);

            while(result.next()) {
                Notification notification = readNotification(result);
                notificationList.add(notification);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return notificationList;
    }

    private Notification readNotification(ResultSet result) throws SQLException {
        int messageId = result.getInt(Notification.messageIdName);
        Timestamp receivingTime = result.getTimestamp(Notification.receivingTimeName);
        int sourceUserId = result.getInt(Notification.sourceUserIdName);
        int targetUserId = result.getInt(Notification.targetUserIdName);
        int targetGroupId = result.getInt(Notification.targetGroupIdName);
        int priority = result.getInt(Notification.priorityName);
        String value = result.getString(Notification.valueName);

        return new Notification(messageId, receivingTime, sourceUserId,
                targetUserId, targetGroupId, priority, value);
    }
}
