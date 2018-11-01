package com.example.SpringBoot;

import com.example.SpringBoot.Error.MessageKey;

import com.example.SpringBoot.Error.WebRequestException;
import com.google.cloud.Timestamp;
import com.google.cloud.datastore.Entity;
import org.github.andythsu.GCP.Services.DatastoreService;
import org.github.andythsu.GCP.Services.UtilService;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author: Andy Su
 * @Date: 10/25/2018
 */

@RestController
@Component
public class SettingController {

    private Logger log = LoggerFactory.getLogger(SettingController.class);

    private final String SETTING_KIND = "setting";

    public UtilService.commonNames commonNames;

    public DatastoreService db;

    @RequestMapping(value = "/settings.json", method = RequestMethod.PATCH)
    public String updateSetting(@RequestBody String body) {

        JSONObject source = UtilService.parseToJSON(body);
        if (source == null){
            throw new WebRequestException(MessageKey.INVALID_JSON);
        }

        JSONObject target;
        try{
            target = new JSONObject(getSetting());
        }catch(JSONException | NullPointerException e){
            MessageKey msg = MessageKey.Builder()
                    .buildMessage("Error fetching data")
                    .buildStatus(HttpURLConnection.HTTP_INTERNAL_ERROR)
                    .buildTag(MessageKey.MessageKeyTags.DATA_ERROR)
                    .build();
            throw new WebRequestException(msg);
        }

        target = UtilService.deepMergeJSON(source, target);

        Map<String, Object> data = new HashMap<>();
        data.put(commonNames.JSON, target.toString());

        return db.saveByKind(SETTING_KIND, data);
    }

    @RequestMapping(value = "/settings.json", method = RequestMethod.POST)
    public String postSetting(@RequestBody String body) {
        JSONObject source = UtilService.parseToJSON(body);
        if (source == null){
            throw new WebRequestException(MessageKey.INVALID_JSON);
        }
        Map<String, Object> data = new HashMap<>();
        data.put(commonNames.JSON, source.toString());
        data.put(commonNames.CREATEDAT, Timestamp.now());
        return db.saveByKind(SETTING_KIND, data);
    }


    @RequestMapping(value = "/settings.json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getSetting() {
        Iterator<Entity> entityIterator = db.getLastCreatedByKind(SETTING_KIND);
        String json = "";
        // iterator will only contain 1 element (latest)
        while (entityIterator.hasNext()) {
            Entity en = entityIterator.next();
            json = String.valueOf(en.getString(commonNames.JSON));
        }
        return json;
    }
}
