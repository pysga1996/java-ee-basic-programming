import static java.lang.String.format;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/chat", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class WebsocketEndpoint {

  static Set<Session> peers = Collections.synchronizedSet(new HashSet<>());

  @OnOpen
  public void onOpen(Session session) {
    System.out.println(format("%s joined the chat room.", session.getId()));
    peers.add(session);
  }

  @OnMessage
  public void onMessage(Message message, Session session) throws IOException, EncodeException {
    String user = (String) session.getUserProperties().get("user");
    if (user == null) {
      session.getUserProperties().put("user", message.getSender());
    }
    if ("quit".equalsIgnoreCase(message.getContent())) {
      session.close();
    }

    System.out.println(format("[%s:%s] %s", session.getId(), message.getReceived(), message.getContent()));

    //broadcast the message
    for (Session peer : peers) {
      if (!session.getId().equals(peer.getId())) { // do not resend the message to its sender
        peer.getBasicRemote().sendObject(message);
      } else {
        Message message1 = new Message();
        message1.setSender("Server");
        message1.setReceived(new Date());
        message1.setContent("Fuck you!" + message.getContent());
        peer.getBasicRemote().sendObject(message1);
      }
    }
  }

  @OnClose
  public void onClose(Session session) throws IOException, EncodeException {
    System.out.println(format("%s left the chat room.", session.getId()));
    peers.remove(session);
    //notify peers about leaving the chat room
    for (Session peer : peers) {
      Message chatMessage = new Message();
      chatMessage.setSender("Server");
      chatMessage.setContent(format("%s left the chat room.",
          session.getUserProperties().get("user")));
      chatMessage.setReceived(new Date());
      peer.getBasicRemote().sendObject(chatMessage);
    }
  }

}