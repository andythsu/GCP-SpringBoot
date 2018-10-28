package com.example.SpringBoot.Services;

import com.google.cloud.datastore.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
public class DataStoreService {


    private Logger log = LoggerFactory.getLogger(DataStoreService.class);

    private final Datastore datastore =
            DatastoreOptions.getDefaultInstance().getService();

    public Iterator<Entity> getAllByKind(String kind) {
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind(kind)
                .build();
        return datastore.run(query);
    }

}
