package com.altynbekova.top.restdemo.mapping;

import com.altynbekova.top.restdemo.contoller.CompanyController;
import com.altynbekova.top.restdemo.contoller.PersonController;
import com.altynbekova.top.restdemo.entity.Company;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class CompanyModelAssembler implements RepresentationModelAssembler<Company, EntityModel<Company>> {
    @Override
    public EntityModel<Company> toModel(Company entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(CompanyController.class).find(entity.getId())).withSelfRel(),
                linkTo(methodOn(CompanyController.class).all()).withRel("companies"));
    }
}
