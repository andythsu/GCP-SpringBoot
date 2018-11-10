package com.example.SpringBoot.Services;

import com.example.SpringBoot.Services.Error.MessageKey;
import com.example.SpringBoot.Services.Error.WebRequestException;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;


public class UtilService {

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

    public static void merge(JSONObject ori_obj, JSONObject new_obj) {
        for (String key : new_obj.keySet()){
            if (ori_obj.has(key)){
                Object ori_val = ori_obj.get(key);
                Object new_val = new_obj.get(key);
                if (!sameInstance(ori_val, new_val)) throw new WebRequestException(new UtilServiceMessageKey(
                        HttpURLConnection.HTTP_BAD_REQUEST,
                        MessageKey.MessageKeyTags.RUN_TIME_ERROR,
                        UtilServiceMessageKey.INCOMPATIBLE_INPUT));
                // go down one for each one
                try{
                    JSONObject ori_val_json = ori_obj.getJSONObject(key);
                    JSONObject new_val_json = new_obj.getJSONObject(key);
                    merge(ori_val_json, new_val_json);
                }catch(JSONException ex){
                    // check if it's a string
                    try{
                        String new_val_str = new_obj.getString(key);
                        ori_obj.put(key, new_val_str);
                    }catch(JSONException exe){
                        throw new WebRequestException(new UtilServiceMessageKey(
                                HttpURLConnection.HTTP_BAD_REQUEST,
                                MessageKey.MessageKeyTags.RUN_TIME_ERROR,
                                UtilServiceMessageKey.WRONG_TYPE_INPUT
                        ));
                    }
                }
            }else{
                ori_obj.put(key, new_obj.get(key));
            }
        }
    }
    private static boolean sameInstance(Object obj1, Object obj2){
        return (obj1 instanceof String && obj2 instanceof String) ||
                (obj1 instanceof JSONObject && obj2 instanceof JSONObject);
    }


}
