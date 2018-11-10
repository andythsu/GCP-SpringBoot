package com.example.SpringBoot.DemoREST;

import com.example.SpringBoot.Services.Datastore.DatastoreService;
import com.google.cloud.Timestamp;
import com.google.cloud.datastore.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author: Andy Su
 * @Date: 2018-11-10
 */

@RestController
@Component

public class DemoController {

    @RequestMapping(value = "/demo" , method = RequestMethod.GET)
    public List<DemoDto> getRequest(){
        List<DemoDto> list = new ArrayList<>();
        Iterator<Entity> entities = DatastoreService.getAllByKind(DatastoreService.DatastoreKinds.DEMO);
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
