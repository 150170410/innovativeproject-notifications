package databaseConnection;

import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import static org.mockito.Mockito.*;


public class NotificationSaverTest {
    DBConnection dbConnectionMock = mock(DBConnection.class);
    Connection connectionMock = mock(Connection.class);
    PreparedStatement preparedStatementMock = mock(PreparedStatement.class);

    DBConnection dbConnection = new DBConnection("localhost:3306/NotificationDatabase", "root", "root");

    NotificationSaver sut = new NotificationSaver(dbConnectionMock);

    final Notification notification = new Notification(1, new Timestamp(System.currentTimeMillis()), 2, 3, 4, 5, "test");

    @Test
    public void saveToDatabase() throws Exception {
        when(dbConnectionMock.getConnection()).thenReturn(connectionMock);
        when(connectionMock.prepareStatement(contains("INSERT INTO Notification"))).thenReturn(preparedStatementMock);

        sut.saveToDatabase(notification);

        verify(preparedStatementMock).execute();
    }


//    @Test
    public void saveToDatabaseWithoutMock() throws Exception {
        sut = new NotificationSaver(dbConnection);

        sut.saveToDatabase(notification);
    }

}