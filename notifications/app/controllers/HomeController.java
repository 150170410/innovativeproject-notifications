package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.*;
import services.RecieveFromRabbit;
import services.SendToRabbit;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import views.html.*;
/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    String notification = "";

    public Result receive() throws IOException, TimeoutException{
        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest("Expecting Json data");
        } else {
            notification = json.findPath("message").textValue();
            if (notification == null) {
                notification = "Empty";
                return badRequest("Missing parameter [message]");
            } else {
                SendToRabbit.setQueueName("hello");
                SendToRabbit.send(notification);
                return ok();
            }
        }
    }

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() throws IOException, java.lang.InterruptedException, TimeoutException {
        RecieveFromRabbit.setQueueName("hello");
        return ok("Your message: " + RecieveFromRabbit.getMessage());
    }
}
