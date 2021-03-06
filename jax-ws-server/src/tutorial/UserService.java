package tutorial;

import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * @author thanhvt
 * @project jax-ws-demo
 */
@WebService(targetNamespace = "http://tutorial", serviceName = "UserService", portName = "UserPort")
@HandlerChain(file = "/handler.xml")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface UserService {
    @WebMethod
    int insert(User user);

    @WebMethod
    boolean update(User user);

    @WebMethod
    boolean delete(int id);

    @WebMethod
    User get(int id);

    @WebMethod
    UserArray getAll();
}
