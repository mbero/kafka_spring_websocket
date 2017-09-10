package com.kafka.websocket.utils;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtils {

	public static JSONObject getJSONObjectFromGivenString(String json) {

		JSONObject jsonObject = new JSONObject(json);
		return jsonObject;
	}

	public static String getJSONStringFromGivenObject(Object objectToTransformIntoJSONString)
			throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(objectToTransformIntoJSONString);

		return jsonInString;
	}
}
