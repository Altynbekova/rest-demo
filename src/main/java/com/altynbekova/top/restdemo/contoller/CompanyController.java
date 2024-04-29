package com.altynbekova.top.restdemo.contoller;

import com.altynbekova.top.restdemo.entity.Company;
import com.altynbekova.top.restdemo.entity.Person;
import com.altynbekova.top.restdemo.mapping.CompanyModelAssembler;
import com.altynbekova.top.restdemo.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CompanyController {
    private CompanyService companyService;

    private CompanyModelAssembler companyModelAssembler;

    @GetMapping("/companies")
    public CollectionModel<EntityModel<Company>> all() {
        List<EntityModel<Company>> people = companyService.findAll().stream()
                .map(company ->
                        companyModelAssembler.toModel(company)
                ).collect(Collectors.toList());

        return CollectionModel.of(people,
                linkTo(methodOn(PersonController.class).all()).withRel("companies"));
    }

    @GetMapping("/companies/{id}")
    public EntityModel<Company> find(@PathVariable long id) {
        return companyModelAssembler.toModel(companyService.findById(id));
    }
}
