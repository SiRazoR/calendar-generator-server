package com.calendargenerator;

import org.jsondoc.spring.boot.starter.EnableJSONDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableJSONDoc
public class CalendargeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CalendargeneratorApplication.class, args);
    }
}