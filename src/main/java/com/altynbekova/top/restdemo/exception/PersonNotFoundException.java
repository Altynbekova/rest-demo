package com.altynbekova.top.restdemo.exception;

public class PersonNotFoundException extends RuntimeException{
    public PersonNotFoundException(long id) {
        super("Person with id " + id+ " not found");
    }
}
