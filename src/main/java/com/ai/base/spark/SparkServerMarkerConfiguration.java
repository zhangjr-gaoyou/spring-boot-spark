package com.ai.base.spark;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SparkServerMarkerConfiguration {

	@Bean
	public Marker sparkServerMarkerBean() {
		return new Marker();
	}

	class Marker {
	}
	
}
