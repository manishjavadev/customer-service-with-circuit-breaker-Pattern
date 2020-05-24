package com.manish.javadev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class SpringbootApplication {

	/**
	 * Used as a marker annotation indicating that the annotated RestTemplate should
	 * use a RibbonLoadBalancerClient for interacting with your service(s).
	 * 
	 * In turn, this allows you to use "logical identifiers" for the URLs you pass
	 * to the RestTemplate. These logical identifiers are typically the name of a
	 * service. For example:
	 * 
	 * restTemplate.getForObject("http://some-service-name/user/{id}",
	 * String.class);
	 * 
	 * where some-service-name is the logical identifier for your service which you
	 * configured in application.properties file.
	 * 
	 * @return
	 */
	@LoadBalanced
	@Bean
	RestTemplate loadBalanced() {
		return new RestTemplate();
	}

	@Primary
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}

}
