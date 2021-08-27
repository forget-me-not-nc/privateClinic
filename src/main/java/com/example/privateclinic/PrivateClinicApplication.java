package com.example.privateclinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PrivateClinicApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(PrivateClinicApplication.class, args);
	}

}
