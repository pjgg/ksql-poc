package org.pjgg.twitter.stream;

import com.masmovil.avro.TweetAvro;
import com.masmovil.avro.UserAvro;

public class User {
	private long id;
	private String name;
	private String screenName;
	private String location;
	private long followersAmount;

	private User() { }

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getLocation() {
		return location;
	}

	public long getFollowersAmount() {
		return followersAmount;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setFollowersAmount(long followersAmount) {
		this.followersAmount = followersAmount;
	}

	public static User.UserBuilder builder() {
		return new User.UserBuilder();
	}

	public static final class UserBuilder {
		private User user = new User();

		public User build() {
			return user;
		}

		public User.UserBuilder withId(long id) {
			this.user.setId(id);
			return this;
		}

		public User.UserBuilder withName(String name) {
			this.user.setName(name);
			return this;
		}

		public User.UserBuilder withScreenName(String screenName) {
			this.user.setScreenName(screenName);
			return this;
		}

		public User.UserBuilder withLocation(String location) {
			this.user.setLocation(location);
			return this;
		}

		public User.UserBuilder withFollowersAmount(long followersAmount) {
			this.user.setFollowersAmount(followersAmount);
			return this;
		}
	}

	public UserAvro toAvro(){
		return UserAvro.newBuilder()
			.setId(id)
			.setLocation(location)
			.setName(name)
			.setFollowersAmount(followersAmount)
			.setScreenName(screenName)
			.build();
	}
}
