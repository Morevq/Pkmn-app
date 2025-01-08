package su.morevq.pkmn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // Обязательная аннотация!
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args); // Обязательный метод
    }
}
