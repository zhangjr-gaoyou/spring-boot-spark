package com.ai.base.spark.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="com.aicuc.streaming.cassandra")

public class CassandraProperties {

	private String host;
	
	private String port;
	
	private String keepAlivems;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getKeepAlivems() {
		return keepAlivems;
	}

	public void setKeepAlivems(String keepAlivems) {
		this.keepAlivems = keepAlivems;
	}

	
	
}
