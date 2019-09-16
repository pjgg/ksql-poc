package org.pjgg.twitter.stream;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.conf.Configuration;

public enum Connectors {

	INSTANCE;

	TwitterStream twitterStreamConnector;
	KafkaProducer kafkaProducer;
	SchemaRegistryConnector schemaRegistryConnector;

	Connectors() {
		twitter4j.conf.Configuration twitter_conf = new ConfigurationBuilder()
			.setOAuthConsumerKey("YOUR_CONSUMER_KEY")
			.setOAuthConsumerSecret("YOUR_CONSUMER_SECRET")
			.setOAuthAccessToken("OAUTH_ACCESS_TOKEN")
			.setOAuthAccessTokenSecret("OAUTH_ACCESS_TOKEN_SECRET")
			.build();

		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(ProducerConfig.CLIENT_ID_CONFIG, "TwitterStreamProducer");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
		props.put(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
		kafkaProducer = new KafkaProducer<>(props);
		twitterStreamConnector = new TwitterStreamFactory(twitter_conf).getInstance();
		schemaRegistryConnector = new SchemaRegistryConnector("http://localhost:8081");
	}

}
