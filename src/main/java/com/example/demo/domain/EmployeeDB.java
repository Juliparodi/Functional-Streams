package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeDB implements Employee {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
}
