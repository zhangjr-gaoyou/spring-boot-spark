package com.ai.base.spark.util;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

import com.ai.base.spark.config.MongodbProperties;

import com.ai.base.spark.entity.OutboundData;
import com.mongodb.BasicDBObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.spark.streaming.api.java.JavaDStream;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;

public class MongodbWriter<T extends OutboundData> implements Serializable {

	private MongodbConfig mongoConfig;

	private String className;

	
	
	@Autowired
	public MongodbWriter(String className, MongodbProperties mongodbProperties) {

		
		this.className = className;
		
		this.mongoConfig = new MongodbConfig(
				 mongodbProperties.getUri(),
				 mongodbProperties.getDatabase(),
				 mongodbProperties.getCollection());
	}

	public List<T> read(BasicDBObject searchQuery) {

		List<T> dataList = new ArrayList<T>();

		// mongodb.connect
		MongoClientWrapper mcw = new MongoClientWrapper();
		mcw.setMongoConfig(mongoConfig);
		mcw.connection();

//		BasicDBObject searchQuery = new BasicDBObject();
//		searchQuery.put("recordDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

		List<Document> docList = mcw.find(searchQuery);

		Class<? extends OutboundData> type;
		OutboundData data = null;

		try {
			type = (Class<? extends OutboundData>) ClassUtils.forName(className, null);

			data = BeanUtils.instantiateClass(type);
		} catch (Exception e) {

		}

		for (Document doc : docList) {

			// TotalProducts prod = new TotalProducts();
			// prod.setProductId(doc.get("productId").toString());
			// prod.setTotalCount(Long.parseLong(doc.get("totalCount").toString()));
			// prod.setRecordDate(doc.get("recordDate").toString());
			// prod.setTimeStamp(new
			// Date(Date.parse(doc.get("timeStamp").toString())));

			((T) data).setDocument(doc);

			dataList.add((T) data);

		}

		return dataList;

	}

	public void save(JavaDStream<T> outDStream) {
		outDStream.foreachRDD(rdd -> {

			rdd.foreachPartition(records -> {

				// mongodb.connect
				MongoClientWrapper mcw = new MongoClientWrapper();
				mcw.setMongoConfig(mongoConfig);
				mcw.connection();

				records.forEachRemaining(record -> {

					// mongodb.save
//					mcw.updateOne(and(eq("productId", record.getProductId()), eq("recordDate", record.getRecordDate())),
//							combine(set("totalCount", record.getTotalCount()), set("timeStamp", record.getTimeStamp())
//
//							));
					
					mcw.updateOne(((T) record).getCondition(), ((T) record).getUpdateValue());
				});

				// mongodb.disconnect
				mcw.disConnection();
			});

		});

	}

	public void setMongoConfig(MongodbConfig mongoConfig) {

		this.mongoConfig = mongoConfig;

	}
}
