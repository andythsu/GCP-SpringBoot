package com.example.SpringBoot;

import com.example.SpringBoot.Error.MessageKey;
import com.example.SpringBoot.Services.DataStoreService;
import com.example.SpringBoot.Services.UtilService;
import com.example.SpringBoot.Error.WebRequestException;
import com.google.cloud.datastore.Entity;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.HttpURLConnection;
import java.util.Iterator;

/**
 * @author: Andy Su
 * @Date: 10/25/2018
 */

@RestController
@Component
public class SettingController {

    private Logger log = LoggerFactory.getLogger(SettingController.class);

    private final String SETTING_KIND = "setting";

    @Autowired
    public DataStoreService db;

    @Autowired
    public UtilService util;

    @RequestMapping(value = "/settings.json", method = RequestMethod.PATCH)
    public String updateSetting(@RequestBody String body) {

        JSONObject source = util.parseToJSON(body);
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

        target = util.deepMergeJSON(source, target);

        return db.saveByKind(SETTING_KIND, target.toString());
    }

    @RequestMapping(value = "/settings.json", method = RequestMethod.POST)
    public String postSetting(@RequestBody String body) {
        JSONObject source = util.parseToJSON(body);
        if (source == null){
            throw new WebRequestException(MessageKey.INVALID_JSON);
        }
        return db.saveByKind(SETTING_KIND, source.toString());
    }


    @RequestMapping(value = "/settings.json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getSetting() {
        Iterator<Entity> entityIterator = db.getLastCreatedByKind(SETTING_KIND);
        String json = "";
        // iterator will only contain 1 element (latest)
        while (entityIterator.hasNext()) {
            Entity en = entityIterator.next();
            json = String.valueOf(en.getString("json"));
        }
        return json;
    }
}
