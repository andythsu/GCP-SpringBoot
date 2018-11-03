package Services.Datastore;

import Services.UtilService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.cloud.Timestamp;
import com.google.cloud.datastore.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
public class DatastoreService {

    private Logger log = LoggerFactory.getLogger(DatastoreService.class);

    private static final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

    private static final KeyFactory keyFactory = datastore.newKeyFactory();

    public static Iterator<Entity> getAllByKind(String kind) {
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind(kind)
                .build();
        return runQuery(query);
    }

    public static Iterator<Entity> getLastCreatedByKind(String kind) {
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind(kind)
                .setOrderBy(StructuredQuery.OrderBy.desc(UtilService.commonNames.CREATEDAT))
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
        Iterator<Entity> entities = getAllByKindAndDataEq(kind, criteria);

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
     *
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
     *
     * @return
     */
    public static Iterator<Entity> getAllByKindAndDataEq(String kind, DatastoreData dd) {
        EntityQuery.Builder queryBuilder = Query.newEntityQueryBuilder()
                .setKind(kind);
        String key = dd.getOneKey();
        Object value = dd.get(key);
        queryBuilder = eqByOneData(queryBuilder, key, value);
        Query query = queryBuilder.build();
        return runQuery(query);
    }

    /**
     * check if data exists in column
     */
    public static boolean isDataInKind(String kind, DatastoreData dd) {
        Iterator<Entity> en = getAllByKindAndDataEq(kind, dd);
        return en.hasNext();
    }

    public static Iterator<Entity> runQuery(Query<Entity> query) {
        return datastore.run(query);
    }

    /**
     * uses appengine library to fetch rows because there is currently no approach for cloud library
     *
     * @param kind
     * @return
     */
    public static int countRows(String kind) {
        com.google.appengine.api.datastore.Query qry = new com.google.appengine.api.datastore.Query(kind);
        com.google.appengine.api.datastore.DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
        int totalCount = datastoreService.prepare(qry).countEntities(FetchOptions.Builder.withDefaults());
        return totalCount;
    }

    public static Iterator<Entity> getLastUpdatedByKind(String kind) {
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind(kind)
                .setOrderBy(StructuredQuery.OrderBy.desc(UtilService.commonNames.UPDATEDAT))
                .setLimit(1)
                .build();
        return runQuery(query);
    }

    public static Key autoGenKey(String kind) {
        keyFactory.setKind(kind);
        Key taskKey = datastore.allocateId(keyFactory.newKey());
        return taskKey;
    }

    public static String saveByKind(String kind, DatastoreData data, Key key) {

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
