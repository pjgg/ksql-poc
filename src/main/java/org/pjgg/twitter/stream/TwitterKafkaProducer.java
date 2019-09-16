package org.pjgg.twitter.stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.pjgg.twitter.stream.TopicCompatibility.CompatibilityBuilder.CompatibilityValue;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.StatusListener;

public class TwitterKafkaProducer {

	private final static Logger log = Logger.getLogger(TwitterKafkaProducer.class.getName());
	private final static String TOPIC = "tweets";

	public static void main(String[] args) throws IOException {
		log.info("Init TwitterKafkaProducer.");
		Connectors.INSTANCE.schemaRegistryConnector.setSchemaCompatibility(TOPIC, TopicCompatibility.builder().withCompatibility(CompatibilityValue.NONE).build());

		Connectors.INSTANCE.twitterStreamConnector.addListener(new StatusListener() {

			@Override
			public void onStatus(Status status) {
				log.info("@" + status.getUser().getScreenName() + " - " + status.getText());
				sendToKafka(toTweet(status));
			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) { }

			@Override
			public void onTrackLimitationNotice(int numberOfLimitedStatuses) { }

			@Override
			public void onScrubGeo(long userId, long upToStatusId) { }

			@Override
			public void onStallWarning(StallWarning warning) { }

			@Override
			public void onException(Exception ex) { ex.printStackTrace(); }
		});

		Connectors.INSTANCE.twitterStreamConnector.sample();
	}

	static Tweet toTweet(Status s) {

		Tweet.TweetBuilder tweetBuilder = Tweet.builder();
		User.UserBuilder userBuilder = User.builder();
		tweetBuilder.withId(UUID.randomUUID().toString());

		if(null != s.getPlace()) {
			tweetBuilder.withCountry(s.getPlace().getCountry());
		}

		tweetBuilder.withRetweet(s.isRetweeted());
		tweetBuilder.withLang(s.getLang());
		tweetBuilder.withFavoriteCount(s.getFavoriteCount());
		tweetBuilder.withTweetMsg(s.getText());

		if(null != s.getUser()){
			userBuilder.withId(s.getUser().getId());
			userBuilder.withFollowersAmount(s.getUser().getFollowersCount());
			userBuilder.withLocation(s.getUser().getLocation());
			userBuilder.withName(s.getUser().getName());
			userBuilder.withScreenName(s.getUser().getScreenName());
			tweetBuilder.withUser(userBuilder.build());
		}

		return tweetBuilder.build();
	}


	static void sendToKafka(Tweet t) {
		Connectors.INSTANCE.kafkaProducer.send(new ProducerRecord<>(TOPIC, t.getId(), t.toAvro()));
	}

}
