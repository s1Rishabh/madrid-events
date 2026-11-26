package com.rishabh.madrid_events;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MadridEventsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MadridEventsApplication.class, args);
	}

}
