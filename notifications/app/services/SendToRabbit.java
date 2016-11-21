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

    private static final String EXCHANGE_NAME = "topic_logs";

    public static void send(Notification notification){
        try{
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            for(int id : notification.getReceivers()){
                channel.queueDeclare(Integer.toString(id), false, false, false, null);
            }

            String routingKey = notification.getTag().toString();
            byte[] message = toBytes(notification);

            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message);
            System.out.println(" [x] Sent '" + routingKey + "':'" + notification.getMsg() + "'");

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
                // ignore close exception
            }
        }

        return bytes;
    }

}