package com.lomalan.main;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class F1BotApplication implements CommandLineRunner {

	public static void main(String[] args) {

		new SpringApplication(F1BotApplication.class).run(args);
	}

	@Override
	public void run(String[] args) throws Exception {
		// For now does nothing
	}
}
