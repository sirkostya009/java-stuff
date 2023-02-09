package ua.sirkostya009.javastuff;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ua.sirkostya009.javastuff.model.NamedEntity;
import ua.sirkostya009.javastuff.repository.EntityRepository;

import java.util.List;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner runner(EntityRepository repository) {
        return args -> {
            var entity1 = new NamedEntity(1L, "asd");
            var entity2 = new NamedEntity(2L, "123");
            var entity3 = new NamedEntity(3L, "456");
            var entity4 = new NamedEntity(4L, "fgh");

            repository.saveAll(List.of(entity1, entity2, entity3, entity4));
        };
    }
}
