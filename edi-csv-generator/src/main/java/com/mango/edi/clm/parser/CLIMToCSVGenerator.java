package com.mango.edi.clm.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.mango.edi")
public class CLIMToCSVGenerator {
    @Autowired
    private EDIToCSVParsingEngine ediToCSVParsingEngine;

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(CLIMToCSVGenerator.class);
        builder.run(args);
    }
}
