package com.example.securitymaster.controller;

import com.example.securitymaster.dao.CustomerDao;
import com.example.securitymaster.ds.Customer;
import com.example.securitymaster.security.annotation.customer.CustomersCreate;
import com.example.securitymaster.security.annotation.customer.CustomersDelete;
import com.example.securitymaster.security.annotation.customer.CustomersPagView;
import com.example.securitymaster.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private CustomerService customerService;


    @CustomersPagView
    @GetMapping("/customers")
    public String listCustomer(Model model, @Param("name") String name){
        List<Customer> customers = customerService.listAll(name);
        model.addAttribute("name",name);
        model.addAttribute("customers",customers);
        return "customers";
    }

    @CustomersCreate
    @GetMapping("/customer-form")
    public String customerForm(Model model){
        model.addAttribute("customer",new Customer());
        return "customer-form";
    }

    @CustomersCreate
    @PostMapping("/customer-form")
    public String saveCustomer(@Valid Customer customer, BindingResult result){
        if (result.hasErrors()){
            return "customer-form";
        }
        customerService.saveCustomer(customer);
        return "redirect:/customer/customers";
    }

    @CustomersDelete
    @GetMapping("/customers/delete")
    public String deleteCustomer(@RequestParam("id") int id){
        if (customerService.exist(id)){
            customerService.deleteCustomer(id);
            return "redirect:/customer/customers";
        } else {
            throw new EntityNotFoundException(id + " Not found!");
        }
    }
}