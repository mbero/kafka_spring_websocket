package com.kafka.websocket.utils;

import org.json.JSONObject;

public class JSONUtils {
	
	 public static JSONObject getJSONObjectFromGivenString(String json){
		 
		 JSONObject jsonObject = new JSONObject(json);
		 
		 return jsonObject;
	 }
}
