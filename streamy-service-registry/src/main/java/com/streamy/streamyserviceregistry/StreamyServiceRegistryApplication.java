package com.streamy.streamyserviceregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class StreamyServiceRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreamyServiceRegistryApplication.class, args);
	}

}
