package ru.javaops.topjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.javaops.topjava.repository.DishRepository;

@SpringBootApplication
public class CaloriesManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(CaloriesManagementApplication.class, args);
    }
}
