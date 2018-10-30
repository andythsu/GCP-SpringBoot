package com.example.SpringBoot.Controllers;

import com.example.SpringBoot.MessageKey;
import com.example.SpringBoot.Services.DataStoreService;
import com.example.SpringBoot.Services.UtilService;
import com.example.SpringBoot.WebRequestError;
import com.google.cloud.datastore.Entity;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;

/**
 * @author: Andy Su
 * @Date: 10/25/2018
 */

@RestController
@Component
public class Setting {

    private Logger log = LoggerFactory.getLogger(Setting.class);

    private final String SETTING_KIND = "setting";

    @Autowired
    public DataStoreService db;

    @Autowired
    public UtilService util;

    @RequestMapping(value = "/settings.json", method = RequestMethod.PATCH)
    public String updateSetting(@RequestBody String body) throws WebRequestError {
        JSONObject source = util.parseToJSON(body);
        if (source == null){
            throw new WebRequestError(new MessageKey("Not a valid json", HttpStatus.BAD_REQUEST));
        }

        JSONObject target;
        try{
            target = new JSONObject(getSetting());
        }catch(JSONException | NullPointerException e){
            throw new WebRequestError(new MessageKey("Error fetching old data", HttpStatus.INTERNAL_SERVER_ERROR));
        }

        target = util.deepMergeJSON(source, target);

        return db.saveByKind(SETTING_KIND, target.toString());
    }

    @RequestMapping(value = "/settings.json", method = RequestMethod.POST)
    public String postSetting(@RequestBody String body) throws WebRequestError {
        JSONObject source = util.parseToJSON(body);
        if (source == null){
            throw new WebRequestError(new MessageKey("Not a valid json", HttpStatus.BAD_REQUEST));
        }
        return db.saveByKind(SETTING_KIND, source.toString());
    }


    @RequestMapping(value = "/settings.json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getSetting() {
        Iterator<Entity> entityIterator = db.getLastCreatedByKind(SETTING_KIND);
        String json = "";
        // gets the first thing in the iterator since it's already sorted by latest time
        while (entityIterator.hasNext()) {
            Entity en = entityIterator.next();
            json = String.valueOf(en.getString("json"));
        }
        return json;
    }
}
