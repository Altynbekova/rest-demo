package com.altynbekova.top.restdemo.service;

import com.altynbekova.top.restdemo.dao.CompanyRepository;
import com.altynbekova.top.restdemo.entity.Company;
import com.altynbekova.top.restdemo.entity.Person;
import com.altynbekova.top.restdemo.exception.CompanyNotFoundException;
import com.altynbekova.top.restdemo.exception.PersonNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> findAll(){
        return companyRepository.findAll();
    }

    public Company findById(long id) {
        return companyRepository.findById(id).
                orElseThrow(() -> new CompanyNotFoundException(id));
    }
}
