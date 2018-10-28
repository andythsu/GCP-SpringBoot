package com.example.SpringBoot.Controllers;

import com.example.SpringBoot.Services.DataStoreService;
import com.google.cloud.datastore.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;

/**
 * @author: Andy Su
 * @Date: 10/25/2018
 */

@RestController
public class Setting {

    @Autowired
    public DataStoreService db;


    @RequestMapping(value = "/settings.json", method = RequestMethod.GET)
    public String getSetting() {
        Iterator<Entity> entityIterator = db.getAllByKind("setting");
        String json = "";
        while (entityIterator.hasNext()) {
            Entity en = entityIterator.next();
            json = String.valueOf(en.getString("json"));
        }
        return json;
    }
}
