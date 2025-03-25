package com.gm.BankXApp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;

@SpringBootApplication
public class BankXAppApplication {

	@Value("${spring.datasource.url}")
	private String dbUrl;

	public static void main(String[] args) {
		SpringApplication.run(BankXAppApplication.class, args);
	}

	@Bean
	public CommandLineRunner logDbFile() {
		return args -> {
			String path = dbUrl.replace("jdbc:sqlite:", "");
			File dbFile = new File(path);
			System.out.println("GTest Using SQLite DB file at: " + dbFile.getAbsolutePath());
			System.out.println("GTest DB file exists? " + dbFile.exists());
			System.out.println("GTest DB file size: " + dbFile.length() + " bytes");
		};
	}
}
