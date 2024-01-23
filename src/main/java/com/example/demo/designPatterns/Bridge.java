package com.example.demo.designPatterns;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Bridge {

    public static void main() {
        //Drivers like JDBC are examples
        try {

            //JDBC is an API
            //DriverManager.registerDriver(new org.h2.Driver());
            Connection conn = DriverManager.getConnection("jdbc:h2:mem:");

            Statement sta = conn.createStatement();

            //this client is an abstraction and can work with any database that has a driver
            sta.executeUpdate("CREATE TABLE Address (ID INT, STREET NAME VARCHAR(20), CITY VARCHAR(20))");

            System.out.println("Table created");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
