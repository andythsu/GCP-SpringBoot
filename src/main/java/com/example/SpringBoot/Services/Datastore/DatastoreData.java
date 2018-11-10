package com.example.SpringBoot.Services.Datastore;

import com.example.SpringBoot.Services.Definitions.KeyValuePair;
import com.google.cloud.Timestamp;

import java.util.*;

/**
 * @author: Andy Su
 * @Date: 11/2/2018
 */

/**
 * stores in (col,value) fashion
 */
public class DatastoreData extends KeyValuePair {

    public DatastoreData(){
        // generate CreatedAt when constructed
        super.put(DatastoreService.DatastoreColumns.CREATEDAT, Timestamp.now());
    }

    public String getOneKey(){
        ArrayList<Object> arr_keys = new ArrayList<>(Arrays.asList(super.keySet().toArray()));
        return (String) arr_keys.get(0);
    }
    public boolean isEqual(DatastoreData target){
        return super.getMap().equals(target.getMap());
    }
}
