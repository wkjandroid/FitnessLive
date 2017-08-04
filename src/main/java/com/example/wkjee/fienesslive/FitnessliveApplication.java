package com.example.wkjee.fienesslive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;

@ServletComponentScan
@SpringBootApplication
public class FitnessliveApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitnessliveApplication.class, args);
	}
}
