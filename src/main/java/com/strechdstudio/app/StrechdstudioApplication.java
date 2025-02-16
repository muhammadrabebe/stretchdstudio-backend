package com.strechdstudio.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//@SpringBootApplication(scanBasePackages = {"com.stertchdstudio.app", "com.stertchdstudio.app.model"})
//@ComponentScan(basePackages = "com.stertchdstudio.app")
@SpringBootApplication
public class StrechdstudioApplication {

	public static void main(String[] args) {
		SpringApplication.run(StrechdstudioApplication.class, args);
	}

}
