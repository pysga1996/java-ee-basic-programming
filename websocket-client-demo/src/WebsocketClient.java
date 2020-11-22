import java.net.URI;
import java.util.Scanner;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import org.apache.tomcat.websocket.WsContainerProvider;

public class WebsocketClient  {
  public static final String SERVER = "ws://localhost:8083/websocket/chat";

  public static void main(String[] args) throws Exception {
    WebSocketContainer client = WsContainerProvider.getWebSocketContainer();
    String message;

    // connect to server
    Scanner scanner = new Scanner(System.in);
    System.out.println("Welcome to Tiny Chat!");
    System.out.println("What's your name?");
    String user = scanner.nextLine();
    Session session;
    URI uri = new URI(SERVER);
    System.out.println("uri: " + uri.toString());
    session = client.connectToServer(WebsocketClientEndpoint.class, new URI(SERVER));
    System.out.println("You are logged in as: " + user);

    // repeatedly read a message and send it to the server (until quit)
    do {
      message = scanner.nextLine();
      session.getBasicRemote().sendText(JsonUtil.formatMessage(message, user));
    } while (!message.equalsIgnoreCase("quit"));
  }
}
