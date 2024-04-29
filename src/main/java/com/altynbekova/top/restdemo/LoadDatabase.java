package com.altynbekova.top.restdemo;

import com.altynbekova.top.restdemo.dao.CompanyRepository;
import com.altynbekova.top.restdemo.dao.PersonRepository;
import com.altynbekova.top.restdemo.entity.Company;
import com.altynbekova.top.restdemo.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class LoadDatabase {
    private static final Logger LOG = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(PersonRepository personRepository, CompanyRepository companyRepository){
        return args -> {
            Person person = personRepository.save(new Person("User1",
                    34, "City1 Street1 Building1"));

            LOG.info("Preloading "+ person);
            LOG.info("Preloading "+ personRepository.save(new Person("User2",
                    87, "City2 Street2 Building2")));
            LOG.info("Preloading "+ companyRepository.save(new Company("Company1", Set.of(person))));
        };
    }
}
