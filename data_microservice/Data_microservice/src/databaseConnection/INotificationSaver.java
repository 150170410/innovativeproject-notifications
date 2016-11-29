package databaseConnection;

import services.Notification;

public interface INotificationSaver {

    void saveToDatabase(Notification notification);
}
