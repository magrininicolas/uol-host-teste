package dev.nicolas.uolhost;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class UolHostApplication {

	public static void main(String[] args) {
		SpringApplication.run(UolHostApplication.class, args);
	}

}
