package com.sapient.personnel.mgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PersonnelMgmtApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonnelMgmtApplication.class, args);
    }

}
