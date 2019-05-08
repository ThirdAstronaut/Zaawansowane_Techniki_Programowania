package com.pwr.ztp_lab;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZtpLabApplication {


    public static void main(String[] args) {
        BasicConfigurator.configure();

        SpringApplication.run(ZtpLabApplication.class, args);
    }

}
