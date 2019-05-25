package com.sca.ShoppingCartAppMind;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="com.sca.ShoppingCartAppMind")
@EnableAutoConfiguration(exclude=HibernateJpaAutoConfiguration.class)
public class ShoppingCartAppMindApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingCartAppMindApplication.class, args);
	}

}
