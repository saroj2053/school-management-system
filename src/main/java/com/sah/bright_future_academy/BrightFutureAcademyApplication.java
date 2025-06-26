package com.sah.bright_future_academy;

import com.sah.bright_future_academy.repo.ContactRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.sah.bright_future_academy.repo")
@EntityScan("com.sah.bright_future_academy.model")
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class BrightFutureAcademyApplication {

	public static void main(String[] args) {
		SpringApplication.run(BrightFutureAcademyApplication.class, args);
	}

}
