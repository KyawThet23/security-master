package com.example.securitymaster.service;

import com.example.securitymaster.dao.CustomerDao;
import com.example.securitymaster.ds.Customer;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;

    public List<Customer> listAll(String name){
        if (name != null){
            return customerDao.search(name);
        }
        return customerDao.findAll();
    }

    public void saveCustomer(Customer customer){
        customerDao.save(customer);
    }

    public void deleteCustomer(int id){
        customerDao.deleteById(id);
    }

    public boolean exist(int id){
        if (customerDao.existsById(id)){
            return true;
        }else {
            return false;
        }
    }
}
