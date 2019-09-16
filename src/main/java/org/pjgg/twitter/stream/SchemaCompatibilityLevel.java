package org.pjgg.twitter.stream;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class SchemaCompatibilityLevel {

	private String compatibilityLevel;

	private String getCompatibilityLevel() {
		return compatibilityLevel;
	}

	public void setCompatibilityLevel(String compatibilityLevel) {
		this.compatibilityLevel = compatibilityLevel;
	}

	public static SchemaCompatibilityLevel.CompatibilityLevelBuilder builder() {
		return new SchemaCompatibilityLevel.CompatibilityLevelBuilder();
	}

	public static final class CompatibilityLevelBuilder {

		enum CompatibilityLevel {
			NONE("none"),
			BACKWARD("backward"),
			FORWARD("forward"),
			FULL("full");

			private String type;
			private static final Map<String,CompatibilityLevel> lookUp = new HashMap<>();

			static {
				for(CompatibilityLevel compatibilityLevel : EnumSet.allOf(CompatibilityLevel.class))
					lookUp.put(compatibilityLevel.getCode(), compatibilityLevel);
			}

			private CompatibilityLevel(String type){
				this.type = type;
			}

			public String getCode(){
				if(null != type) return type;
				else return  "none";
			}

			public CompatibilityLevel getType(final String code){
				if (lookUp.containsKey(code))
					return lookUp.get(code);
				return NONE;
			}

		}

		private SchemaCompatibilityLevel schemaCompatibilityLevel = new SchemaCompatibilityLevel();

		public SchemaCompatibilityLevel build() {
			return schemaCompatibilityLevel;
		}

		public SchemaCompatibilityLevel.CompatibilityLevelBuilder withCompatibilityLevel(CompatibilityLevel compatibility) {
			this.schemaCompatibilityLevel.setCompatibilityLevel(compatibility.getCode());
			return this;
		}
	}
}
