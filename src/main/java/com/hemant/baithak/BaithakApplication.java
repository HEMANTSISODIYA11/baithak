package com.hemant.baithak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("com.hemant.baithak.model")
@SpringBootApplication
public class BaithakApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaithakApplication.class, args);
	}

}


//https://www.baeldung.com/java-websockets

//https://spring.io/guides/gs/messaging-stomp-websocket

//json or the jackson library to convert the json to java and vice versa