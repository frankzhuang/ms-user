package com.frank.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class MsUserApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext applicationContext = SpringApplication.run(MsUserApplication.class, args);

		if (log.isDebugEnabled()) {
			logBeanDefinitionNames(applicationContext.getBeanDefinitionNames());
		}
	}

	private static void logBeanDefinitionNames(String[] names) {
		for (String name: names) {
			log.debug("Loaded Bean: " + name);
		}
	}

}
