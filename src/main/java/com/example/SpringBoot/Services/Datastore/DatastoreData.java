package com.example.SpringBoot.Services.Datastore;

import com.example.SpringBoot.Services.Definitions.KeyValuePair;
import com.example.SpringBoot.Services.Error.MessageKey;
import com.example.SpringBoot.Services.Error.WebRequestException;
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

    public String getOneKey() {
        ArrayList<Object> arr_keys = new ArrayList<>(Arrays.asList(super.keySet().toArray()));
        if (arr_keys.size() < 1) throw new WebRequestException(
                MessageKey.SERVER_ERROR
        );
        return (String) arr_keys.get(0);
    }

    public ArrayList<String> getTwoKeys() {
        ArrayList<Object> arr_keys = new ArrayList<>(Arrays.asList(super.keySet().toArray()));
        if (arr_keys.size() < 2) throw new WebRequestException(
                MessageKey.SERVER_ERROR
        );
        ArrayList<String> rtn = new ArrayList<>();
        rtn.add((String) arr_keys.get(0));
        rtn.add((String) arr_keys.get(1));
        return rtn;
    }

    public boolean isEqual(DatastoreData target) {
        return super.getMap().equals(target.getMap());
    }
}
