package com.example.securitymaster.ds;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "The first name cannot be left blank.")
    @Pattern(regexp = "[A-Za-z-]*", message = "First name cannot contains illegal character.")
    private String firstName;

    @NotBlank(message = "The first name cannot be left blank.")
    @Pattern(regexp = "[A-Za-z-]*", message = "Last name cannot contains illegal character.")
    private String lastName;

    @NotBlank(message = "The phone number cannot be left blank.")
    @Pattern(regexp = "[0-9\\-+]*", message = "Phone number cannot contains illegal character.")
    private String phoneNumber;

    @NotBlank(message = "Address name cannot be left blank.")
    @Pattern(regexp = "[\\w .\\-/,]*", message = "Address cannot contains illegal character.")
    private String address;

    @NotBlank(message = "Number cannot be left blank.")
    @Pattern(regexp = "[A-Za-z0-9\\-]*",message = "Cubical number contains illegal character.")
    private String cubicalNo;

    public Employee() {
    }
    public Employee(String firstName, String lastName, String phoneNumber, String address, String cubicalNo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.cubicalNo = cubicalNo;
    }
}
