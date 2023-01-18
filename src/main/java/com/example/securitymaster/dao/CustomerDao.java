package com.example.securitymaster.dao;

import com.example.securitymaster.ds.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerDao extends JpaRepository<Customer,Integer> {

    @Query("SELECT e FROM Customer e WHERE concat(e.firstName,e.lastName) LIKE %:name%")
    List<Customer> search(@Param("name") String name);
}
