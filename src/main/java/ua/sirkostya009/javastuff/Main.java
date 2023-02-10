package ua.sirkostya009.javastuff;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import ua.sirkostya009.javastuff.model.NamedEntity;
import ua.sirkostya009.javastuff.repository.EntityRepository;

import java.util.List;

import static java.util.List.of;

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

    @Bean
    public CorsFilter corsFilter() {
        var config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(of("http://localhost:4200", "http://localhost:3000"));
        config.setAllowedHeaders(of(
                "Origin", "Access-Control-Allow-Origin", "Content-Type", "Accept", "Authorization", "Origin, Accept",
                "X-Requested-With", "Access-Control-Request-Method", "Access-Control-Request-Headers"
        ));
        config.setAllowedMethods(of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        var configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);

        return new CorsFilter(configSource);
    }
}
