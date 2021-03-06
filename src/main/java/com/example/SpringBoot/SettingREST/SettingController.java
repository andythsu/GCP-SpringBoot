package com.example.SpringBoot.SettingREST;

import com.example.SpringBoot.Services.Datastore.DatastoreData;
import com.example.SpringBoot.Services.Datastore.DatastoreService;
import com.example.SpringBoot.Services.Error.MessageKey;
import com.example.SpringBoot.Services.Error.WebRequestException;
import com.example.SpringBoot.Services.Token.TokenSession;
import com.example.SpringBoot.Services.Token.TokenUtil;
import com.example.SpringBoot.Services.UtilService;
import com.google.cloud.datastore.Entity;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

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

    public DatastoreService db;

    @Autowired
    private TokenSession tokenSession;

    @Autowired
    private TokenUtil tokenUtil;

//    @RequestMapping(value = "/settings.json", method = RequestMethod.PATCH)
//    public String updateSetting(@RequestBody String body) {
//
//        JSONObject source = UtilService.parseToJSON(body);
//        if (source == null){
//            throw new WebRequestException(MessageKey.INVALID_JSON);
//        }
//
//        JSONObject target;
//        try{
//            target = new JSONObject(getSetting());
//        }catch(JSONException | NullPointerException e){
//            throw new WebRequestException(MessageKey.DATA_FETCH_ERROR);
//        }
//
//        target = UtilService.deepMergeJSON(source, target);
//
//        Map<String, Object> data = new HashMap<>();
//        data.put(commonNames.JSON, target.toString());
//
//        return db.saveByKind(SETTING_KIND, data);
//    }

    @RequestMapping(value = "/settings.json", method = RequestMethod.POST)
    public String postSetting(@RequestBody String body) {
        JSONObject source = UtilService.parseToJSON(body);
        if (source == null){
            throw new WebRequestException(MessageKey.INVALID_JSON);
        }
        DatastoreData dd = new DatastoreData();
        dd.put(DatastoreService.DatastoreColumns.JSON, source.toString());
        return db.saveByKind(SETTING_KIND, dd, null);
    }


    @RequestMapping(value = "/settings.json", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getSetting(@RequestHeader(value="token") String token) {

        tokenUtil.validateToken(token);

        Iterator<Entity> entityIterator = db.getLastCreatedByKind(SETTING_KIND);
        String json = "";
        // iterator will only contain 1 element (latest)
        while (entityIterator.hasNext()) {
            Entity en = entityIterator.next();
            json = String.valueOf(en.getString(DatastoreService.DatastoreColumns.JSON));
        }
        return json;
    }


}
