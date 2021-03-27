package com.prototype.organisation;




import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;




@ServletComponentScan
@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
public class OrganisationApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrganisationApplication.class, args);
	}

}
