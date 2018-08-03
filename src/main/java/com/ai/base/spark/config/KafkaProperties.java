package com.ai.base.spark.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="com.aicuc.streaming.kafka")

public class KafkaProperties {

	private String topics;
	private String bootstrap;
	private String keyDeserializer="org.apache.kafka.common.serialization.StringDeserializer";
	private String valueDeserializer;
	
	private String groupId;
	
	private String offsetReset="latest";
	private boolean autoCommit=false;
	
	public String getTopics() {
		return topics;
	}
	public void setTopics(String topics) {
		this.topics = topics;
	}
	public String getBootstrap() {
		return bootstrap;
	}
	public void setBootstrap(String bootstrap) {
		this.bootstrap = bootstrap;
	}
	public String getKeyDeserializer() {
		return keyDeserializer;
	}
	public void setKeyDeserializer(String keyDeserializer) {
		this.keyDeserializer = keyDeserializer;
	}
	public String getValueDeserializer() {
		return valueDeserializer;
	}
	public void setValueDeserializer(String valueDeserializer) {
		this.valueDeserializer = valueDeserializer;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getOffsetReset() {
		return offsetReset;
	}
	public void setOffsetReset(String offsetReset) {
		this.offsetReset = offsetReset;
	}
	public boolean isAutoCommit() {
		return autoCommit;
	}
	public void setAutoCommit(boolean autoCommit) {
		this.autoCommit = autoCommit;
	}
	
	
}
