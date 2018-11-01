package com.example.SpringBoot.Services;

import com.google.cloud.Timestamp;
import com.google.cloud.datastore.*;
import com.google.cloud.datastore.StructuredQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Iterator;
import java.util.Map;

public class DataStoreService {

    private Logger log = LoggerFactory.getLogger(DataStoreService.class);

    private static final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

    private static final KeyFactory keyFactory = datastore.newKeyFactory();

    private static UtilService.commonNames commonNames;

    public static Iterator<Entity> getAllByKind(String kind) {
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind(kind)
                .build();
        return datastore.run(query);
    }

    public static Iterator<Entity> getLastCreatedByKind(String kind){
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind(kind)
                .setOrderBy(StructuredQuery.OrderBy.desc(commonNames.CREATEDAT))
                .setLimit(1)
                .build();
        return datastore.run(query);
    }

    public static String saveByKind(String kind, Map<String, Object> data){
        keyFactory.setKind(kind);
        Key taskKey = datastore.allocateId(keyFactory.newKey());
        FullEntity.Builder entity = FullEntity.newBuilder(taskKey);

        /* map data */
        entity = mapEntity(entity, data);

        Key insertedKey = datastore.add(entity.build()).getKey();
        return insertedKey.toString();
    }

    private static FullEntity.Builder mapEntity(FullEntity.Builder entity, Map<String, Object> data){
        for (String key : data.keySet()) {
            Object value = data.get(key);
            if (value instanceof String) {
                entity.set(key, (String) value);
            } else if (value instanceof Integer) {
                entity.set(key, (Integer) value);
            } else if (value instanceof Long) {
                entity.set(key, (Long) value);
            } else if (value instanceof Double) {
                entity.set(key, (Double) value);
            } else if (value instanceof Timestamp) {
                entity.set(key, (Timestamp) value);
            }
        }
        return entity;
    }



}
