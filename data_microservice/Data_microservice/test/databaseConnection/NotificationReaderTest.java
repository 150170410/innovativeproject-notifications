package databaseConnection;

import org.junit.After;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NotificationReaderTest {
    final Notification notification = new Notification(1, new Timestamp(123000), 2, 3, 4, 5, "test");

    DBConnection dbConnectionMock = mock(DBConnection.class);
    Connection connectionMock = mock(Connection.class);
    Statement statementMock = mock(Statement.class);
    ResultSet resultMock = mock(ResultSet.class);

    DBConnection dbConnection = new DBConnection("localhost:3306/NotificationDatabase", "root", "root");
    INotificationReader sut = new NotificationReader(dbConnectionMock);

    @After
    public void after()
    {
        dbConnection.closeConnection();
    }

    @Test
    public void getNotificationFromDatabase() throws Exception {
        when(dbConnectionMock.getConnection()).thenReturn(connectionMock);
        when(connectionMock.createStatement()).thenReturn(statementMock);
        when(statementMock.executeQuery(contains("SELECT * FROM Notification"))).thenReturn(resultMock);

        when(resultMock.next()).thenReturn(true).thenReturn(false);
        when(resultMock.getInt(anyString()))
                .thenReturn(notification.getMessageId())
                .thenReturn(notification.getSourceUserId())
                .thenReturn(notification.getTargetUserId())
                .thenReturn(notification.getTargetGroupId())
                .thenReturn(notification.getPriority());
        when(resultMock.getString(anyString())).thenReturn(notification.getValue());
        when(resultMock.getTimestamp(anyString())).thenReturn(notification.getReceivingTime());

        List<Notification> notificationList = sut.getNotification(notification.getTargetUserId());

        assertEquals(1, notificationList.size());
        assertEquals(notification, notificationList.get(0));
    }

//    @Test
    public void getNotificationFromDatabaseWithoutMock() throws Exception {
        sut = new NotificationReader(dbConnection);

        List<Notification> notificationList = sut.getNotification(notification.getTargetUserId());
        assertEquals(1, notificationList.size());

        assertEquals(notification, notificationList.get(0));
    }



}