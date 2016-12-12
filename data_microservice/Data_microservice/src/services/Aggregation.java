package services;

import databaseConnection.INotificationSaver;

public class Aggregation {
    private INotificationSaver saver;
    public Aggregation(INotificationSaver saver) {
        this.saver = saver;
    }

    public void run(Notification notification)
    {
        switch (notification.getAggregationType())
        {
            case 0:
                None(notification);
                break;
            case 1:
                Last(notification);
                break;
            case 2:
                First(notification);
                break;
            default:
                //error;
        }
    }

    private void None(Notification notification) {
        // brak agregacji
        saver.saveToDatabase(notification, "msg_none");
    }

    private void Last(Notification notification) {
        // tylko najnowszy
        boolean isInBase = saver.isAnyNotificationFromProducerInDataBase(notification, "msg_last");
        if ( ! isInBase )
            saver.saveToDatabase(notification, "msg_last");
        else
            saver.update(notification, "msg_last");

    }

    private void First(Notification notification) {
        // tylko najstarszy
        boolean isInBase = saver.isAnyNotificationFromProducerInDataBase(notification, "msg_first");
        if (! isInBase)
            saver.saveToDatabase(notification, "msg_first");
    }

}
