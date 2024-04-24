package com.altynbekova.top.restdemo.dao;

import com.altynbekova.top.restdemo.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
