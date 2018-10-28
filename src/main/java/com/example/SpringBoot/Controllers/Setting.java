package com.example.SpringBoot.Controllers;

import com.example.SpringBoot.AuthService;
import com.example.SpringBoot.DataStoreService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;

/**
 * @author: Andy Su
 * @Date: 10/25/2018
 */

@RestController
public class Greeting {

    Logger log = LoggerFactory.getLogger(Greeting.class);

    public DataStoreService db = new DataStoreService();

//    @Autowired
//    public Greeting(DataStoreService db){
//        this.db = db;
//    }

    @GetMapping("/setting.json")
    public String getSetting() {
        Iterator<Entity> entityIterator = db.get();
        while(entityIterator.hasNext()){
            Entity en = entityIterator.next();
            log.info(String.valueOf(en.getString("json")));
        }
        return ;
    }
}
