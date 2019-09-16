package org.pjgg.twitter.stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class SchemaRegistryConnector {

	private final static MediaType SCHEMA_CONTENT = MediaType.parse("application/vnd.schemaregistry.v1+json");

	private final OkHttpClient httpClient;
	private final ObjectMapper objectMapper;
	private final String schemaRegistryURL;

	public SchemaRegistryConnector(final String schemaRegistryURL) {
		httpClient = new OkHttpClient();
		objectMapper = new ObjectMapper();
		this.schemaRegistryURL = schemaRegistryURL;
	}


	public boolean createSchema(final String topic, final String schema) {
		return true;
	}

	public List<String> getAllSchemasNames() {
		return new ArrayList<>();
	}

	public int getVersionsAmount() {
		return 0;
	}

	public SchemaRegistryRecord getSchema(final String topic, final long version) {
		return new SchemaRegistryRecord();
	}

	public SchemaRegistryRecord getLatestSchema(final String topic) {
		return new SchemaRegistryRecord();
	}

	public boolean isSchemaRegistered(final String topic, final String schemaAsJson) {
		return true;
	}

	public boolean isSchemaCompatible(final String topic, final String schemaAsJson) {
		return true;
	}

	public String getSchemaCompatibility() {
		return "None";
	}

	public boolean setSchemaCompatibility(final SchemaCompatibilityLevel compatibility) {
		return true;
	}

	public String getSchemaCompatibility(final String topic) throws IOException {

		String result = "UNKNOWN";

		Request request = new Request.Builder()
			.url(schemaRegistryURL + "/config/" + topic)
			.build();

		switch (httpClient.newCall(request).execute().code()) {
			case 200:
			case 201:
				result = objectMapper.readTree(httpClient.newCall(request).execute().body().string()).get("compatibilityLevel").asText();
				break;
		}

		return result;
	}

	public boolean setSchemaCompatibility(final String topic, final TopicCompatibility compatibility)
		throws IOException {

		boolean result = false;

		Request request = new Request.Builder()
			.put(RequestBody.create(SCHEMA_CONTENT, objectMapper.writeValueAsString(compatibility)))
			.url(schemaRegistryURL + "/config/" + topic)
			.build();

		switch (httpClient.newCall(request).execute().code()) {
			case 200:
			case 201:
				result = true;
				break;
		}

		return result;
	}

}
