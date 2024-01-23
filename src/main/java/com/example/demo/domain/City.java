package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class City {

    private String name;
    private List<User> userList;
}
