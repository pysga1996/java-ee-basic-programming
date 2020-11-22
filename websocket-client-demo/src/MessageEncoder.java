import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<Message> {

  @Override
  public void init(final EndpointConfig config) {
  }

  @Override
  public void destroy() {
  }

  @Override
  public String encode(final Message message) {
    return JsonUtil.formatMessage(message.getContent(), message.getSender());
  }

}