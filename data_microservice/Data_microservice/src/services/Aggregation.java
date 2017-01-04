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
        saver.saveToDatabase(notification);
    }

    private void Last(Notification notification) {
        // tylko najnowszy
        boolean isInBase = saver.isAnyNotificationFromProducerInDataBase(notification);
        if ( ! isInBase )
            saver.saveToDatabase(notification);
        else
            saver.update(notification);

    }

    private void First(Notification notification) {
        // tylko najstarszy
        boolean isInBase = saver.isAnyNotificationFromProducerInDataBase(notification);
        if (! isInBase)
            saver.saveToDatabase(notification);
    }

}
