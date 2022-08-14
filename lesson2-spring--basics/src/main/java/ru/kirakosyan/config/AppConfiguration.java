package ru.kirakosyan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kirakosyan.UserService;
import ru.kirakosyan.persist.UserRepository;
import ru.kirakosyan.persist.UserRepositoryImpl;

@Configuration
public class AppConfiguration {
    @Bean
    public UserRepository userRepository() {
        return new UserRepositoryImpl();
    }

    @Bean
    public UserService userService(UserRepository userRepository) {
        return new UserService(userRepository);
    }
}
