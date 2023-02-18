package ua.sirkostya009.javastuff;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ua.sirkostya009.javastuff.repository.PublicFigureRepository;

@Slf4j
@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = PublicFigureRepository.class)
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
