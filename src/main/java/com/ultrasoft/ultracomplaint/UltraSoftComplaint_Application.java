package com.ultrasoft.ultracomplaint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class UltraSoftComplaint_Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(UltraSoftComplaint_Application.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(UltraSoftComplaint_Application.class);
	}
}
