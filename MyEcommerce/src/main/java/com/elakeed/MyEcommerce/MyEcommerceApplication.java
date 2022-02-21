package com.elakeed.MyEcommerce;

import com.elakeed.MyEcommerce.repository.TagNameDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyEcommerceApplication implements CommandLineRunner {

	@Autowired
	private TagNameDAO tagNameDAO;
	public static void main(String[] args) {
		SpringApplication.run(MyEcommerceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
