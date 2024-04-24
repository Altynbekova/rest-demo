package com.altynbekova.top.restdemo.mapping;

import com.altynbekova.top.restdemo.contoller.PersonController;
import com.altynbekova.top.restdemo.entity.Person;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PersonModelAssembler implements RepresentationModelAssembler<Person, EntityModel<Person>> {
    @Override
    public EntityModel<Person> toModel(Person entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(PersonController.class).find(entity.getId())).withSelfRel(),
                linkTo(methodOn(PersonController.class).all()).withRel("users"));
    }
}
