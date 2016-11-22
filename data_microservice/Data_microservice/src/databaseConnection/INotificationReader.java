package databaseConnection;

import java.util.List;

public interface INotificationReader {

    List<Notification> getNotification(int userId);
}
