package com.altynbekova.top.restdemo.contoller;

import com.altynbekova.top.restdemo.dao.PersonRepository;
import com.altynbekova.top.restdemo.entity.Person;
import com.altynbekova.top.restdemo.exception.PersonNotFoundException;
import com.altynbekova.top.restdemo.mapping.PersonModelAssembler;
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
    private PersonModelAssembler personModelAssembler;

    public PersonController(PersonRepository repository, PersonModelAssembler personModelAssembler) {
        this.repository = repository;
        this.personModelAssembler=personModelAssembler;
    }

    @GetMapping("/users")
    public CollectionModel<EntityModel<Person>> all() {
        List<EntityModel<Person>> people = repository.findAll().stream()
                .map(person ->
                    personModelAssembler.toModel(person)
                ).collect(Collectors.toList());

        return CollectionModel.of(people,
                linkTo(methodOn(PersonController.class).all()).withRel("users"));
    }

    @PostMapping(value = "/users")
    public ResponseEntity<?> create(@RequestBody Person person) {
        EntityModel<Person> entityModel = personModelAssembler.toModel(repository.save(person));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/users/{id}")
    public EntityModel<Person> find(@PathVariable long id) {
        Person person = repository.findById(id).
                orElseThrow(() -> new PersonNotFoundException(id));

        return personModelAssembler.toModel(person);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> update(@RequestBody Person person, @PathVariable long id) {
        Person personToUpdate = repository.findById(id).map(p -> {
            p.setName(person.getName());
            return repository.save(p);
        }).orElseGet(() -> {
            person.setId(id);
            return repository.save(person);
        });
        EntityModel<Person> entityModel = personModelAssembler.toModel(personToUpdate);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
}
