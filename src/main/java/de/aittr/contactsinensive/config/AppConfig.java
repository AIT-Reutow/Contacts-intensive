package de.aittr.contactsinensive.config;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class AppConfig {

    @Bean
    public Faker getFaker() {
        return new Faker(Locale.ENGLISH);
    }
}
