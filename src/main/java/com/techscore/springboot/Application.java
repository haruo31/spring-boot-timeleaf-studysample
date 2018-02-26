package com.techscore.springboot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
public class Application  {

    public static final String H2_URL = "jdbc:h2:mem:test";
    public static Connection keepAlive;

    public static void main(String[] args) throws SQLException {
        keepAlive = DriverManager.getConnection(H2_URL);

        keepAlive.prepareCall("create table if not exists chatrec (id int primary key auto_increment, message text)").execute();
        SpringApplication.run(Application.class, args);
    }
}
