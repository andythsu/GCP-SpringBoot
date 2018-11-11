package com.example.SpringBoot.DemoREST;

import com.example.SpringBoot.Services.Datastore.DatastoreData;
import com.example.SpringBoot.Services.Datastore.DatastoreService;
import com.example.SpringBoot.Services.Email.MailContent;
import com.example.SpringBoot.Services.Error.MessageKey;
import com.example.SpringBoot.Services.Error.WebRequestException;
import com.google.cloud.Timestamp;
import com.google.cloud.datastore.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Author: Andy Su
 * @Date: 2018-11-10
 */

@RestController
public class DemoController implements DemoRoute{

    private Logger log = LoggerFactory.getLogger(DemoController.class);

    @Override
    public List<DemoDto> getAll(){
        List<DemoDto> list = new ArrayList<>();
        Iterator<Entity> entities = DatastoreService.getAllByKind(DatastoreService.DatastoreKinds.DEMO);
        return generateData(entities);
    }

    @Override
    public List<DemoDto> getAllByType(@PathVariable(DemoRoute.TYPE) String type) {
        if (type == null) throw new WebRequestException(
                MessageKey.NULL_POINTER_ERROR
        );
        String kind = DatastoreService.DatastoreKinds.DEMO;
        DatastoreData dd = new DatastoreData();
        dd.put(DemoDto.TypeCol, type);
        Iterator entities = DatastoreService.getAllByKindAndDataEqByOneData(kind, dd);
        return generateData(entities);
    }

    @Override
    public List<DemoDto> getAllByTypeAndName(@PathVariable(DemoRoute.TYPE) String type, @PathVariable(DemoRoute.NAME) String name) {
        if (type == null || name == null) throw new WebRequestException(
            MessageKey.NULL_POINTER_ERROR
        );
        String kind = DatastoreService.DatastoreKinds.DEMO;
        DatastoreData dd = new DatastoreData();
        dd.put(DemoDto.TypeCol, type);
        dd.put(DemoDto.NameCol, name);
        Iterator entities = DatastoreService.getAllByKindAndDataEqByTwoData(kind, dd);
        return generateData(entities);
    }

    @Override
    public String save(Map<String, String> data) {
        if (data == null) throw new WebRequestException(
                MessageKey.NULL_POINTER_ERROR
        );
        String kind = DatastoreService.DatastoreKinds.DEMO;
        DatastoreData dd = new DatastoreData();
        String name = data.containsKey(DemoDto.NameCol) ? data.get(DemoDto.NameCol) : DemoDto.GenName;
        String type = data.containsKey(DemoDto.TypeCol) ? data.get(DemoDto.TypeCol) : DemoDto.GenType;
        String value = data.containsKey(DemoDto.ValueCol) ? data.get(DemoDto.ValueCol) : DemoDto.GenValue;

        dd.put(DemoDto.NameCol, name);
        dd.put(DemoDto.TypeCol, type);
        dd.put(DemoDto.ValueCol, value);
        return DatastoreService.saveByKind(kind, dd);
    }

    private ArrayList<DemoDto> generateData(Iterator<Entity> entities){
        ArrayList<DemoDto> list = new ArrayList<>();
        while (entities.hasNext()){
            Entity en = entities.next();
            Timestamp createdAt = en.getTimestamp(DemoDto.CreatedAtCol);
            String type = en.getString(DemoDto.TypeCol);
            String name = en.getString(DemoDto.NameCol);
            String value = en.getString(DemoDto.ValueCol);
            list.add(new DemoDto(createdAt, type, name, value));
        }
        return list;
    }
}
