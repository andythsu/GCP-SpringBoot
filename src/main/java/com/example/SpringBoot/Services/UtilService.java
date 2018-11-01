package com.example.SpringBoot.Services;

import org.json.JSONException;
import org.json.JSONObject;


public class UtilService {

    public class commonNames{
        public static final String JSON = "Json";
        public static final String CREATEDAT = "CreatedAt";
        public static final String EXPIREDAT = "ExpiredAt";
    }

    public static JSONObject parseToJSON(String json) {
        try {
            return new JSONObject(json);
        } catch (JSONException e) {
            json = json.replace("\\","\\\\");
            try{
                return new JSONObject(json);
            } catch(JSONException ee){
                return null;
            }
        }
    }

    public static JSONObject deepMergeJSON(JSONObject source, JSONObject target) throws JSONException {
        for (String key : JSONObject.getNames(source)) {
            Object source_value = source.get(key);
            if (!target.has(key)) {
                // new value for "key":
                target.put(key, source_value);
            } else {
                // if they are both JSONobjects, keep iterating
                if (source_value instanceof JSONObject) {
                    if (target.get(key) instanceof JSONObject) {
                        deepMergeJSON((JSONObject) source_value, (JSONObject) target.get(key));
                    } else {
                        deepMergeJSON((JSONObject) source_value, target);
                    }
                } else {
                    target.put(key, source_value);
                }
            }
        }
        return target;
    }

}
