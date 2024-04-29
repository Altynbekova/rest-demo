package com.altynbekova.top.restdemo.contoller;

import com.altynbekova.top.restdemo.dao.PersonRepository;
import com.altynbekova.top.restdemo.entity.Person;
import com.altynbekova.top.restdemo.exception.PersonNotFoundException;
import com.altynbekova.top.restdemo.mapping.PersonModelAssembler;
import com.altynbekova.top.restdemo.service.PersonService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class PersonController {
    private PersonRepository repository;
    private PersonService personService;
    private PersonModelAssembler personModelAssembler;

    public PersonController(PersonRepository repository,
                            PersonService service,
                            PersonModelAssembler personModelAssembler) {
        this.repository = repository;
        this.personService = service;
        this.personModelAssembler = personModelAssembler;
    }

    @GetMapping("/users")
    public CollectionModel<EntityModel<Person>> all() {
        List<EntityModel<Person>> people = personService.findAll().stream()
                .map(person ->
                        personModelAssembler.toModel(person)
                ).collect(Collectors.toList());

        return CollectionModel.of(people,
                linkTo(methodOn(PersonController.class).all()).withRel("users"));
    }

    @PostMapping(value = "/users")
    public ResponseEntity<?> create(@RequestBody Person person) {
        EntityModel<Person> entityModel = personModelAssembler.toModel(personService.save(person));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/users/{id}")
    public EntityModel<Person> find(@PathVariable long id) {
        Person person = personService.findById(id);

        return personModelAssembler.toModel(person);
    }

    @GetMapping("/users/{min}/{max}")
    public CollectionModel<EntityModel<Person>> findByAgeRange(@PathVariable int min,
                                                               @PathVariable int max) {
        List<EntityModel<Person>> people = personService.findByAgeBetween(min, max).stream()
                .map(person ->
                        personModelAssembler.toModel(person)
                ).collect(Collectors.toList());

        return CollectionModel.of(people,
                linkTo(methodOn(PersonController.class).all()).withRel("users"));

    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        personService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/users/address/{pattern}")
    public ResponseEntity<?> deleteByAddress(@PathVariable String pattern){
        personService.deleteByAddress(pattern);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> update(@RequestBody Person person, @PathVariable long id) {
        EntityModel<Person> entityModel = personModelAssembler.toModel(personService.update(person, id));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
}
