package com.example.SpringBoot.Services.Datastore;

import com.example.SpringBoot.Services.Error.MessageKey;
import com.example.SpringBoot.Services.Error.WebRequestException;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.Timestamp;
import com.google.cloud.datastore.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;


@Component
public class DatastoreService {

    private Logger log = LoggerFactory.getLogger(DatastoreService.class);

//    private final static Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

    private static Datastore datastore;

    private static KeyFactory keyFactory;

    static {
        try {
            datastore = DatastoreOptions.newBuilder()
                        .setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream("GCP_service_account.json")))
                        .build()
                        .getService();
            keyFactory = datastore.newKeyFactory();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class DatastoreColumns{
        public static final String CREATEDAT = "CreatedAt";
        public static final String EXPIREDAT = "ExpiredAt";
        public static final String UPDATEDAT = "UpdatedAt";
        public static final String TOKEN = "Token";
        public static final String JSON = "Json";
    }

    public static class DatastoreKinds{
        public static final String AUTH = "auth";
        public static final String CREDENTIAL = "credential";
        public static final String SETTING = "setting";
        public static final String DEMO = "demo";
    }

    public static Iterator<Entity> getAllByKind(String kind) {
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind(kind)
                .build();
        return runQuery(query);
    }

    public static Iterator<Entity> getLastCreatedByKind(String kind) {
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind(kind)
                .setOrderBy(StructuredQuery.OrderBy.desc(DatastoreColumns.CREATEDAT))
                .setLimit(1)
                .build();
        return runQuery(query);
    }

    /**
     * if data exists, overwrite
     *
     * @param kind
     * @param criteria
     * @param new_val
     * @return
     */
    public static String upsertByKindAndData(String kind, DatastoreData criteria, DatastoreData new_val) {
        Iterator<Entity> entities = getAllByKindAndDataEqByOneData(kind, criteria);

        while (entities.hasNext()) {
            Entity old_en = entities.next();
            Key old_en_key = old_en.getKey();

            FullEntity.Builder fb = FullEntity.newBuilder(old_en_key);
            fb = buildEntity(fb, new_val);

            FullEntity entity = fb.build();

            datastore.put(entity);
        }

        return "upserted successfully";
    }

    /**
     * every field has to be equal
     * @param query
     * @param key
     * @param value
     * @return
     */
    public static EntityQuery.Builder eqByOneData(EntityQuery.Builder query, String key, Object value) {
        if (value instanceof String) {
            query.setFilter(StructuredQuery.PropertyFilter.eq(key, (String) value));
        } else if (value instanceof Integer) {
            query.setFilter(StructuredQuery.PropertyFilter.eq(key, (Integer) value));
        } else if (value instanceof Long) {
            query.setFilter(StructuredQuery.PropertyFilter.eq(key, (Long) value));
        } else if (value instanceof Double) {
            query.setFilter(StructuredQuery.PropertyFilter.eq(key, (Double) value));
        } else if (value instanceof Timestamp) {
            query.setFilter(StructuredQuery.PropertyFilter.eq(key, (Timestamp) value));
        }

        return query;
    }

    /**
     * return all entities if exists (all fields have to equall)
     * @return
     */
    public static Iterator<Entity> getAllByKindAndDataEqByOneData(String kind, DatastoreData dd) {
        EntityQuery.Builder queryBuilder = Query.newEntityQueryBuilder()
                .setKind(kind);
        String key = dd.getOneKey();
        Object value = dd.get(key);
        queryBuilder = eqByOneData(queryBuilder, key, value);
        Query query = queryBuilder.build();
        return runQuery(query);
    }

    public static Iterator<Entity> getAllByKindAndDataEqByTwoData(String kind, DatastoreData dd){
        EntityQuery.Builder queryBuilder = Query.newEntityQueryBuilder()
                .setKind(kind);
        List<String> list = dd.getTwoKeys();
        String key1 = list.get(0);
        String key2 = list.get(1);
        Object val1 = dd.get(key1);
        Object val2 = dd.get(key2);

        queryBuilder = eqByTwoData(queryBuilder, key1, val1, key2, val2);
        Query query = queryBuilder.build();
        return runQuery(query);
    }

    public static EntityQuery.Builder eqByTwoData(EntityQuery.Builder queryBuilder, String key1, Object val1, String key2, Object val2) {
        StructuredQuery.Filter first = generateEqFilter(key1, val1);
        StructuredQuery.Filter second = generateEqFilter(key2, val2);

        if (first == null || second == null){
            throw new WebRequestException(
                MessageKey.SERVER_ERROR, first, second
            );
        }

        queryBuilder.setFilter(
                StructuredQuery.CompositeFilter.and(first,second));
        return queryBuilder;
    }

    private static StructuredQuery.Filter generateEqFilter(String key, Object val) {
        if (val instanceof String){
            return StructuredQuery.PropertyFilter.eq(key, (String) val);
        }else if (val instanceof Timestamp){
            return StructuredQuery.PropertyFilter.eq(key, (Timestamp) val);
        }else{
            return null;
        }
    }

    /**
     * check if data exists in column
     */
    public static boolean isDataInKind(String kind, DatastoreData dd) {
        Iterator<Entity> en = getAllByKindAndDataEqByOneData(kind, dd);
        return en.hasNext();
    }

    public static Iterator<Entity> runQuery(Query<Entity> query) {
        return datastore.run(query);
    }

    /**
     * uses appengine library to fetch rows because there is currently no approach for cloud library
     * [warning] appengine is interfering with mailing service.
     * @param kind
     * @return
     */
//    public static int countRows(String kind) {
//        com.google.appengine.api.datastore.Query qry = new com.google.appengine.api.datastore.Query(kind);
//        com.google.appengine.api.datastore.DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
//        int totalCount = datastoreService.prepare(qry).countEntities(FetchOptions.Builder.withDefaults());
//        return totalCount;
//    }

    public static Iterator<Entity> getLastUpdatedByKind(String kind) {
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind(kind)
                .setOrderBy(StructuredQuery.OrderBy.desc(DatastoreColumns.UPDATEDAT))
                .setLimit(1)
                .build();
        return runQuery(query);
    }

    public static Key autoGenKey(String kind) {
        keyFactory.setKind(kind);
        Key taskKey = datastore.allocateId(keyFactory.newKey());
        return taskKey;
    }

    public static String saveByKind(String kind, DatastoreData data){
        return saveByKind(kind, data, null);
    }

    public static String saveByKind(String kind, DatastoreData data, Key key) {

        // ensure CreatedAt is set in DB
        if (!data.hasKey(DatastoreColumns.CREATEDAT)){
            data.put(DatastoreColumns.CREATEDAT, Timestamp.now());
        }

        // use system generate
        if (key == null) {
            key = autoGenKey(kind);
        }

        FullEntity.Builder entity = FullEntity.newBuilder(key);

        /* map data */
        entity = buildEntity(entity, data);

        Key insertedKey = datastore.add(entity.build()).getKey();
        return insertedKey.toString();
    }

    private static FullEntity.Builder buildEntity(FullEntity.Builder entity, DatastoreData dd) {
        for (String key : dd.keySet()) {
            Object value = dd.get(key);
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
