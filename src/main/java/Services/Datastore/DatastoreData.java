package Services.Datastore;

import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author: Andy Su
 * @Date: 11/2/2018
 */

@Component
public class DatastoreData {
    /**
     * stores in (col,value) fashion
     */
   private Map<String, Object> data = new HashMap<>();
    public void put(String key, Object value){
       data.put(key, value);
    }
    public Object get(String key){
        return data.get(key);
    }
    public Map<String, Object> toMap(){
        return data;
    }
    public Set<String> keySet(){
        return data.keySet();
    }
    public String getOneKey(){
        ArrayList<Object> arr_keys = new ArrayList<>(Arrays.asList(data.keySet().toArray()));
        return (String) arr_keys.get(0);
    }
    public boolean isEqual(DatastoreData target){
        return data.equals(target.toMap());
    }

}
