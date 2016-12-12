package databaseConnection;

import com.sun.istack.internal.NotNull;
import services.Notification;

public interface INotificationSaver {

    void saveToDatabase(Notification notification, @NotNull String tableName);

    boolean isAnyNotificationFromProducerInDataBase(Notification notification, @NotNull String tableName);

    void update(Notification notification, @NotNull String tableName);
}
