package com.example.securitymaster.controller;

import com.example.securitymaster.dao.DepartmentDao;
import com.example.securitymaster.dao.EmployeeDao;
import com.example.securitymaster.ds.Department;
import com.example.securitymaster.ds.Employee;
import com.example.securitymaster.security.annotation.departmant.DepartmenstPagView;
import com.example.securitymaster.security.annotation.departmant.DepartmentsCreate;
import com.example.securitymaster.security.annotation.departmant.DepartmentsDelete;
import com.example.securitymaster.security.annotation.employee.EmployeesCreate;
import com.example.securitymaster.security.annotation.employee.EmployeesDelete;
import com.example.securitymaster.security.annotation.employee.EmployeesPagView;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentDao departmentDao;

    @DepartmenstPagView
    @GetMapping("/departments")
    public ModelAndView findAllDepartments(){
        return new ModelAndView("departments",
                "departments",departmentDao.findAll());
    }

    @DepartmentsDelete
    @GetMapping("/departments/delete")
    public String deleteDepartment(@RequestParam("id") int id){
        if (departmentDao.existsById(id)){
            departmentDao.deleteById(id);
            return "redirect:/department/departments";
        } else {
            throw new EntityNotFoundException(id + " Not found!");
        }
    }

    @DepartmentsCreate
    @GetMapping("/department-form")
    public String departmentForm(Model model){
        model.addAttribute("department",new Department());
        return "department-form";
    }

    @DepartmentsCreate
    @PostMapping("/department-form")
    public String saveDepartment(@Valid Department department, BindingResult result){
        if (result.hasErrors()){
            return "department-form";
        }
        departmentDao.save(department);
        return "redirect:/department/departments";
    }
}
