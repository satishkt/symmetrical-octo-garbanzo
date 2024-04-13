package com.mango.edi.config;

import com.berryworks.edireader.EDIReader;
import com.mango.edi.clm.parser.exceptions.LogSyntaxExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class CLIMToCSVConfiguration {

    public final static String NEW_LINE = System.getProperty("line.separator");


    @Autowired
    LogSyntaxExceptionHandler logSyntaxExceptionHandler;

    @Value("${is.namespace.enabled}")
    private boolean isNameSapceEnabled;

    @Bean
    public EDIReader ediReader() {
        var ediReader = new EDIReader();
        ediReader.setSyntaxExceptionHandler(logSyntaxExceptionHandler);
        ediReader.setNamespaceEnabled(isNameSapceEnabled);
        return ediReader;
    }




}
