package com.ai.base.spark.entity;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

import java.io.Serializable;

import org.bson.Document;
import org.bson.conversions.Bson;

public class OutboundData implements Serializable {

	public void setDocument(Document doc){
		
		
	}
	
	public Bson getCondition(){
		
		return null;
		
	}
	
	public Bson getUpdateValue(){
		
		return null;
		
	}
	
}
