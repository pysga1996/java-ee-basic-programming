package tutorial;

import javax.xml.ws.Endpoint;

/**
 * @author thanhvt
 * @project jax-ws-demo
 */
public class SoapPublisher {

    public static final String WS_URL = "http://localhost:8080/user-ws/";

    public static void main(String[] args) {
        Endpoint.publish(WS_URL, new UserServiceImpl());
    }
}