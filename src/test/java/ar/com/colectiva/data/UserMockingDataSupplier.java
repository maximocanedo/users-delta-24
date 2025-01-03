package ar.com.colectiva.data;

import ar.com.colectiva.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.*;

import static io.jsonwebtoken.lang.Collections.setOf;
import static org.hibernate.internal.util.collections.CollectionHelper.listOf;

@Service
public class UserMockingDataSupplier implements MockingDataSupplier<User, String> {

    private static final String DEFAULT_PASSWORD = "Abc.1234%5678";
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserMockingDataSupplier(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> list() {
        return listOf(
            andreaBocelli(),
            hectorDaSilva(),
            johnSmith(),
            juanDiegoFlorez(),
            julianAlvarez(),
            leoMessi()
        );
    }

    @Override
    public Map<String, User> map() {
        Map<String, User> usernames = new HashMap<>();
        list().forEach(user -> usernames.put(user.getUsername(), user));
        return usernames;
    }

    private User createBasicUser(
            String username,
            String password,
            String name,
            String surname
    ) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);
        user.setExpired(false);
        user.setCredentialsExpired(false);
        user.setLocked(false);
        user.setName(name);
        user.setSurname(surname);
        return user;
    }

    private User createBasicUser(
        String username,
        String name,
        String surname
    ) { return createBasicUser(username, DEFAULT_PASSWORD, name, surname); }

    public User johnSmith() {
        return createBasicUser(
            "john.smith",
            "John",
            "Smith"
        );
    }

    public User andreaBocelli() {
        return createBasicUser(
            "andrea.bocelli",
            "Andrea",
            "Bocelli"
        );
    }

    public User juanDiegoFlorez() {
        return createBasicUser(
            "juan.diego.florez",
            "Juan Diego",
            "Flórez"
        );
    }

    public User hectorDaSilva() {
        return createBasicUser(
            "hector.silva",
            "Héctor",
            "Da Silva"
        );
    }

    public User julianAlvarez() {
        return createBasicUser(
            "julian.alvarez",
            "Julián",
            "Álvarez"
        );
    }

    public User leoMessi() {
        return createBasicUser(
            "messi",
            "Leonel",
            "Messi"
        );
    }

}
