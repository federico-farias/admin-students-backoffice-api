package com.bintics.adminscholls;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
    "com.bintics.adminscholls.domains",
    "com.bintics.adminscholls.shared",
    "com.bintics.adminscholls.config"
})
public class AdminStudentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminStudentsApplication.class, args);
    }

}
