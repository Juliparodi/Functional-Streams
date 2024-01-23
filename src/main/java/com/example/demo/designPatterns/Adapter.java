package com.example.demo.designPatterns;

import com.example.demo.domain.Employee;
import com.example.demo.framework.EmployeeClient;

import java.util.List;

public class Adapter {

    public static void main() {
        //Arrays.asList is an example
        EmployeeClient client = new EmployeeClient();

        List<Employee> employees = client.getEmployeeList();

        System.out.println(employees);

    }
}
