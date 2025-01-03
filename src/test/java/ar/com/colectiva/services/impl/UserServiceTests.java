package ar.com.colectiva.services.impl;

import ar.com.colectiva.data.UserMockingDataSupplier;
import ar.com.colectiva.models.AccountEvent;
import ar.com.colectiva.models.User;
import ar.com.colectiva.models.dto.req.UserSignupRequest;
import ar.com.colectiva.models.exceptions.Fail;
import ar.com.colectiva.repositories.UserRepository;
import ar.com.colectiva.services.AccountTrackingService;
import ar.com.colectiva.validators.exceptions.ValidationError;
import ar.com.colectiva.services.SecurityContextService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    private final AccountTrackingService accountTrackingService;
    private final UserMockingDataSupplier dataSupplier;
    private final PasswordEncoder passwordEncoder;
    private final SecurityContextService securityContextService;
    private final UserRepository userRepository;
    private final UserServiceImpl userService;

    public UserServiceTests() {
        accountTrackingService = mock(AccountTrackingService.class);
        passwordEncoder = mock(PasswordEncoder.class);
        securityContextService = mock(SecurityContextService.class);
        userRepository = mock(UserRepository.class);

        dataSupplier = new UserMockingDataSupplier(passwordEncoder);

        userService = new UserServiceImpl(
            accountTrackingService,
            passwordEncoder,
            userRepository
        );

    }

    @BeforeEach
    void setup() {
        final Map<String, User> usernames = dataSupplier.map();
        final Answer<Optional<User>> answerWhenFindingUser = (x) -> {
            String username = x.getArgument(0);
            if(usernames.containsKey(username)) {
                return Optional.of(dataSupplier.map().get(username));
            }
            return Optional.empty();
        };
        final Answer<Boolean> answerWhenQueryingExistingUser = (x) -> {
            String username = x.getArgument(0);
            return usernames.containsKey(username);
        };
        when(userRepository.findByUsername(any(String.class))).thenAnswer(answerWhenFindingUser);
        when(userRepository.findById(any(String.class))).thenAnswer(answerWhenFindingUser);
        when(userRepository.existsById(any(String.class))).thenAnswer(answerWhenQueryingExistingUser);
        when(userRepository.existsByUsername(any(String.class))).thenAnswer(answerWhenQueryingExistingUser);
        when(userRepository.save(any(User.class))).thenAnswer(x -> x.getArgument(0));
        when(userRepository.findUsers(any(String.class), any(Pageable.class))).thenAnswer(x -> {
            Pageable p = x.getArgument(1);
            return dataSupplier.slice(p);
        });
    }

    void as(User user) {
        when(securityContextService.getCurrentUser()).thenReturn(Optional.of(user));
    }

    void noUserLoggedIn() {
        when(securityContextService.getCurrentUser()).thenReturn(Optional.empty());
    }

    @Test
    void loadUserByUsername() {
        User test = dataSupplier.pickRandom();
        assertDoesNotThrow(() -> {
            userService.loadUserByUsername(test.getUsername());
        }, "No carga un usuario existente. ");
        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername(test.getUsername() + "%?jfdslfjklkjd");
        }, "¿Carga un usuario no existente?");
    }

    @Test
    void existsByUsername() {
        User test = dataSupplier.pickRandom();
        assertTrue(
            userService.existsByUsername(test.getUsername()),
            "¿No existe usuario @" + test.getUsername() + "?"
        );
        String nonExistentUsername = "asfjlsadkjf_sdfkj";
        assertFalse(
            userService.existsByUsername(nonExistentUsername),
            "¿Existe un usuario @" + nonExistentUsername + "?"
        );
    }

    @Test
    void findByUsername() {
        User test = dataSupplier.pickRandom();
        Optional<User> user = userService.findByUsername(test.getUsername());
        assertTrue(user.isPresent(), "No encuentra @" + test.getUsername() + ". ");
        assertEquals(user.get().getUsername(), test.getUsername());

        String nonExistentUsername = "nonExistentUsername";
        Optional<User> nonExistentSearch = userService.findByUsername(nonExistentUsername);
        assertTrue(nonExistentSearch.isEmpty(), "¿Existe un usuario @" + nonExistentUsername + "?");
    }

    @Test
    void getByUsername() {
        User test = dataSupplier.pickRandom();
        assertDoesNotThrow(() -> {
            User user = userService.getByUsername(test.getUsername());
            assertEquals(user.getUsername(), test.getUsername());
        }, "No encuentra @" + test.getUsername() + ". ");

        String nonExistentUsername = "nonExistentUsername";
        assertThrows(Fail.class, () -> {
            userService.getByUsername(nonExistentUsername);
        }, "¿Existe un usuario @" + nonExistentUsername + "?");
    }

    @Test
    void findAccounts() {
        Pageable pageable = PageRequest.of(0, 10);
        Slice<User> users = userService.findAccounts(pageable, "");
        assertTrue(users.hasContent());
        assertTrue(users.isFirst());
    }

    @Test
    void signup() {
        UserSignupRequest request = new UserSignupRequest();
        request.setName("Alexander");
        request.setSurname("Calgary");
        request.setUsername("alex.calgary");
        request.setPassword("Alex$12345678");
        AtomicReference<User> res = new AtomicReference<>();
        assertDoesNotThrow(() -> res.set(userService.signup(request)));
        assertNotNull(res.get());
        assertEquals(request.getUsername(), res.get().getUsername());
        assertEquals(request.getName(), res.get().getName());
        assertEquals(request.getSurname(), res.get().getSurname());
        verify(userRepository).save(any(User.class));
        verify(accountTrackingService).logActivity(any(AccountEvent.class), any(String.class), any(User.class));
    }

    @Test
    void signup__BadFields() {
        User test = dataSupplier.pickRandom();
        // Bad Name.
        UserSignupRequest badNameRequest = new UserSignupRequest();
        badNameRequest.setName("");
        badNameRequest.setSurname("Calgary");
        badNameRequest.setUsername("alex.calgary");
        badNameRequest.setPassword("Alex$12345678");
        ValidationError badNameValidationError = assertThrows(ValidationError.class, () -> userService.signup(badNameRequest));
        assertEquals("name", badNameValidationError.getField());
        // Bad Surname.
        UserSignupRequest badSurnameRequest = new UserSignupRequest();
        badSurnameRequest.setName("Alex");
        badSurnameRequest.setSurname("");
        badSurnameRequest.setUsername("alex.calgary");
        badSurnameRequest.setPassword("Alex$12345678");
        ValidationError badSurnameValidationError = assertThrows(ValidationError.class, () -> userService.signup(badSurnameRequest));
        assertEquals("surname", badSurnameValidationError.getField());
        // Bad Username (Validation).
        UserSignupRequest badUsernameRequest = new UserSignupRequest();
        badUsernameRequest.setName("Alex");
        badUsernameRequest.setSurname("Calgary");
        badUsernameRequest.setUsername("alex%&$calgary");
        badUsernameRequest.setPassword("Alex$12345678");
        ValidationError badUsernameValidationError = assertThrows(ValidationError.class, () -> userService.signup(badUsernameRequest));
        assertEquals("username", badUsernameValidationError.getField());
        // Bad Username (Existent).
        UserSignupRequest existentUsernameRequest = new UserSignupRequest();
        existentUsernameRequest.setName("Alex");
        existentUsernameRequest.setSurname("Calgary");
        existentUsernameRequest.setUsername(test.getUsername());
        existentUsernameRequest.setPassword("Alex$12345678");
        ValidationError v1 = assertThrows(ValidationError.class, () -> userService.signup(existentUsernameRequest));
        assertEquals("username", v1.getField());
        // Bad Password.
        UserSignupRequest badPasswordRequest = new UserSignupRequest();
        badPasswordRequest.setName("Alex");
        badPasswordRequest.setSurname("Calgary");
        badPasswordRequest.setUsername("alex.calgary");
        badPasswordRequest.setPassword("12345678");
        ValidationError badPasswordValidationError = assertThrows(ValidationError.class, () -> userService.signup(badPasswordRequest));
        assertEquals("password", badPasswordValidationError.getField());
    }

}