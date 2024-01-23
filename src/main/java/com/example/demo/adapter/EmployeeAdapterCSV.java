package com.example.demo.adapter;

import com.example.demo.domain.Employee;
import com.example.demo.domain.EmployeeCSV;
import lombok.AllArgsConstructor;

public class EmployeeAdapterCSV implements Employee  {

    private final EmployeeCSV employeeFromCSV;

    public EmployeeAdapterCSV(EmployeeCSV employeeFromCSV) {
        this.employeeFromCSV = employeeFromCSV;
    }
    @Override
    public String getId() {
        return String.valueOf(employeeFromCSV.getId());
    }

    @Override
    public String getFirstName() {
        return employeeFromCSV.getFirstName();
    }

    @Override
    public String getLastName() {
        return employeeFromCSV.getLastName();
    }

    @Override
    public String getEmail() {
        return employeeFromCSV.getEmailAddress();
    }
}
