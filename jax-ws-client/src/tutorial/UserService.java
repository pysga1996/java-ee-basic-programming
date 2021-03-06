
package tutorial;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "UserService", targetNamespace = "http://tutorial")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface UserService {


    /**
     * 
     * @param arg0
     * @return
     *     returns tutorial.User
     */
    @WebMethod
    @WebResult(partName = "return")
    @Action(input = "http://tutorial/UserService/getRequest", output = "http://tutorial/UserService/getResponse")
    public User get(
        @WebParam(name = "arg0", partName = "arg0")
        int arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(partName = "return")
    @Action(input = "http://tutorial/UserService/updateRequest", output = "http://tutorial/UserService/updateResponse")
    public boolean update(
        @WebParam(name = "arg0", partName = "arg0")
        User arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns boolean
     */
    @WebMethod
    @WebResult(partName = "return")
    @Action(input = "http://tutorial/UserService/deleteRequest", output = "http://tutorial/UserService/deleteResponse")
    public boolean delete(
        @WebParam(name = "arg0", partName = "arg0")
        int arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns int
     */
    @WebMethod
    @WebResult(partName = "return")
    @Action(input = "http://tutorial/UserService/insertRequest", output = "http://tutorial/UserService/insertResponse")
    public int insert(
        @WebParam(name = "arg0", partName = "arg0")
        User arg0);

    /**
     * 
     * @return
     *     returns tutorial.UserArray
     */
    @WebMethod
    @WebResult(partName = "return")
    @Action(input = "http://tutorial/UserService/getAllRequest", output = "http://tutorial/UserService/getAllResponse")
    public UserArray getAll();

}
