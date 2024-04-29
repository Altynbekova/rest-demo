package com.altynbekova.top.restdemo.dao;

import com.altynbekova.top.restdemo.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findByAgeBetween(int min, int max);

    void deleteByAddressContains(String address);
}
