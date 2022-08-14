package ru.kirakosyan;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.kirakosyan.config.AppConfiguration;
import ru.kirakosyan.persist.User;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);

        UserService userService = context.getBean("userService", UserService.class);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter user name: ");
            String userName = scanner.nextLine();

            System.out.println("Enter user role: ");
            String userRole = scanner.nextLine();

            userService.insert(new User(userName, userRole));

            System.out.println("New user has been added. Current users count: " + userService.findAll());

            System.out.println("Enter \"end\" to exit");
            if (scanner.nextLine().equals("end")) {
                return;
            }
        }
    }
}
