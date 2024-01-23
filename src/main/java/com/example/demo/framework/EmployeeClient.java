package com.example.demo.framework;

import com.example.demo.adapter.EmployeeAdapterLdap;
import com.example.demo.domain.Employee;
import com.example.demo.domain.EmployeeCSV;
import com.example.demo.domain.EmployeeDB;
import com.example.demo.domain.EmployeeLdap;

import java.util.ArrayList;
import java.util.List;

public class EmployeeClient {

    public List<Employee> getEmployeeList() {
        List<Employee> employees = new ArrayList<>();
        Employee employeeFromDB = new EmployeeDB("1234", "john", "parodi", "julianparodi19@gmail.com");
        employees.add(employeeFromDB);

        EmployeeLdap employeeFromLdap = new EmployeeLdap("hello", "parodi", "john", "aloha@gmail.com");

        employees.add(new EmployeeAdapterLdap(employeeFromLdap));

        EmployeeCSV employeeFromCSV = new EmployeeCSV("567,SHerlock,Holmes,sherlock@holmes.com");


        return employees;
    }
}
