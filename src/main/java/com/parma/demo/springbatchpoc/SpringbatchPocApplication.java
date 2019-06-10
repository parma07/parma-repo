package com.parma.demo.springbatchpoc;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class SpringbatchPocApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbatchPocApplication.class, args);
		
		
	}
}

/*
  
Batch Tips
 

Consider marking one of the beans as @Primary, 
updating the consumer to accept multiple beans, 
or using @Qualifier to identify the bean that should be consumed

*/