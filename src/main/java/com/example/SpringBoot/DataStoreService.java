package com.example.SpringBoot;

import com.google.cloud.datastore.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;

//@Component
public class DataStoreService {

    public AuthService authService = new AuthService();

    private Logger log = LoggerFactory.getLogger(DataStoreService.class);

    public static final String KEYKIND = "test";
    public static final String SETTINGKIND = "setting";

//    @Autowired
//    public DataStoreService(AuthService authService){
//        this.authService = authService;
//    }

    private final Datastore datastore =
            DatastoreOptions.getDefaultInstance().getService();

    private final KeyFactory keyFactory =
            datastore.newKeyFactory()
                    .setKind(DataStoreService.KEYKIND);

    public Iterator<Entity> get() {
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind(DataStoreService.KEYKIND)
                .build();
        return datastore.run(query);
    }

    public Iterator<Entity> getSettingJson(){
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind(DataStoreService.SETTINGKIND)
                .build();
        return datastore.run(query);
    }
}
