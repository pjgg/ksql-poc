package org.pjgg.twitter.stream;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class TopicCompatibility {

	private String compatibility;

	public String getCompatibility() {
		return compatibility;
	}

	public void setCompatibility(String compatibility) {
		this.compatibility = compatibility;
	}

	public static TopicCompatibility.CompatibilityBuilder builder() {
		return new TopicCompatibility.CompatibilityBuilder();
	}

	public static final class CompatibilityBuilder {

		enum CompatibilityValue {
			NONE("none"),
			BACKWARD("backward"),
			FORWARD("forward"),
			FULL("full");

			private String type;
			private static final Map<String,CompatibilityValue> lookUp = new HashMap<>();

			static {
				for(CompatibilityValue compatibility : EnumSet.allOf(CompatibilityValue.class))
					lookUp.put(compatibility.getCode(), compatibility);
			}

			private CompatibilityValue(String type){
				this.type = type;
			}

			public String getCode(){
				if(null != type) return type;
				else return  "none";
			}

			public CompatibilityValue getType(final String code){
				if (lookUp.containsKey(code))
					return lookUp.get(code);
				return NONE;
			}

		}

		private TopicCompatibility topicCompatibility = new TopicCompatibility();

		public TopicCompatibility build() {
			return topicCompatibility;
		}

		public TopicCompatibility.CompatibilityBuilder withCompatibility(CompatibilityValue compatibility) {
			this.topicCompatibility.setCompatibility(compatibility.getCode());
			return this;
		}
	}
}