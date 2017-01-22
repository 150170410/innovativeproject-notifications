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
public class TopicsController extends Controller {

    public Result getTopics(String username) throws SQLException {
        System.out.println("get topics");
        PreparedStatement statement = null;
        Connection connection = null;
        List<ObjectNode> results = new ArrayList<ObjectNode>();
        try {
            connection = DB.getConnection("default");
            statement = connection.prepareStatement("SELECT user_id FROM users WHERE login = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
              connection.close();
              return badRequest("Invalid username");
            }
            String receiverId = resultSet.getString("user_id");

            statement = connection.prepareStatement("SELECT * FROM topics WHERE source_user_id = ?");
            statement.setString(1, receiverId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ObjectNode result = JsonNodeFactory.instance.objectNode();
                result.put("id", resultSet.getInt("topic_id"));
                result.put("name", resultSet.getString("name"));
                results.add(result);
            }

        } catch(SQLException e) {
            Logger.info(e.getMessage());
        } finally {
            connection.close();
        }
        return ok(Json.toJson(results));
    }

    public Result getProducers() throws SQLException {
        System.out.println("get producers");
        PreparedStatement statement = null;
        Connection connection = null;
        List<ObjectNode> producers = new ArrayList<ObjectNode>();
        try {
            connection = DB.getConnection("default");

            statement = connection.prepareStatement("SELECT * FROM users WHERE role = 'P' OR role = 'PU'");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ObjectNode result = JsonNodeFactory.instance.objectNode();
                result.put("id", resultSet.getInt("user_id"));
                result.put("name", resultSet.getString("login"));
                producers.add(result);
            }

        } catch(SQLException e) {
            Logger.info(e.getMessage());
        } finally {
            connection.close();
        }
        return ok(Json.toJson(producers));
    }
    
    @BodyParser.Of(BodyParser.Json.class)
    public Result addTopic() throws SQLException {
      JsonNode json = request().body().asJson();
      String topicName = json.findPath("topicName").textValue();
      String username = json.findPath("username").textValue();
      Connection connection = null;
      PreparedStatement statement = null;
      try {
        connection = DB.getConnection("default");
        statement = connection.prepareStatement("SELECT user_id FROM users WHERE login = ?");
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next()) {
            connection.close();
            return badRequest("Invalid username");
        }
        String userId = resultSet.getString("user_id");
        
        statement = connection.prepareStatement("INSERT INTO `topics` (`topic_id`, `source_user_id`, `name`) VALUES (NULL, ?, ?)");
        statement.setString(1, userId);
        statement.setString(2, topicName);
        if(statement.executeUpdate() > 0)
            return ok("Success");
        
      }
      catch(SQLException e) {
          Logger.info(e.getMessage());
      } finally {
          connection.close();
      }

      return badRequest("error occured");
    }
    
    @BodyParser.Of(BodyParser.Json.class)
    public Result deleteTopic() throws SQLException {
      JsonNode json = request().body().asJson();
      String topicName = json.findPath("topicName").textValue();
      String username = json.findPath("username").textValue();
      Connection connection = null;
      PreparedStatement statement = null;
      
      try {
        connection = DB.getConnection("default");
        statement = connection.prepareStatement("SELECT user_id FROM users WHERE login = ?");
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next()) {
            connection.close();
            return badRequest("Invalid username");
        }
        String userId = resultSet.getString("user_id");
        
        statement = connection.prepareStatement("DELETE FROM topics WHERE source_user_id=? AND name=?");
        statement.setString(1, userId);
        statement.setString(2, topicName);
        if(statement.executeUpdate() > 0)
            return ok("Success");
        
      }
      catch(SQLException e) {
          Logger.info(e.getMessage());
      } finally {
          connection.close();
      }

      return badRequest("error occured");
    }

    public Result getSubscribedTopics(String producer, String receiver) throws SQLException {
        System.out.println("get subscribed topics");
        PreparedStatement statement = null;
        Connection connection = null;
        List<ObjectNode> results = new ArrayList<ObjectNode>();
        try {
            connection = DB.getConnection("default");
            statement = connection.prepareStatement("SELECT user_id FROM users WHERE login = ?");
            statement.setString(1, producer);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                connection.close();
                return badRequest("Invalid username");
            }
            String producerId = resultSet.getString("user_id");

            statement = connection.prepareStatement("SELECT user_id FROM users WHERE login = ?");
            statement.setString(1, receiver);
            resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                connection.close();
                return badRequest("Invalid username");
            }
            String receiverId = resultSet.getString("user_id");

            statement = connection.prepareStatement("SELECT topics.* FROM topics INNER JOIN subscriptions ON topics.topic_id = subscriptions.topic_id AND subscriptions.target_user_id = ? WHERE topics.source_user_id=?");
            statement.setString(1, receiverId);
            statement.setString(2, producerId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ObjectNode result = JsonNodeFactory.instance.objectNode();
                System.out.println(resultSet.getInt("topic_id") + " " + resultSet.getString("name"));
                result.put("id", resultSet.getInt("topic_id"));
                result.put("name", resultSet.getString("name"));
                results.add(result);
            }

        } catch(SQLException e) {
            Logger.info(e.getMessage());
        } finally {
            connection.close();
        }
        return ok(Json.toJson(results));
    }

    public Result getNotSubscribedTopics(String producer, String receiver) throws SQLException {
        System.out.println("get subscribed topics");
        PreparedStatement statement = null;
        Connection connection = null;
        List<ObjectNode> results = new ArrayList<ObjectNode>();
        try {
            connection = DB.getConnection("default");
            statement = connection.prepareStatement("SELECT user_id FROM users WHERE login = ?");
            statement.setString(1, producer);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                connection.close();
                return badRequest("Invalid username");
            }
            String producerId = resultSet.getString("user_id");

            statement = connection.prepareStatement("SELECT user_id FROM users WHERE login = ?");
            statement.setString(1, receiver);
            resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                connection.close();
                return badRequest("Invalid username");
            }
            String receiverId = resultSet.getString("user_id");

            statement = connection.prepareStatement("SELECT topics.* FROM topics WHERE topics.source_user_id = ? AND topics.topic_id NOT IN (SELECT topic_id FROM subscriptions WHERE subscriptions.target_user_id = ?)");
            statement.setString(1, producerId);
            statement.setString(2, receiverId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ObjectNode result = JsonNodeFactory.instance.objectNode();
                result.put("id", resultSet.getInt("topic_id"));
                result.put("name", resultSet.getString("name"));
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
    public Result subscribe() throws SQLException {
        JsonNode json = request().body().asJson();
        String topicName = json.findPath("topicName").textValue();
        String username = json.findPath("username").textValue();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DB.getConnection("default");
            statement = connection.prepareStatement("SELECT user_id FROM users WHERE login = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                connection.close();
                return badRequest("Invalid username");
            }
            String userId = resultSet.getString("user_id");

            statement = connection.prepareStatement("SELECT topic_id FROM topics WHERE name = ?");
            statement.setString(1, topicName);
            resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                connection.close();
                return badRequest("Invalid username");
            }
            String topicId = resultSet.getString("topic_id");

            statement = connection.prepareStatement("INSERT INTO `subscriptions` (`target_user_id`, `topic_id`) VALUES (?, ?)");
            statement.setString(1, userId);
            statement.setString(2, topicId);
            if(statement.executeUpdate() > 0)
                return ok("Success");

        }
        catch(SQLException e) {
            Logger.info(e.getMessage());
        } finally {
            connection.close();
        }

        return badRequest("error occured");
    }

    @BodyParser.Of(BodyParser.Json.class)
    public Result unsubscribe() throws SQLException {
        JsonNode json = request().body().asJson();
        String topicName = json.findPath("topicName").textValue();
        String username = json.findPath("username").textValue();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DB.getConnection("default");
            statement = connection.prepareStatement("SELECT user_id FROM users WHERE login = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                connection.close();
                return badRequest("Invalid username");
            }
            String userId = resultSet.getString("user_id");

            statement = connection.prepareStatement("SELECT topic_id FROM topics WHERE name = ?");
            statement.setString(1, topicName);
            resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                connection.close();
                return badRequest("Invalid username");
            }
            String topicId = resultSet.getString("topic_id");

            statement = connection.prepareStatement("DELETE FROM subscriptions WHERE target_user_id=? AND topic_id=?");
            statement.setString(1, userId);
            statement.setString(2, topicId);
            if(statement.executeUpdate() > 0)
                return ok("Success");

        }
        catch(SQLException e) {
            Logger.info(e.getMessage());
        } finally {
            connection.close();
        }

        return badRequest("error occured");
    }

}
