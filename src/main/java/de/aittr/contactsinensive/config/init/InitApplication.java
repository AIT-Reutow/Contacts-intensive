package de.aittr.contactsinensive.config.init;

import com.github.javafaker.Faker;
import de.aittr.contactsinensive.entity.Contact;
import de.aittr.contactsinensive.entity.User;
import de.aittr.contactsinensive.persistence.ContactRepository;
import de.aittr.contactsinensive.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import static java.util.Map.entry;

@Component
@RequiredArgsConstructor
public class InitApplication implements CommandLineRunner {

    private static String PASSWORD = "MyPass007!";
    private static final Map<String, UserDto> USERS = Map.ofEntries(
            entry("john.doe@example.com", new UserDto("John", "Doe", "john.doe@example.com")),
            entry("maria.neumann@example.com", new UserDto("Maria", "Neumann", "maria.neumann@example.com")),
            entry("max.schulz@example.com", new UserDto("Max", "Schulz", "max.schulz@example.com")),
            entry("jack.black@example.com", new UserDto("Jack", "Black", "jack.black@example.com")),
            entry("unknown.black@example.com", new UserDto("Jane", "Black", "unknown.black@example.com")),
            entry("anna.mueller@example.com", new UserDto("Anna", "Müller", "anna.mueller@example.com")),
            entry("peter.krause@example.com", new UserDto("Peter", "Krause", "peter.krause@example.com")),
            entry("sophia.lehmann@example.com", new UserDto("Sophia", "Lehmann", "sophia.lehmann@example.com")),
            entry("leon.hoffmann@example.com", new UserDto("Leon", "Hoffmann", "leon.hoffmann@example.com")),
            entry("emma.fischer@example.com", new UserDto("Emma", "Fischer", "emma.fischer@example.com")),
            entry("liam.becker@example.com", new UserDto("Liam", "Becker", "liam.becker@example.com")),
            entry("noah.schmidt@example.com", new UserDto("Noah", "Schmidt", "noah.schmidt@example.com")),
            entry("mia.bauer@example.com", new UserDto("Mia", "Bauer", "mia.bauer@example.com")),
            entry("elias.wolf@example.com", new UserDto("Elias", "Wolf", "elias.wolf@example.com")),
            entry("charlotte.klein@example.com", new UserDto("Charlotte", "Klein", "charlotte.klein@example.com"))
    );

    private final UserRepository userRepository;
    private final ContactRepository contactRepository;
    private final Faker faker;
    private final PasswordEncoder encoder;

    @Override
    @Transactional
    public void run(String... args) {

        PASSWORD = encoder.encode(PASSWORD);

        Map<String, User> userMap = persistUsers();
        for (Map.Entry<String, User> userEntry : userMap.entrySet()) {
            User user = userEntry.getValue();

            String randomAddress = faker.address().fullAddress();
            String randomPhone = faker.phoneNumber().phoneNumber();
            String email = user.getEmail();
            UserDto userDto = USERS.get(email);
            String firstName = userDto.userFirstname;
            String lastName = userDto.userLastname;

            Contact myContact = new Contact(
                    user,
                    firstName,
                    lastName,
                    randomAddress,
                    randomPhone,
                    email
            );

            contactRepository.save(myContact);
            user.setMyContact(myContact);

            addRandomContactsToUser(user, new Random().nextInt(10, 30));
        }
    }

    //    @Transactional
    public void addRandomContactsToUser(User user, int count) {
        List<Contact> contacts = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Contact randomContact = getRandomContact(user);
            contactRepository.save(randomContact);
            contacts.add(randomContact);
        }
//        contactRepository.saveAll(contacts);
        user.getContacts().addAll(contacts);
    }

    public Contact getRandomContact(User user) {
        String randomAddress = faker.address().fullAddress();
        String randomPhone = faker.phoneNumber().phoneNumber();
        String randomEmail = faker.internet().emailAddress();
        String randomFirstName = faker.name().firstName();
        String randomLastName = faker.name().lastName();

        return new Contact(user,
                randomFirstName,
                randomLastName,
                randomAddress,
                randomPhone,
                randomEmail
        );
    }

    @Transactional
    public Map<String, User> persistUsers() {
        return USERS.entrySet().stream()
                .filter(entry -> !userRepository.isUserExistByEmail(entry.getValue().userEmail()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> userRepository.save(entry.getValue().toUser())
                ));
    }

    private record UserDto(String userFirstname, String userLastname, String userEmail) {
        public User toUser() {
            return new User(userEmail, PASSWORD);
        }
    }
}
