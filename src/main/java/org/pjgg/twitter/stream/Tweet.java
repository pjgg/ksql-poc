package org.pjgg.twitter.stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.masmovil.avro.TweetAvro;
import java.util.UUID;

public class Tweet {

	private String id;
	private String country;
	private String msg;
	private boolean retweet;
	private long favoriteCount;
	private String lang;
	private User user;

	private Tweet() {
		this.user = User.builder().build();
	}

	public String getId() {
		return id;
	}

	public String getMsg() {
		return msg;
	}

	public String getCountry() {
		return country;
	}

	public boolean isRetweet() {
		return retweet;
	}

	public long getFavoriteCount() {
		return favoriteCount;
	}

	public String getLang() {
		return lang;
	}

	public User getUser() {
		return user;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setRetweet(boolean retweet) {
		this.retweet = retweet;
	}

	public void setFavoriteCount(long favoriteCount) {
		this.favoriteCount = favoriteCount;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static Tweet.TweetBuilder builder() {
		return new Tweet.TweetBuilder();
	}

	public static final class TweetBuilder {

		private Tweet tweet = new Tweet();

		public Tweet build() {
			return tweet;
		}

		public Tweet.TweetBuilder withUser(User user) {
			this.tweet.setUser(user);
			return this;
		}

		public Tweet.TweetBuilder withLang(String lang) {
			this.tweet.setLang(lang);
			return this;
		}

		public Tweet.TweetBuilder withId(String id) {
			this.tweet.setId(id);
			return this;
		}

		public Tweet.TweetBuilder withCountry(String country) {
			this.tweet.setCountry(country);
			return this;
		}

		public Tweet.TweetBuilder withRetweet(boolean retweet) {
			this.tweet.setRetweet(retweet);
			return this;
		}

		public Tweet.TweetBuilder withFavoriteCount(long favoriteCount) {
			this.tweet.setFavoriteCount(favoriteCount);
			return this;
		}

		public Tweet.TweetBuilder withTweetMsg(String msg) {
			this.tweet.setMsg(msg);
			return this;
		}
	}

	public TweetAvro toAvro(){
		return TweetAvro.newBuilder()
			.setCountry(country)
			.setId(id)
			.setMsg(msg)
			.setFavoriteCount(favoriteCount)
			.setLang(lang)
			.setRetweet(retweet)
			.setUser(user.toAvro())
			.build();
	}
}
