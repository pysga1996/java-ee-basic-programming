package tutorial;

import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * @author thanhvt
 * @project jax-ws-client-demo
 */
public class SoapClient {

    public static final String WSDL_LOCATION = "http://localhost:8085/jax-ws-server/?wsdl";

    public static void main(String[] args) {
        UserServiceImpl userServiceImplService = new UserServiceImpl();
        UserService userService = userServiceImplService.getUserServiceImplPort();

        Date date = new Date();
        XMLGregorianCalendar xmlDate = null;
        GregorianCalendar gc = new GregorianCalendar();

        gc.setTime(date);
        try {
            xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        User user1 = new User();
        user1.setId(1);
        user1.setUsername("Mongo");
        user1.setBirthDate(xmlDate);

        User user2 = new User();
        user2.setId(2);
        user2.setUsername("Redis");
        user2.setBirthDate(xmlDate);
        System.out.println("Insert User : " + userService.insert(user1));
        System.out.println("Insert User : " + userService.insert(user2));
        System.out.println("Get User : " + userService.get(user1.getId()));
        user1.setUsername("Oracle");
        System.out.println("Update User : " + userService.update(user1));
        System.out.println("Get all Users : " + Collections.singletonList(userService.getAll()));
        System.out.println("Delete User : " + userService.delete(user1.getId()));
    }
}
