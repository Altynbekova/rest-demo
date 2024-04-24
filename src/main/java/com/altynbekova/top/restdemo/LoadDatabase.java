package com.altynbekova.top.restdemo;

import com.altynbekova.top.restdemo.dao.PersonRepository;
import com.altynbekova.top.restdemo.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    private static final Logger LOG = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(PersonRepository personRepository){
        return args -> {
            LOG.info("Preloading "+ personRepository.save(new Person("User1")));
            LOG.info("Preloading "+ personRepository.save(new Person("User2")));
        };
    }
}
