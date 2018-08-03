package com.ai.base.spark.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="com.aicuc.streaming.spark")

public class SparkProperties {

	private String appName;
	
	private String master;
	
	private String checkpointPath;
	
	private int interval=2;
	
	private int streamCount = 2;
	

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	public String getCheckpointPath() {
		return checkpointPath;
	}

	public void setCheckpointPath(String checkpointPath) {
		this.checkpointPath = checkpointPath;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public int getStreamCount() {
		return streamCount;
	}

	public void setStreamCount(int streamCount) {
		this.streamCount = streamCount;
	}
	
}
