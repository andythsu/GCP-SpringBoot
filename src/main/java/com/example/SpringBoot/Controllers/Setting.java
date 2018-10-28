package com.example.SpringBoot.Controllers;

import com.example.SpringBoot.DataStoreService;
import com.google.cloud.datastore.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;

/**
 * @author: Andy Su
 * @Date: 10/25/2018
 */

@RestController
public class Setting {

    Logger log = LoggerFactory.getLogger(Setting.class);

    public DataStoreService db = new DataStoreService();


//    @Autowired
//    public Greeting(DataStoreService db){
//        this.db = db;
//    }

    @GetMapping("/setting.json")
    public String getSetting() {
        Iterator<Entity> entityIterator = db.getSettingJson();
        String json = "";
        while(entityIterator.hasNext()){
            Entity en = entityIterator.next();
            json = String.valueOf(en.getString("json"));
        }
        return json;
    }
}
