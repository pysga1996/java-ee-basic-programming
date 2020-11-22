import static java.lang.String.format;

import java.text.SimpleDateFormat;
import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

@ClientEndpoint(encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class WebsocketClientEndpoint {

  private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

  @OnOpen
  public void onOpen(Session session) {
    System.out.println(format("Connection established. session id: %s", session.getId()));
  }

  @OnMessage
  public void onMessage(Message message) {
    System.out.println(format("[%s:%s] %s",
        simpleDateFormat.format(message.getReceived()), message.getSender(), message.getContent()));
  }

}
