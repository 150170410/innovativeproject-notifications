package controllers;

import play.*;
import play.mvc.*;
import services.ReceiveFromRabbit;
import services.SendToRabbit;
import services.Notification;
import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.concurrent.TimeoutException;

import play.db.DB;
import java.sql.*;

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
    /*public Result index() throws IOException, java.lang.InterruptedException, TimeoutException {
        ReceiveFromRabbit r = new ReceiveFromRabbit();
        r.receive();
        while(r.messages.isEmpty()){
          
        }
        Notification not = r.messages.poll();
        return ok("Your message: '" + not.getMessage() +  "' Time: " + not.getTime());
    }*/

    public Result getMessage() throws SQLException
    {
        Statement stmt = null;
        Connection connection = null;
        String result = "";
        try
        {
            connection = DB.getConnection("default");
            stmt = connection.createStatement();
            String query = "SELECT * FROM notification";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
            {
                String message = rs.getString("message");
                result += message + "\n";
            }
        } catch(SQLException e) {
            Logger.info(e.getMessage());
        } finally {
            connection.close();
        }
        return ok(result);
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result LogIn() throws SQLException
    {
      JsonNode json = request().body().asJson();
      String username = json.findPath("username").textValue();
      String password = json.findPath("password").textValue();

      Statement stmt = null;
      try
      {
        Connection connection = DB.getConnection("default");
        stmt = connection.createStatement();
        String query = "SELECT password FROM users WHERE username = '" + username + "'";
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
          String passwd = rs.getString("password");
          if (password.equals(passwd))
          {
            stmt.close();
            return ok("Success");
          }
        }
      }
      catch(SQLException e) {
          Logger.info(e.getMessage());
      } finally {
          if (stmt != null) stmt.close();
      }

      return ok("Invalid");
    }
}