package tutorial;

import javax.jws.WebService;
import java.util.*;

/**
 * @author thanhvt
 * @project jax-ws
 */

@WebService(endpointInterface = "tutorial.UserService", targetNamespace = "http://tutorial", serviceName = "UserService", portName = "UserPort")
public class UserServiceImpl implements UserService {

    private static final Map<Integer, User> USERS = new HashMap<>();

    static {
        User root = new User();
        root.setId(1);
        root.setUsername("vu Tat Thanh");
        root.setBirthDate(new Date(1996, Calendar.SEPTEMBER, 25));
        USERS.put(1, new User());
    }

    @Override
    public int insert(User user) {
        Integer id = generateUniqueId();
        user.setId(id);
        USERS.put(id, user);
        return id;
    }

    private int generateUniqueId() {
        return USERS.keySet().stream().max(Comparator.comparingInt(x -> x)).orElse(0)
            + 1; // (x1, x2) -> x1 - x2
    }

    @Override
    public boolean update(User user) {
        return USERS.put(user.getId(), user) != null;
    }

    @Override
    public boolean delete(int id) {
        return USERS.remove(id) != null;
    }

    @Override
    public User get(int id) {
        return USERS.getOrDefault(id, new User());
    }

    @Override
    public UserArray getAll() {
        List<User> userList = new ArrayList<>(USERS.values());
        UserArray userArray = new UserArray();
        userArray.setUser(userList);
        return userArray;
    }
}
