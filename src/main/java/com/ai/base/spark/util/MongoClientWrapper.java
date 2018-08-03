package com.ai.base.spark.util;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;


import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;

public class MongoClientWrapper {

	private MongoCollection<Document> collection;
	private MongoClient mongoClient;

	private MongodbConfig mongodbProperties;

	public void setMongoConfig(MongodbConfig mongoConfig) {
		this.mongodbProperties = mongoConfig;
	}

	public void connection() {

		final String uriString = mongodbProperties.getUri() + "/" + mongodbProperties.getDatabase();
		MongoClientURI uri = new MongoClientURI(uriString);
		mongoClient = new MongoClient(uri);
		MongoDatabase mongoDB = mongoClient.getDatabase(mongodbProperties.getDatabase());
		collection = mongoDB.getCollection(mongodbProperties.getCollection());

	}

	public void disConnection() {

		mongoClient.close();
	}

	public void insertOne(Document record) {

		collection.insertOne(record);

	}

	public void updateOne(Bson arg0, Bson arg1) {

		collection.updateOne(arg0, arg1, new UpdateOptions().upsert(true).bypassDocumentValidation(true));

	}
	
	public List<Document> find(BasicDBObject searchQuery) {

		
		MongoCursor<Document> docs = collection.find(searchQuery).iterator();
		
		List<Document> docList = new ArrayList<Document>();
		
		while(docs.hasNext()){
			
			docList.add(docs.next());
			
		}
		return docList;
		
	}
}

