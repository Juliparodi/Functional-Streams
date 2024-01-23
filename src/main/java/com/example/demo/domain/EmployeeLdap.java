package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeLdap {

    private String cn;
    private String surname;
    private String givenName;
    private String mail;

}
