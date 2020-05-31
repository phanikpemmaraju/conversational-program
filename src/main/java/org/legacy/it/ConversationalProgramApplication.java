package org.legacy.it;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ConversationalProgramApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConversationalProgramApplication.class, args);
	}

}
