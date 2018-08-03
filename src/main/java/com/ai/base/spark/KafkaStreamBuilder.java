package com.ai.base.spark;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import com.ai.base.spark.config.KafkaProperties;
import com.ai.base.spark.config.SparkProperties;
import com.ai.base.spark.entity.InboundEvent;


public  class KafkaStreamBuilder<T extends InboundEvent> {
	



	
	protected KafkaProperties kafkaProperties;
	
	protected JavaStreamingContext jssc;

	protected SparkProperties sparkProperties;
	

//	public static KafkaStreamBuilder<?> create() {
//	
//          return(new KafkaStreamBuilder<>());
//		
//	}
	
	
	
	public KafkaStreamBuilder() {
		//super();
	}
	
	@Autowired
	public KafkaStreamBuilder(JavaStreamingContext jssc, 	
			                  SparkProperties sparkProperties, 
			                  KafkaProperties kafkaProperties 
		) {
		//super();
		this.kafkaProperties = kafkaProperties;
		this.jssc = jssc;
		this.sparkProperties = sparkProperties;
	}

	

	public KafkaStreamBuilder<T> setKafkaProperties(KafkaProperties kafkaProperties) {
		this.kafkaProperties = kafkaProperties;
		return this;
	}

	public KafkaStreamBuilder<T> setJssc(JavaStreamingContext jssc) {
		this.jssc = jssc;
		return this;
	}

	public KafkaStreamBuilder<T> setSparkProperties(SparkProperties sparkProperties) {
		this.sparkProperties = sparkProperties;
		return this;
	}

	@SuppressWarnings("unchecked")
	public JavaDStream<T> build() {
		
		Map<String, Object> kafkaParams = new HashMap<>();

		kafkaParams.put("key.deserializer", kafkaProperties.getKeyDeserializer());
		kafkaParams.put("value.deserializer", kafkaProperties.getValueDeserializer());
		kafkaParams.put("bootstrap.servers", kafkaProperties.getBootstrap());
		kafkaParams.put("group.id", kafkaProperties.getGroupId());
		kafkaParams.put("auto.offset.reset", kafkaProperties.getOffsetReset());
		kafkaParams.put("enable.auto.commit", kafkaProperties.isAutoCommit());
		String topics = kafkaProperties.getTopics();
		Collection<String> topicsSet = Arrays.asList(topics.split(","));

		
		List<JavaDStream<ConsumerRecord<String, InboundEvent>>> streamList = new ArrayList<>(sparkProperties.getStreamCount());

		for (int i = 0; i < sparkProperties.getStreamCount(); i++) {
			streamList.add(KafkaUtils.createDirectStream(jssc, LocationStrategies.PreferConsistent(),
					
					ConsumerStrategies.<String, InboundEvent>Subscribe(topicsSet, kafkaParams))

			);
		}

		JavaDStream<ConsumerRecord<String, InboundEvent>> directKafkaStream;
		
		directKafkaStream = jssc.union(streamList.get(0), streamList.subList(1, streamList.size()));

		JavaDStream<T> dataStream = directKafkaStream.map(cs ->{
				return (T) cs.value();
	  	    });
		
		return dataStream;
		
	}
	
}
