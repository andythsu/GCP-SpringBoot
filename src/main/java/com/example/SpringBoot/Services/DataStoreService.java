package com.example.SpringBoot.Services;

import com.google.cloud.Timestamp;
import com.google.cloud.datastore.*;
import com.google.cloud.datastore.StructuredQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import java.util.Iterator;

@Component
public class DataStoreService {

    private Logger log = LoggerFactory.getLogger(DataStoreService.class);

    private final String CREATEDAT = "CreatedAt";

    private final String JSON = "Json";

    private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

    private final KeyFactory keyFactory = datastore.newKeyFactory();

    public Iterator<Entity> getAllByKind(String kind) {
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind(kind)
                .build();
        return datastore.run(query);
    }

    public Iterator<Entity> getLastCreatedByKind(String kind){
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind(kind)
                .setOrderBy(StructuredQuery.OrderBy.desc(CREATEDAT))
                .setLimit(1)
                .build();
        return datastore.run(query);
    }

    public String saveByKind(String kind, String data){
        keyFactory.setKind(kind);
        Key taskKey = datastore.allocateId(keyFactory.newKey());
        FullEntity entity = FullEntity.newBuilder(taskKey)
                .set(JSON, data)
                .set(CREATEDAT, Timestamp.now())
                .build();
        Key insertedKey = datastore.add(entity).getKey();
        return insertedKey.toString();

    }

}
