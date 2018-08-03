package com.ai.base.spark.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;




@Configuration
@EnableConfigurationProperties({ SparkProperties.class, CassandraProperties.class, KafkaProperties.class,
		MongodbProperties.class })

public class SparkServerConfig {

	@Autowired
	private SparkProperties sparkProperties;

	@Autowired
	private CassandraProperties cassandraProperties;

	@Autowired
	private KafkaProperties kafkaProperties;

	@Autowired
	private MongodbProperties mongodbProperties;

	@Bean
	public SparkConf sparkConf() {
		SparkConf conf = new SparkConf().setAppName(sparkProperties.getAppName()).setMaster(sparkProperties.getMaster())
				.set("spark.cassandra.connection.host", cassandraProperties.getHost())
				.set("spark.cassandra.connection.port", cassandraProperties.getPort())
				.set("spark.cassandra.connection.keep_alive_ms", cassandraProperties.getKeepAlivems());

		return conf;
	}

	@Bean
	public JavaStreamingContext javaStreamingContext() {

		JavaStreamingContext jssc = new JavaStreamingContext(sparkConf(),
				Durations.seconds(sparkProperties.getInterval()));
		// add check point directory

		jssc.checkpoint(sparkProperties.getCheckpointPath());

		return jssc;

	}



//	@Bean
//	public JavaDStream<ConsumerRecord<String, OrderEvent>> javaDStream() {
//	
//	//public JavaDStream<ConsumerRecord<String, Object>> javaDStream() {
//		Map<String, Object> kafkaParams = new HashMap<>();
//
//		kafkaParams.put("key.deserializer", kafkaProperties.getKeyDeserializer());
//		kafkaParams.put("value.deserializer", kafkaProperties.getValueDeserializer());
//
//		kafkaParams.put("bootstrap.servers", kafkaProperties.getBootstrap());
//		kafkaParams.put("group.id", kafkaProperties.getGroupId());
//		kafkaParams.put("auto.offset.reset", kafkaProperties.getOffsetReset());
//		kafkaParams.put("enable.auto.commit", kafkaProperties.isAutoCommit());
//
//		String topics = kafkaProperties.getTopics();
//
//		Collection<String> topicsSet = Arrays.asList(topics.split(","));
//
//		List<JavaDStream<ConsumerRecord<String, OrderEvent>>> streamList = new ArrayList<>(
//		//List<JavaDStream<ConsumerRecord<String, Object>>> streamList = new ArrayList<>(
//				sparkProperties.getStreamCount());
//
//		for (int i = 0; i < sparkProperties.getStreamCount(); i++) {
//			streamList.add(KafkaUtils.createDirectStream(javaStreamingContext(), LocationStrategies.PreferConsistent(),
//					ConsumerStrategies.<String, OrderEvent>Subscribe(topicsSet, kafkaParams))
//				//	ConsumerStrategies.<String, Object>Subscribe(topicsSet, kafkaParams))
//		
//			);
//		}
//
//		JavaDStream<ConsumerRecord<String, OrderEvent>> directKafkaStream = javaStreamingContext()
//	//	JavaDStream<ConsumerRecord<String, Object>> directKafkaStream = javaStreamingContext()
//				.union(streamList.get(0), streamList.subList(1, streamList.size()));
//
//		return directKafkaStream;
//	}

}
