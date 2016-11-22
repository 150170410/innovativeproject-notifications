package services;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class SendToRabbit {

    private static final String QUEUE_NAME = "nokia";

    public static void send(Notification notification){
        try{
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");

            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            byte[] message = toBytes(notification);

            channel.basicPublish("", QUEUE_NAME, null, message);
            System.out.println(" [x] Sent to : " + QUEUE_NAME + ": '" + notification.getMessage() + "'");
            channel.close();
            connection.close();

        }catch(TimeoutException | IOException e){
            e.printStackTrace();
        }


    }

    private static byte[] toBytes(Notification notification) throws IOException {
        byte[] bytes;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(notification);
            out.flush();
            bytes = bos.toByteArray();
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return bytes;
    }

}