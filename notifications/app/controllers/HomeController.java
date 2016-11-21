package controllers;


import play.mvc.*;
import services.ReceiveFromRabbit;
import services.SendToRabbit;
import services.Notification;
import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.concurrent.TimeoutException;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    @BodyParser.Of(BodyParser.Json.class)
    public Result send() {
        JsonNode json = request().body().asJson();
        String message = json.findPath("message").textValue();
        String tag = json.findPath("tag").textValue();
        boolean toGroup = json.findPath("sendToGroup").booleanValue();
        int receiverId = json.findPath("receiverId").intValue();
        int senderId = json.findPath("senderId").intValue();
        int priority = json.findPath("priority").intValue();

        if(tag == null || message == null) {
            return badRequest("Missing parameter");
        } else {
            Notification notification = new Notification(toGroup, senderId, receiverId, "test tag", priority, message);
            SendToRabbit.send(notification);
            return ok("\nTime " + notification.getTime() + " msg: " + message);
        }
    }

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() throws IOException, java.lang.InterruptedException, TimeoutException {
        ReceiveFromRabbit r = new ReceiveFromRabbit();
        r.receive();
        while(r.messages.isEmpty()){

        }
        Notification not = r.messages.poll();
        return ok("Your message: '" + not.getMessage() +  "' Time: " + not.getTime());
    }
}
