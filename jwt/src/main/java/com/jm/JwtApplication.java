package com.jm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.support.SpringBootServletInitializer;


@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class JwtApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(JwtApplication.class, args);
	}

	/*@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(JwtApplication.class);
	}*/
}
