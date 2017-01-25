package controllers;

import play.*;
import play.mvc.*;
import play.libs.Json;
import services.ReceiveFromRabbit;
import services.SendToRabbit;
import services.Notification;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
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
            Notification notification = new Notification(toGroup, senderId, receiverId, tag, priority, message);
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

    public Result getMessage(String username) throws SQLException {
        PreparedStatement stmt = null;
        Connection connection = null;
        List<ObjectNode> results = new ArrayList<ObjectNode>();
        try {
            connection = DB.getConnection("default");
            stmt = connection.prepareStatement("SELECT user_id FROM users WHERE login = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
              connection.close();
              return badRequest("Invalid username");
            }
            String receiverId = rs.getString("user_id");

            stmt = connection.prepareStatement("SELECT * FROM messages WHERE target_user_id = ?");
            stmt.setString(1, receiverId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                ObjectNode result = JsonNodeFactory.instance.objectNode();
                result.put("id", rs.getInt("msg_id"));
                result.put("message", rs.getString("value"));
                results.add(result);
            }

        } catch(SQLException e) {
            Logger.info(e.getMessage());
        } finally {
            connection.close();
        }
        return ok(Json.toJson(results));
    }
    
    @BodyParser.Of(BodyParser.Json.class)
    public Result removeMessage() throws SQLException {
      JsonNode json = request().body().asJson();
      String username = json.findPath("username").textValue();
      int id = json.findPath("id").intValue();
      Connection connection = null;
      PreparedStatement stmt = null;
      try {
        connection = DB.getConnection("default");
        stmt = connection.prepareStatement("SELECT user_id FROM users WHERE login=?");
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        String userId;
        if (rs.next()) {
            userId = rs.getString("user_id");
        } else {
          return badRequest("Invalid credentials");
        }

        stmt = connection.prepareStatement("DELETE FROM messages WHERE msg_id = ? AND target_user_id = ?");
        stmt.setInt(1, id);
        stmt.setString(2, userId);
        if (stmt.executeUpdate() > 0)
          return ok("Success");
        else
          return badRequest("Something went wrong.");
      } catch (SQLException e) {
        Logger.info(e.getMessage());
      } finally {
        connection.close();
      }
      return badRequest("Try again later.");
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result removeAllMessages() throws SQLException {
      JsonNode json = request().body().asJson();
      String username = json.findPath("username").textValue();
      Connection connection = null;
      PreparedStatement stmt = null;
      try {
        connection = DB.getConnection("default");
        stmt = connection.prepareStatement("SELECT user_id FROM users WHERE login=?");
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        String userID;
        if (rs.next()) {
          userID = rs.getString("user_id");
        } else {
          return badRequest("Invalid credentials");
        }

        stmt = connection.prepareStatement("DELETE FROM messages WHERE target_user_id = ?");
        stmt.setString(1, userID);
        if (stmt.executeUpdate() > 0)
          return ok("Success");
        else return badRequest("Something went wrong.");
      } catch (SQLException e) {
        Logger.info(e.getMessage());
      } finally {
        connection.close();
      }
      return badRequest("Try again later.");
    }
    
    @BodyParser.Of(BodyParser.Json.class)
    public Result LogIn() throws SQLException {
      JsonNode json = request().body().asJson();
      String username = json.findPath("username").textValue();
      String password = json.findPath("password").textValue();
      Connection connection = null;
      PreparedStatement stmt = null;
      try {
        connection = DB.getConnection("default");
        stmt = connection.prepareStatement("SELECT pass FROM users WHERE login=?");
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
          String passwd = rs.getString("pass");
          if (password.equals(passwd)) {
            stmt.close();
            return ok("Success");
          }
        }
      }
      catch(SQLException e) {
          Logger.info(e.getMessage());
      } finally {
          connection.close();
      }

      return badRequest("Invalid username or password");
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result RegisterClient() throws SQLException {
      JsonNode json = request().body().asJson();
      String username = json.findPath("username").textValue();
      String password = json.findPath("password").textValue();
      String email = json.findPath("email").textValue();
      Connection connection = null;
      PreparedStatement stmt = null;
      try {
        connection = DB.getConnection("default");
        stmt = connection.prepareStatement("SELECT login FROM users WHERE login=? || mail=?");
        stmt.setString(1, username);
        stmt.setString(2, email);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
          return badRequest("Username or email already exists.");
        }
        stmt = connection.prepareStatement("INSERT INTO users(login, pass, mail, role) VALUES(?, ?, ?, 'U')");
        stmt.setString(1, username);
        stmt.setString(2, password);
        stmt.setString(3, email);
        if (stmt.executeUpdate() > 0)
          return ok("Success");
        else
          return badRequest("Something went wrong.");
      } catch (SQLException e) {
        Logger.info(e.getMessage());
      } finally {
        connection.close();
      }

      return badRequest("Try again later.");
    }
}
