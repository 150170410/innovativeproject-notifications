package databaseConnection;

import com.sun.istack.internal.NotNull;
import services.Notification;

public abstract class INotificationSaver {

    public abstract void saveToDatabase(Notification notification, @NotNull String tableName);
    public void saveToDatabase(Notification notification) {
        saveToDatabase(notification, "messages");
    }

    public abstract boolean isAnyNotificationFromProducerInDataBase(Notification notification,
                                                                    @NotNull String tableName);
    public boolean isAnyNotificationFromProducerInDataBase(Notification notification) {
        return isAnyNotificationFromProducerInDataBase(notification, "messages");
    }

    public abstract void update(Notification notification, @NotNull String tableName);
    public void update(Notification notification) {
        update(notification, "messages");
    }
}
