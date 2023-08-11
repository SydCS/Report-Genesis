package com.gen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@ServletComponentScan
public class ChainApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChainApplication.class, args);
    }

}
