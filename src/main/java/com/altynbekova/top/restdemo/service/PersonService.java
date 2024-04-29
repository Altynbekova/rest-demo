package com.altynbekova.top.restdemo.service;

import com.altynbekova.top.restdemo.dao.PersonRepository;
import com.altynbekova.top.restdemo.entity.Person;
import com.altynbekova.top.restdemo.exception.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    public List <Person> findAll(){
        return personRepository.findAll();
    }

    public Person save(Person person){
        return personRepository.save(person);
    }

    public Person findById(long id) {
        return personRepository.findById(id).
        orElseThrow(() -> new PersonNotFoundException(id));
    }

    public void deleteById(long id) {
        personRepository.deleteById(id);
    }

    public Person update(Person person, long id) {
        return personRepository.findById(id).map(p -> {
            p.setName(person.getName());
            return personRepository.save(p);
        }).orElseGet(() -> {
            person.setId(id);
            return personRepository.save(person);
        });
    }

    public List<Person> findByAgeBetween(int min, int max){
        return personRepository.findByAgeBetween(min, max);
    }

    public void deleteByAddress(String pattern) {
        personRepository.deleteByAddressContains(pattern);
    }
}
