package databaseConnection;

import services.Notification;

public abstract class INotificationSaver {

    public abstract void saveToDatabase(Notification notification, String tableName);
    public void saveToDatabase(Notification notification) {
        saveToDatabase(notification, "messages");
    }

    public abstract boolean isAnyNotificationFromProducerInDataBase(Notification notification,
                                                                    String tableName);
    public boolean isAnyNotificationFromProducerInDataBase(Notification notification) {
        return isAnyNotificationFromProducerInDataBase(notification, "messages");
    }

    public abstract void update(Notification notification, String tableName);
    public void update(Notification notification) {
        update(notification, "messages");
    }
}
