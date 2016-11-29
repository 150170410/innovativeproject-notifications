package databaseConnection;

import java.util.List;
import services.Notification;

public interface INotificationReader {

    List<Notification> getNotification(int userId);
}
