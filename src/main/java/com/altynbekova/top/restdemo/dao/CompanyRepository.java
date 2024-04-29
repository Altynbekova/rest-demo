package com.altynbekova.top.restdemo.dao;

import com.altynbekova.top.restdemo.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
