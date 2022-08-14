package ru.kirakosyan;

import org.springframework.stereotype.Service;
import ru.kirakosyan.persist.User;
import ru.kirakosyan.persist.UserRepository;

import javax.annotation.PostConstruct;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        System.out.println("init");
    }

    public void insert(User user) {
        if (user.getRole().equals("ADMIN") || user.getRole().equals("GUEST")) {
            this.userRepository.insert(user);
        } else {
            throw new IllegalArgumentException("Incorrect role");
        }
    }

    public int findAll() {
        return userRepository.findAll().size();
    }
}
