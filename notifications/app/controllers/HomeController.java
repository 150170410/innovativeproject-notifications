package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.*;

import views.html.*;
/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    String notification = "";

    public Result receive() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest("Expecting Json data");
        } else {
            notification = json.findPath("message").textValue();
            if (notification == null) {
                notification = "Empty";
                return badRequest("Missing parameter [message]");
            } else {
                return ok("Your last message: " + notification);
            }
        }
    }

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok("Your message: " + notification);
    }
}
