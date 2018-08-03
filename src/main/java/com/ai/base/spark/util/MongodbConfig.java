package com.ai.base.spark.util;


import java.io.Serializable;

public class MongodbConfig implements Serializable {

	public MongodbConfig(String uri, String database, String collection) {
		super();
		this.uri = uri;
		this.database = database;
		this.collection = collection;
	}

	private String uri;
	private String database;
	
	private String collection;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getCollection() {
		return collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	
}

